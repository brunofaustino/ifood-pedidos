# iFood teste Engenheiro de Dados

## Introdução

Esse projeto é uma proposta de solução para a criação de um ambiente de processamento de dados de pedidos do iFood.
Mais informações em: [iFood Test](https://github.com/ifood/ifood-data-engineering-test)

A arquitetura da solução foi desenvolvida com foco em escalabilidade. 

![image](https://files-github-projects-bfa.s3.amazonaws.com/arquitetura-delta-lake-ifood-test-data-engineer.jpg)

A origem dos dados é formada por três arquivos, constituindo as entidades: 
- order: contendo dados dos pedidos gerados pelos clientes
- consumer: informações dos clientes
- restaurant: estabelecimentos cadastrados na plataforma do iFood
    
Utilizamos o Delta Lake como uma camada acima do nosso data lake (Amazon S3), o que nos permite trabalhar com transações ACID e obter melhor performance em interações frequentes com o nosso data lake. Sendo também possível obter a experiência de batch e streaming de forma unificada.

Camadas do delta lake:
- Bronze: dado em seu formato nativo e bruto
- Silver: dado limpo e transformado
- Gold: dado que será acessado para consumo

## Fluxo das tabelas

Antes da ingestão na camada Bronze do Delta Lake, os dados são pseudonimizados gerando uma nova tabela (lookup table), para armazenar *separadamente* os dados pessoais.
Em seguida, as informações são salvas em seu formato bruto na camada Bronze.
Feito isso, depois de algumas transformações são geradas duas novas tabelas:
- Order Item: contém os itens de um determinado do pedido
- Order Garnish itens: adicionais dos itens

Por fim, todas as informações relacionadas aos pedidos são carregadas para a camada Gold, para serem consumidas por diferentes áreas.

![image](https://files-github-projects-bfa.s3.amazonaws.com/fluxo-das-tabelas-ifood-test-data-engineer.jpg)


![image](https://files-github-projects-bfa.s3.amazonaws.com/fluxo-das-tabelas-ifood-test-data-engineer-2.jpg)

---

# Privacidade, Compliance e LGPD/GDPR

## Pseudonimização

A [LGPD](http://www.planalto.gov.br/ccivil_03/_ato2015-2018/2018/lei/l13709.htm)  trata a pseudonimização como:
_"tratamento por meio do qual um dado perde a possibilidade de associação, direta ou indireta, a um indivíduo, senão pelo uso de informação adicional mantida separadamente pelo controlador em ambiente controlado e seguro."_

![image](https://files-github-projects-bfa.s3.amazonaws.com/fluxo-criptografia-resized.jpg.png)

Primeiramente identificamos que a base de dados possui atributos de dados pessoais (CPF e Telefone) que, segundo a LGPD, são _"toda informação relacionada a pessoa natural identificada ou identificável"_. 
Com isso, uma política de 4 etapas foi criada a fim de pseudonimizar os dados, fazendo com que sejam mantidos e tratados separadamente para garantir a não atribuição de uma pessoa identificável:
 
1) Serialização: criamos uma chave serializada, resultado do embaralhamento dos atributos: CPF, Nome, Telefone e DDD, isso ajuda a evitar comparações por listas de hash. Utilizamos uma função de hash (SHA) aplicando algoritmos matemáticos a fim de mapear as strings de entrada em bits de tamanho fixo (hash). Funções como essa realizam criptografias unidirecionais (que não podem ser revertidas computacionalmente por engenharia reversa).
2) Criptografia: hash's unidirecionais não são suficientes uma vez que podem ser revertidos 
com a utilização de rainbow tables (grandes dicionários de dados com hash’s pré-calculados). 
Sendo assim, para cada hash gerado aplicamos um algoritmo de criptografia forte (AES-125) a fim de contornar os riscos de ataques por tabelas de hash’s.
4) Lookup tables: Por fim, com resultado das operações acima, geramos um novo atributo pseudonimizado (pseudonymous_id) e o armazenamos separadamente em uma tabela de mapeamento (lookup table) com seus respectivos dados pessoais. 
Isso permite que em todo o processo de processamento dos dados apenas os pseudônimos sejam utilizados.
Nossa lookup table possui os dados pessoais que podem ser usados para identificar determinada pessoa.
Esse processo faz com que somente os dados não pessoais sejam utilizados em processos posteriores de manipulação dos dados.   

Vale ressaltar que, a GDPR nos incentiva a [pseudonimizar os dados o mais rápido possível](https://www.privacy-regulation.eu/en/recital-78-GDPR.htm), por esse motivo optamos por fazer a pseudonimização imediatamente antes da ingestão dos dados no Delta Lake.

- Assim, fazemos com que os dados que serão manipulados junto as informações de pedidos não contenham os dados pessoais. Como o exemplo de "Consumer" apresentado abaixo: 

![image](https://files-github-projects-bfa.s3.amazonaws.com/customer-table.png)

- Por sua vez, a tabela de mapeamento (lookup table) pode ser armazenada isoladamente.

![image](https://files-github-projects-bfa.s3.amazonaws.com/lookup-table.png)

## Governança de dados

- Uma vez que os dados pessoais estão isolados, podemos facilmente criar políticas de acesso, por exemplo, bloqueando o acesso de usuários IAM aos dados do diretório onde serão salvos os dados pessoais (delta_lake/lookup/consumer_order/), bem como a implementação de controles de acesso as tabelas (podendo ser vinculados com provedores de identidade).

- O Delta Lake nos ajuda com políticas de retenção e Solicitações de Esquecimento (um dos Direitos do Titular do Dado). Em ambientes tradicionais de data lake torna-se muito difícil mapear completamente entidades de dados. 
No Delta Lake após a exclusão, comandos como *"vaccum"* podem ser utilizados para remover todos os arquivos que deixaram de ser referenciados pela sua respectiva tabela delta. Além disso, obtemos ganho de performance com agrupamento de *"ordem-z"* na varredura do processo de exclusão. 
A combinação Delta Lake + pseudonymous_id + Glue Catalog facilita o mapeamento e controle dos dados. 


---

# Pipeline ETL

## Requerimentos

* [Databricks](https://databricks.com/product/aws-pricing) Standard Plan ou superior
* [Amazon AWS](https://aws.amazon.com/pt/)
    - Amazon Redshift
    - S3
    - Glue Catalog
    - Cloudformation
* [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2-windows.html)
             
## Criando o ambiente

### Amazon AWS

Vamos utilizar o Cloudformation para o provisionamento do ambiente na AWS. Todos os arquivos de template estão no diretório iac/cloudformation/.
- Observações: 
    - Os comandos abaixo estão prontos para serem executados na raiz deste projeto.
    - Utilizaremos a região 'us-west-2'
    - *Atenção*! Os procedimentos de criação do ambiente não levam em conta práticas de segurança. 
    Essas ações foram criadas apenas para automatizar o máximo possível o provisionamento do ambiente, sendo facilmente executadas em ambientes de teste. Portanto, ajuste as políticas caso necessário.


- Altere o arquivo iac/cloudformation/stack_data.yaml substituindo o YOUR_ACCOUNT_ID pelo seu [Account ID](https://www.apn-portal.com/knowledgebase/articles/FAQ/Where-Can-I-Find-My-AWS-Account-ID) da AWS:
    
    ![image](https://files-github-projects-bfa.s3.amazonaws.com/template-you-account-id-2.png)
    
- Crie um usuário IAM com as seguintes políticas anexadas indicadas abaixo.     
    - AmazonEC2FullAccess
    - IAMFullAccess
    - AmazonS3FullAccess
    - AmazonRedshiftFullAccess
    - AWSCloudFormationFullAccess

- Defina as credenciais do seu usuário IAM rodando o aws configure. 
    ```bash
    aws configure
    ```

- Criando as Roles e Policies necessárias.
    ```bash
    aws cloudformation create-stack --stack-name stack-access --template-body file://iac/cloudformation/stack_access.yaml --output text --capabilities CAPABILITY_NAMED_IAM
    ```
- Aguarde o término do provisionamento da stack (status: CREATE_COMPLETE). Você pode acompanhar o status executando:
    ```bash
    aws cloudformation describe-stacks --stack-name stack-access --query "Stacks[0].StackStatus" --output text
    ```
 
- Agora que as políticas necessárias foram criadas, anexe a Policy “Databricks” ao seu usuário IAM.
    ![image](https://files-github-projects-bfa.s3.amazonaws.com/iam-policies-2.png)

- No [gerenciamento de contas da Databricks](https://accounts.cloud.databricks.com/) informe as credenciais do seu usuário IAM.
 
    ![image](https://files-github-projects-bfa.s3.amazonaws.com/aws-account-databricks.png)

- Crie um Bucket S3 (na região us-west-2) na AWS. Esse bucket será utilizado no Pipeline de ETL e no Redshift Spectrum.

- Execute o cloudformation para a criação da stack de dados.
     ```bash
    aws cloudformation create-stack --stack-name stack-data --template-body file://iac/cloudformation/stack_data.yaml --output text --capabilities CAPABILITY_NAMED_IAM
    ```
 - Aguarde a término do provisionamento da stack (status: CREATE_COMPLETE). Você pode acompanhar o status executando:
    ```bash
    aws cloudformation describe-stacks --stack-name stack-data --query "Stacks[0].StackStatus" --output text
    ```
- Após o término execute o comando abaixo para obter o endpoint do cluster redshift.
    ```bash
    aws cloudformation describe-stacks --stack-name stack-data --query "Stacks[0].Outputs[?OutputKey=='ClusterEndpoint'].OutputValue" --output text
    ```

### Databricks cluster

- Criação Instance Profile:

    ![image](https://files-github-projects-bfa.s3.amazonaws.com/add-instance-profile-databricks.png)
    > arn:aws:iam::YOUR_ACCOUNT_ID:instance-profile/Databricks_Glue_S3

- Criação do Cluster Spark:
    - Configurações utilizadas:
    
        ![image](https://files-github-projects-bfa.s3.amazonaws.com/criar-cluster-1.png)
    
    - Selecione a Instance Profile:
    
        ![image](https://files-github-projects-bfa.s3.amazonaws.com/criar-cluster-2.png)
    
    - Habilite o Glue Catalog:
    
        ![image](https://files-github-projects-bfa.s3.amazonaws.com/criar-cluster-3.png)
        > spark.databricks.hive.metastore.glueCatalog.enabled true
    
        Obs.: a utilização de metastore externo pode aumentar consideravelmente a latência dos dados. 
        Para contornar esse problema podemos habilitar o cache Glue Catalog. Basta adicionalmente informar:
        ```bash
        spark.hadoop.aws.glue.cache.table.enable true
        spark.hadoop.aws.glue.cache.table.size 1000
        spark.hadoop.aws.glue.cache.table.ttl-mins 30
        ```
    
    - Instalação Library:
    ![image](https://files-github-projects-bfa.s3.amazonaws.com/criar-cluster-4-install-library.png)
        > pycrypto==2.6.1

- Criação das tabelas no redshift:
    ```bash
    python iac/redshift/app.py --endpoint-redshift "YOUR_REDSHIFT_ENDPOINT" --aws-account-id "YOUR_ACCOUNT_ID" --aws-s3-bucket "YOUR_BUCKET_NAME"
    ```

    
## :white_check_mark: Execução

Após o provisionamento do ambiente, o notebook contendo o pipeline ETL encontra-se pronto para execução na [Databricks](https://databricks.com/):

:point_right: [Notebook Pipeline ETL](https://github.com/brunofaustino/ifood-pedidos/blob/master/etl/pipeline_etl.ipynb)

Por favor, no notebook atente-se somente em informar o nome do bucket criado.

![image](https://files-github-projects-bfa.s3.amazonaws.com/s3-bucket-name-databricks.png)

Após o término da execução do pipeline ETL os dados estão disponíveis no redshifit para consulta via SQL e consumo através da API.

---

# API

- Download da imagem:
    ```bash
    docker pull bfamorim/api-restaurant:1.0
    ```

- Execução da imagem. Substitua a <REDSHIFT_ENDPOINT> pelo endpoint do redshift.
    ```bash
    docker run -p 8082:8082 -e REDSHIFT_ENDPOINT=<REDSHIFT_ENDPOINT> bfamorim/api-restaurant:1.0
    ```

- Requisições:
    - Contagem por dia para o estado de São Paulo:
    ```bash
    http://localhost:8082/order/count-per-day-for-city-state?orderDate=2018-12-08&state=SP
    ```
    - Contagem por dia para o estado de São Paulo e cidade de São Paulo:
    ```bash
    http://localhost:8082/order/count-per-day-for-city-state?orderDate=2018-12-08&state=SP&city=SAO PAULO
    ```
  
    - Top 10 restaurantes por cliente:
    ```bash
    http://localhost:8082/order/top-10-consumed-restaurants-per-customer?customerId=2a68e52c-929a-4801-8128-86afb8cacb58
    ```
"# ifood-pedidos" 

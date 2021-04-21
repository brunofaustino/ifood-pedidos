# api-restaurant

## Build do projeto com gradle

- gradle build

## Build com docker

- docker build -t <YOUR_USERNAME>/api-restaurant:1.0 .

## Execução com docker
- docker run -p 8082:8082 -e REDSHIFT_ENDPOINT=<YOUR_REDSHIFT_ENDPOINT> <YOUR_USERNAME>/api-restaurant:1.0

## Examples Consuming Endpoints

- Requisições
    - Contagem por dia para o estado de São Paulo
    ```bash
    http://localhost:8082/order/count-per-day-for-city-state?orderDate=2018-12-08&state=SP
    ```
    - Contagem por dia para o estado de São Paulo and cidade São Paulo
    ```bash
    http://localhost:8082/order/count-per-day-for-city-state?orderDate=2018-12-08&state=SP&city=SAO PAULO
    ```
  
    - Top 10 restaurantes por cliente
    ```bash
    http://localhost:8082/order/top-10-consumed-restaurants-per-customer?customerId=2a68e52c-929a-4801-8128-86afb8cacb58
    ```

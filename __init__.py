from Crypto.Cipher import AES
from Crypto.Hash import SHA256

key = 'A4NECBPIU5LDUEAC9A8DGLBBKSGLBYEB'

cipher = AES.new(key, AES.MODE_ECB)
text = '1759848a3678fd13649f0f485724f5dd9f4945d86a398a13986823abe38a6272'
msg = cipher.encrypt(text)
print(msg)
print(type(msg))

decipher = AES.new(key, AES.MODE_ECB)
msg_decipher = decipher.decrypt(msg)
print(msg_decipher)

msg_decipher == bytes(text, encoding='utf8')
str(msg_decipher, encoding='utf8') == text

str(msg_decipher, encoding='utf8')

h = SHA256.new()
h.update(b'Hello')
print(h.hexdigest())

from Crypto.Hash import SHA256

hashObject = SHA256.new()
hashObject.update(b'TechTutorialsX')
hashObject.hexdigest()

expectedDigest = "648246ee43bdfc84da50120d50ee57fd88206cebc65db477fbe683d4aacfa1e7"

print(hashObject.hexdigest())
print(hashObject.hexdigest() == expectedDigest)


def sha_256(text):
    hash_object = SHA256.new()
    hash_object.update(bytes(text, encoding='utf8'))
    text_hash = hash_object.hexdigest()
    return text_hash


print(type(sha_256('TechTutorialsX')))

###################################################

from Crypto.Cipher import AES
from Crypto.Hash import SHA256


def encrypt(text):
    key = 'A4NECBPIU5LDUEAC9A8DGLBBKSGLBYEB'
    cipher = AES.new(key, AES.MODE_ECB)
    text_encrypt = cipher.encrypt(text)

    return text_encrypt


def sha_256(text):
    hash_object = SHA256.new()
    hash_object.update(bytes(text, encoding='utf8'))
    text_hash = hash_object.hexdigest()
    return text_hash

text = '56097710016|FARLEY|19|512207182'
s = sha_256(text)
print(type(s))
s
d = decipher.encrypt(s)
decipher.decrypt(d)

enc = encrypt(text)
print(decipher.decrypt(enc))

var = b'5l\xe3\t\xf0\xc7\xc3\x9d\x15\xdb5\x10\x06a\x0e|l\x1b\x0f\xb0\xc6e\x80\x1d\xc4b\xe7\x89^M!\x9awg\xa1\xad\xfe\xaa\xe5\xb8\xe8\xff\xd3\xa3\x99-\xa2\xf1\nB?v]\xc9\xbb\t\xa6\x90\xa1\x91A\xdfP\xe2'
print(str(var))

var = b"NWzjCfDHw50V2zUQBmEOfGwbD7DGZYAdxGLniV5NIZp3Z6Gt/qrluOj/06OZLaLxCkI/dl3JuwmmkKGRQd9Q4g=="
print(decipher.decrypt(var))

b"abcde".decode("utf-8")
var.decode('utf-8')

###################################################

from Crypto.Cipher import AES
from Crypto.Hash import SHA256


def encrypt(text):
    text_encrypt = cipher.encrypt(text)
    return text_encrypt


def sha_256(text):
    hash_object = SHA256.new()
    hash_object.update(bytes(text, encoding='utf8'))
    text_hash = hash_object.hexdigest()
    return text_hash


@udf(returnType=BinaryType())
def udf_pseudonymous(text):
    def encrypt(text):
        key = 'A4NECBPIU5LDUEAC9A8DGLBBKSGLBYEB'
        cipher = AES.new(key, AES.MODE_ECB)
        text_encrypt = cipher.encrypt(text)

        return text_encrypt

    def sha_256(text):
        hash_object = SHA256.new()
        hash_object.update(bytes(text, encoding='utf8'))
        text_hash = hash_object.hexdigest()
        return text_hash

    return encrypt(sha_256(text))


@udf(returnType=StringType())
def udf_decrypt(text):
    key = 'A4NECBPIU5LDUEAC9A8DGLBBKSGLBYEB'
    decipher = AES.new(key, AES.MODE_ECB)
    texts_decrypt = decipher.decrypt(text)
    return str(texts_decrypt, encoding='utf8')


###################################################

<h1 align="center">AWS SAM - Serverless Architecture </h1>


<p align="center">
   <a href="#-projeto">Projeto</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
   <a href="#-requisitos">Requisitos </a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
   <a href="#-executar">Como executar ?</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 </p>
 
 
## 💻 Projeto 

Essa é uma aplicação construída utilizando o ambiente serverless da AWS usando AWS SAM, Amazon API Gateway, AWS Lambda and Amazon DynamoDB.
Também utiliza a estrutura ORM do DynamoDBMapper para mapear os items TRIP em uma tabela DynamoDB para uma API RESTfull.

## :rocket: Requisitos

* AWS CLI, já configurada e com a permissão de PowerUser
* [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Docker](https://www.docker.com/community-edition)
* [Maven](https://maven.apache.org/install.html)
* [SAM CLI](https://github.com/awslabs/aws-sam-cli)
* [Python 3](https://docs.python.org/3/)

## ⚙️ Como executar ?

### Instalar dependências

We use `maven` to install our dependencies and package our application into a JAR file:

```bash
mvn install
```

### Rodando localmente


1. Rode o DynamoDB localmente em um container Docker. `docker run -p 8000:8000 -v $(pwd)/local/dynamodb:/data/ amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -dbPath /data`
2. Crie a table do DynamoDB. `aws dynamodb create-table --table-name trip --attribute-definitions AttributeName=topic,AttributeType=S AttributeName=dateTimeCreation,AttributeType=S AttributeName=tag,AttributeType=S AttributeName=consumed,AttributeType=S --key-schema AttributeName=topic,KeyType=HASH AttributeName=dateTimeCreation,KeyType=RANGE --local-secondary-indexes 'IndexName=tagIndex,KeySchema=[{AttributeName=topic,KeyType=HASH},{AttributeName=tag,KeyType=RANGE}],Projection={ProjectionType=ALL}' 'IndexName=consumedIndex,KeySchema=[{AttributeName=topic,KeyType=HASH},{AttributeName=consumed,KeyType=RANGE}],Projection={ProjectionType=ALL}' --billing-mode PAY_PER_REQUEST --endpoint-url http://localhost:8000`

Caso a table já exista, você pode deletá-la: `aws dynamodb delete-table --table-name trip --endpoint-url http://localhost:8000`

3. Inicie a API local do SAM.
 - 🍎 Mac: `sam local start-api --env-vars src/test/resources/test_environment_mac.json`
 - 🪟 Windows: `sam local start-api --env-vars src/test/resources/test_environment_windows.json`
 - 🐧 Linux: `sam local start-api --env-vars src/test/resources/test_environment_linux.json`
 
 OBS:  Se você já possui o container localmente em sua máquina, você pode pular o processo de download pelo comando  --skip-pull-image 
 
 Se o comando anterior rodou, você agora consegue acessar o endpoint localmente. 


---
<h4 align="center">
   Code and coffee ☕
</h4>


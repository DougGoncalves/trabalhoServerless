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

### Local development

**Invoking function locally through local API Gateway**
1. Start DynamoDB Local in a Docker container. `docker run -p 8000:8000 -v $(pwd)/local/dynamodb:/data/ amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -dbPath /data`
2. Create the DynamoDB table. `aws dynamodb create-table --table-name study --attribute-definitions AttributeName=topic,AttributeType=S AttributeName=dateTimeCreation,AttributeType=S AttributeName=tag,AttributeType=S AttributeName=consumed,AttributeType=S --key-schema AttributeName=topic,KeyType=HASH AttributeName=dateTimeCreation,KeyType=RANGE --local-secondary-indexes 'IndexName=tagIndex,KeySchema=[{AttributeName=topic,KeyType=HASH},{AttributeName=tag,KeyType=RANGE}],Projection={ProjectionType=ALL}' 'IndexName=consumedIndex,KeySchema=[{AttributeName=topic,KeyType=HASH},{AttributeName=consumed,KeyType=RANGE}],Projection={ProjectionType=ALL}' --billing-mode PAY_PER_REQUEST --endpoint-url http://localhost:8000`

If the table already exist, you can delete: `aws dynamodb delete-table --table-name study --endpoint-url http://localhost:8000`

3. Start the SAM local API.
 - On a Mac: `sam local start-api --env-vars src/test/resources/test_environment_mac.json`
 - On Windows: `sam local start-api --env-vars src/test/resources/test_environment_windows.json`
 - On Linux: `sam local start-api --env-vars src/test/resources/test_environment_linux.json`
 
 OBS:  If you already have the container locally (in your case the java8), then you can use --skip-pull-image to remove the download

If the previous command ran successfully you should now be able to hit the following local endpoint to
invoke the functions rooted at `http://localhost:3000/study/{topic}?starts=2020-01-02&ends=2020-02-02`.
It shoud return 404. Now you can explore all endpoints, use the src/test/resources/Study DataLake.postman_collection.json to import a API Rest Collection into Postman.

**SAM CLI** is used to emulate both Lambda and API Gateway locally and uses our `template.yaml` to
understand how to bootstrap this environment (runtime, where the source code is, etc.) - The
following excerpt is what the CLI will read in order to initialize an API and its routes:


## Packaging and deployment

AWS Lambda Java runtime accepts either a zip file or a standalone JAR file - We use the latter in
this example. SAM will use `CodeUri` property to know where to look up for both application and
dependencies:

Firstly, we need a `S3 bucket` where we can upload our Lambda functions packaged as ZIP before we
deploy anything - If you don't have a S3 bucket to store code artifacts then this is a good time to
create one:

```bash
export BUCKET_NAME=my_cool_new_bucket
aws s3 mb s3://$BUCKET_NAME
```

Next, run the following command to package our Lambda function to S3:

```bash
sam package \
    --template-file template.yaml \
    --output-template-file packaged.yaml \
    --s3-bucket $BUCKET_NAME
```

Next, the following command will create a Cloudformation Stack and deploy your SAM resources.

```bash
sam deploy \
    --template-file packaged.yaml \
    --stack-name study-datalake \
    --capabilities CAPABILITY_IAM
```

> **See [Serverless Application Model (SAM) HOWTO Guide](https://github.com/awslabs/serverless-application-model/blob/master/HOWTO.md) for more details in how to get started.**

After deployment is complete you can run the following command to retrieve the API Gateway Endpoint URL:

```bash
aws cloudformation describe-stacks \
    --stack-name sam-orderHandler \
    --query 'Stacks[].Outputs'
```



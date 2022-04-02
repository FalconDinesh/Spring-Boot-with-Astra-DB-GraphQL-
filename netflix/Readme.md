# ASTRA WITH SPRING BOOT USING GRAPHQL

DataStax Astra is a `Database as a Service`(Daas) offering that is powered by `Apache Cassandra`. This gives us a `fully hosted, fully managed Cassandra database` that we can use to store our data, which includes all of the power that Cassandra offers for `scalability, high availability and performance`.

It is the `first and only serverless and multi-region database service` that is based on an open-source `NoSQL database`, namely Apache Cassandra.


On top of this, Astra also incorporates the `Stargate data platform` that exposes the exact same underlying data via different APIs. This gives us access to traditional Cassandra tables using REST and GraphQL APIs – both of which are 100% compatible with each other and the more traditional CQL APIs. These can make access to our data `incredibly flexible with only a standard HTTP client` – such as the Spring RestTemplate.

What tools does Astra DB include?

    1. Stargate APIs: The Stargate APIs are available as the Astra DB APIs. Create an application token and begin interacting with your database.

    2. CQLSH: Each Astra DB database includes an integrated CQL shell for interactive Cassandra Query Language (CQL) commands. Use CQLSH to create tables, insert data, and modify data in your keyspace.

    3. Astra DB Data Loader: From the console, use Data Loader to load the following into your Astra DB database:

        CSV data
        Example datasets
        Extracted DynamoDB data that resides in an Amazon S3 bucket

    4. DataStax Bulk Loader: Use DataStax Bulk Loader (dsbulk) to load and unload data into your Astra DB database. You can load data from a variety of sources and unload data from DSE database for transfer, use, or storage of data.

    5. Metrics dashboards: Use the integrated metrics dashboards to view health metrics that include information regarding latency and throughput to the database. These metrics provide insights into the performance of the database and how workloads are distributed.


Stargate is a `data gateway deployed between client applications and a database`. It's built with extensibility as a first-class citizen and makes it easy to use a database for any application workload by adding plugin support for new APIs, data types, and access methods.

stargate contains the following components:

`API Services`: Responsible for defining the API, handling and converting requests to db queries, dispatching to persistence, returning and serving response

* cql: API implementation for the `Cassandra Query Language`
* restapi: API implementation for exposing Cassandra data over `REST`
* graphqlapi: API implementation for exposing Cassandra data over `GraphQL`


Ultimately, Astra
DB is an easier and more cost-efficient way to use Cassandra

Notes: we `should not use cql and document api on same tables`, since they will act on differnt tables for api's
for ex: customer table may be cql and order table could be json
____________________________________________________________________________
____________________________________________________________________________

## In this Spring Boot Application we are going to use GraphQL to interact with Astra DB


`Guide`: https://github.com/datastax/astra-sdk-java/wiki

`Documentation`: https://docs.datastax.com/en/astra/docs/

-> com.datastax.stargate.sdk.gql.ApiGraphQLClient

______________________________________________________________________________
Method                             -      Description
______________________________________________________________________________
cqlKeyspace(String keyspace)       -      Access `/graphql/{keyspace}` endpoint.

cqlSchema()                        -      Access `/graphql-schema` endpoint.

graphQLFirst()                     -      Access `/graphql-admin` to deploy Schema.

keyspaces()                        -      Return list of Namespace(keyspaces) available.


We will use astraClient class and `astraClient.apiStargateGraphQL()` for graphql.

_____________________________________________________________________________________________________________________

### Table DML

```
CREATE TABLE netflix.movies (
    id uuid,
    name text,
    language text,
    genre text,
    year int,
    imdb_rating float,
    length int,
    PRIMARY KEY (id, name, language)
) WITH CLUSTERING ORDER BY (name ASC, language ASC);
```

```
INSERT INTO netflix.movies (id , name , language , genre ,year, imdb_rating , length) VALUES ( f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454, 'Titanic', 'English', 'Romance', 1990, 8.7, 195);
```

`prepare env.bat file` and store the Env variables

SET ASTRA_DB_ID=your database id

SET ASTRA_DB_REGION= region

SET ASTRA_DB_KEYSPACE=netflix

SET ASTRA_DB_APPLICATION_TOKEN= token generated for user

To run the springboot application
```
mvn clean install spring-boot:run -Dmaven.test.skip=true


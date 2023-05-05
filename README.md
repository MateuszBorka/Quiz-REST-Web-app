# Quiz-REST-web-app

Quiz-REST-web-app: A Java Spring Project for Creating, Solving and Sharing Quizzes

Quiz app is a Java Spring project that follows a REST architecture pattern. It provides an internet platform for users to create, solve, and share quizzes. With a Docker image file and Postman collection for easy testing, this project can be easily deployed and tested.

Here is a list of frameworks, libraries, and technologies used in this project:

#### Development process:

-   IntelliJ IDEA: A popular integrated development environment for Java.
-   Git: A widely used version control system for software development.
-   Maven: A build automation tool for Java projects.
-   Lombok: A Java library that helps reduce boilerplate code.

#### Architecture:

-   REST pattern: A software architecture style that allows for the creation of lightweight and scalable web services.
-   Spring: A popular framework for building Java-based web applications.
-   Spring Boot: A framework that simplifies the development of Spring-based applications.
-   Spring Security: A powerful security framework for Spring applications.
-   HATEOAS: A RESTful web service design pattern that provides links to related resources.
-   jjwt: A Java library for JSON Web Tokens.
-   Jackson: A Java library for parsing JSON data.

#### Database:

-   Hikari: A lightweight and fast JDBC connection pool.
-   Spring Data JPA: A library that simplifies the implementation of JPA repositories.
-   Hibernate: An ORM (object-relational mapping) framework for Java.
-   PostgreSQL: A popular open-source relational database management system.
-   H2: A lightweight in-memory database for testing purposes.

#### Testing:

-   JUnit: A popular testing framework for Java.
-   AssertJ: A fluent assertion library for Java.
-   Mockito: A popular Java mocking framework for unit testing.

#### Deployment:

-   Docker: A containerization platform that simplifies the deployment process.

To test the application, please refer to the readme.txt file in the docker folder for instructions on creating the Docker container. Additionally, you can use the Quiz-REST-web-app.postman_collection.json file in the resources folder to create requests. Your first request should be Login User from Auth Controller folder. You will get response that looks like this: 
![image](https://user-images.githubusercontent.com/117806124/236453971-80ce0eeb-79e6-41a0-88dc-e8cb6569f211.png)

Copy accessToken, that will be your authentification key for performing next requests. Chose request that you want to send, then go to the Auth field in Postman and Paste accessToken to the Token field. Now server will respond to you. There is an example with Get all quizzes request: 
![image](https://user-images.githubusercontent.com/117806124/236454611-3c50a95e-8aeb-4772-8331-94b1af5e3283.png)

For testing purposes accessToken won't expire, but in production time of expiration should be set to 5 minutes.

Please note that this application was primarily designed to work with PostgreSQL, but H2 was implemented for the purposes of showcasing the project on GitHub. All code for PostgreSQL is still included in the project.

<h1 align="center">
  Spring-boot API
  
  ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
  ![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
  ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
  ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
  ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
  <br>
</h1>

<p align="center">
  <a href="#introduction">Introduction</a> •
  <a href="#installation-guide">Installation Guide</a> •
  <a href="#%EF%B8%8Fproject-structure">Project Structure</a> •
  <a href="#api">API Reference</a> •
  <a href="#license">License</a> •
  <a href="#contributors">Contributors</a> 
</p>

<div align="center">

![GitHub Repo stars](https://img.shields.io/github/stars/Ctere1/spring-boot)
![GitHub forks](https://img.shields.io/github/forks/Ctere1/spring-boot)
![GitHub watchers](https://img.shields.io/github/watchers/Ctere1/spring-boot)

</div>

## ℹ️ Introduction
- This is a simple CRUD API project that uses Spring Boot, Hibernate and PostgreSQL. 
- Project started with web tutorial [here](https://bezkoder.com/spring-boot-postgresql-example/) and [spring initializr](https://start.spring.io/). Dependencies are: **Spring Web**, **Spring Data JPA**, **PostgreSQL Driver**.
- The project:
    - uses Gradle as a build tool. You can use Maven or any other build tool instead of Gradle.
    - uses PostgreSQL as a database. You can use any other database instead of PostgreSQL. You can also use an in-memory database like H2.
    - uses Hibernate as an ORM tool. You can use any other ORM tool instead of Hibernate.

## 💾Installation Guide

- To clone and run this application, you'll need [Git](https://git-scm.com), [Java](https://www.java.com/en/download/help/download_options.html), [Gradle](https://gradle.org/install/) and [PostgreSQL](https://www.postgresql.org/download/) installed on your computer.
From your command line:

    ```bash
    # Clone this repository
    $ git clone https://github.com/Ctere1/spring-boot
    # Go into the repository
    $ cd spring-boot
    # Run the app
    $ gradle bootRun
    ```

- You can change the database connection string in the `application.properties` file.
    > After these steps, you can use the API with Postman or any other API testing tool.

## 🗺️Project Structure 

- The project structure is as follows:    

  ```bash
  ├───src
  │   ├───main
  │   │   ├───java\com\tutorial\tutorial
  │   │   │   ├───controller
  │   │   │   │       └───TutorialController.java
  │   │   │   ├───model
  │   │   │   │       └───Tutorial.java
  │   │   │   └───repository
  │   │   │           └───TutorialRepository.java
  │   │   └───resources
  │   │       └───application.properties
  │   └───test
  │        └───TutorialApplicationTests.java
  │
  └───tutorial_api.postman_collection.json

  ``` 

#### What is the purpose of each folder and file?

- `src/main/java/com/tutorial/tutorial` folder contains the main application files.
- `src/main/java/com/tutorial/tutorial/controller` folder contains the controller files. These files contain the API endpoints.
- `src/main/java/com/tutorial/tutorial/model` folder contains the model files. These files contain the database models.
- `src/main/java/com/tutorial/tutorial/repository` folder contains the repository files. These files contain the interface for database operations.
- `src/main/resources` folder contains the application properties file.
- `src/test` folder contains the test files.
- `tutorial_api.postman_collection.json` file contains the postman collection for the API.


## ⚡API

### **Tutorial Endpoints**

| HTTP Verb   | Endpoint                    | Description                         |  
| :---------- | :-----------------------    |:----------------------------------  |    
| `GET`       | `/api/tutorials`            |  Returns All tutorials              |
| `GET`       | `/api/tutorials/{id}`       |  Returns the tutorial with {id}     |
| `DELETE`    | `/api/tutorials/{id}`       |  Deletes the tutorial with {id}     |
| `POST`      | `/api/tutorials`            |  Creates and returns tutorial       |
| `PUT`       | `/api/tutorials/{id}`       |  Updates and returns the tutorial   |

>**Note**   
You can query the tutorials with `title` and `published` parameters. Check the postman collection for details.



### **Tutorial Data Example**

```json
{
     "id": 3,
     "title": "Test tutorial",
     "description": "test",
     "published": false
 }
```

## ©License
![GitHub](https://img.shields.io/github/license/Ctere1/spring-boot?style=flat-square)


## 📌Contributors

<a href="https://github.com/Ctere1/">
  <img src="https://contrib.rocks/image?repo=Ctere1/Ctere1" />
</a>


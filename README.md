<h1 align="center">
  Spring-boot API  
  
   
  ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
  ![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
  ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white) <br>
  ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white) 
  ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
  ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
  <br>
</h1>

<p align="center">
  <a href="#ℹ%EF%B8%8F-introduction">Introduction</a> •
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
    - uses Spring Security and JWT for authentication and authorization. Role based authorization is implemented, Bearer token for authentication.

> [!TIP]    
> For client side, please refer to following project: [Spring Boot React](https://github.com/Ctere1/spring-boot-react) 

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

- You can change the database connection string in the `application.properties` file. And you can change the JWT secret key.
   
> [!IMPORTANT]  
> ~Before running the application, you should run the following SQL script to create the `tutorial_roles` table in your database.~  
> UPDATE: No need to run SQL script anymore. It will auto add all roles.
      
   ```sql
    INSERT INTO tutorial_roles(name) VALUES('ROLE_USER');
    INSERT INTO tutorial_roles(name) VALUES('ROLE_MODERATOR');
    INSERT INTO tutorial_roles(name) VALUES('ROLE_ADMIN');
   ```

- After these steps, you can use the API with Postman or any other API testing tool.


## 🗺️Project Structure 

- The project structure is as follows:    

  ```bash
  ├───src
  │   ├───main
  │   │   ├───java\com\tutorial\tutorial
  │   │   │   │
  │   │   │   ├───controller
  │   │   │   │
  │   │   │   ├───model
  │   │   │   │   
  │   │   │   ├───payload
  │   │   │   │   
  │   │   │   ├───security
  │   │   │   │
  │   │   │   └───repository
  │   │   │
  │   │   │
  │   │   └───resources
  │   │       └───application.properties
  │   │
  │   └───test
  │        └───TutorialApplicationTests.java
  │
  │
  └───tutorial_api.postman_collection.json

  ``` 

#### What is the purpose of each folder and file?

- `src/main/java/com/tutorial/tutorial` folder contains the main application files.
- `src/main/java/com/tutorial/tutorial/controller` folder contains the controller files. These files contain the API endpoints.
- `src/main/java/com/tutorial/tutorial/model` folder contains the model files. These files contain the database models.
- `src/main/java/com/tutorial/tutorial/repository` folder contains the repository files. These files contain the interface for database operations.
- `src/main/resources` folder contains the application properties file.
- `src/main/java/com/tutorial/tutorial/payload` folder contains the payload files. These files contain the request and response models.
- `src/main/java/com/tutorial/tutorial/security` folder contains the security files. These files contain the jwt authentication and authorization.
- `src/test` folder contains the test files.
- `Java Tutorial API.postman_collection.json` file contains the Postman collection for the API endpoints.


## ⚡API

- You can check the swagger documentation after running the server. The swagger documentation is available at `http://localhost:8080/swagger-ui/index.html#/`.
- You can perform CRUD operations with authentication and authorization with the swagger ui.  
  ![Screenshot](images/ss.png)   

### **Tutorial Endpoints**

| HTTP Verb   | Endpoint                    | Description                         | Parameters              | Body (JSON)                         |
| :---------- | :-----------------------    |:----------------------------------  | :--------------------   | :--------------------------------   | 
| `GET`       | `/api/tutorials`            |  Returns All tutorials              | `page`, `size`, `sort`  | -                                   |
| `GET`       | `/api/tutorials/{id}`       |  Returns the tutorial with {id}     | `id`                    | -                                   |
| `GET`       | `/api/sortedtutorials`      |  Returns the sorted tutorials       | `sort`                  | -                                   |
| `DELETE`    | `/api/tutorials/{id}`       |  Deletes the tutorial with {id}     | `id`                    | -                                   |
| `DELETE`    | `/api/tutorials`            |  Deletes all tutorials              |  -                      | -                                   |
| `POST`      | `/api/tutorials`            |  Creates and returns tutorial       |  -                      | `title`, `description`, `published` |
| `PUT`       | `/api/tutorials/{id}`       |  Updates and returns the tutorial   | `id`                    | `title`, `description`, `published` |

> [!NOTE]  
> You can query the tutorials with `title` and `published` parameters. Also, you can sort the tutorials with `sort` parameter. Check the [postman collection](https://github.com/Ctere1/spring-boot/blob/master/Java%20Tutorial%20API.postman_collection.json) for details.


### **Tutorial Endpoint Data Example**

> ![GET](https://img.shields.io/badge/-GET-green)    
> http://localhost:8080/api/tutorials?page=1&size=3&sort=id,asc

```json
{
    "totalItems": 2,
    "tutorials": [
        {
            "id": 2,
            "title": "Test tutorial",
            "description": "test",
            "published": true
        },
        {
            "id": 3,
            "title": "Test tutorial 2",
            "description": "test 2",
            "published": false
        }
    ],
    "totalPages": 1,
    "currentPage": 1
}
```

### **Auth Endpoints**

| HTTP Verb   | Endpoint                    | Description                         | Parameters      | Body (JSON)                             |
| :---------- | :-----------------------    |:----------------------------------  | :-------------  | :-------------------------------------  | 
| `POST`      | `/api/auth/signup`          |  Creates new user for login         | -               | `username`, `email`, `password`, `role` |
| `POST`      | `/api/auth/signin`          |  Returns the accessToken            | -               | `username`, `password`                  |


### **Auth Endpoint Data Example**

> ![GET](https://img.shields.io/badge/-POST-red)    
> http://localhost:8080/api/auth/signin | JSON Body: {"username": "cemil","password": "123456"}

```json
{
    "id": 1,
    "username": "cemil",
    "email": "test@email.com",
    "roles": [
        "ROLE_USER",
        "ROLE_ADMIN",
        "ROLE_MODERATOR"
    ],
    "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjZW1pbCIsImlhdCI6MTY5OTg5ODU3NywiZXhwIjoxNjk5OTg0OTc3fQ._tKB8Otw8Ahp0iCJrXbmHTQVw2tv8lQ2i8vn-9t79XQ",
    "tokenType": "Bearer"
}
```

## ©License
![GitHub](https://img.shields.io/github/license/Ctere1/spring-boot?style=flat-square)


## 📌Contributors

<a href="https://github.com/Ctere1/">
  <img src="https://contrib.rocks/image?repo=Ctere1/Ctere1" />
</a>


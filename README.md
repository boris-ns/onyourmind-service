# OnYourMind - service

Backend service for OnYourMind application.  
OnYourMind is a small and simple application where you can register and create new posts, see other user's posts and post comments.  
There are two types of users: regular users and admins.  

Application is developed using Spring Boot and MySQL as a database.  
Service has implemented authentication and authorization with JWT tokens.

# How to use

First, you need to clone this repository  

```git clone https://github.com/boris-ns/onyourmind-service.git```  

Then in ```src/main/resources/application.properties``` enter your database username, database password, email and email password.  

After that, you can run the service

```
cd onyourmind-service
mvn spring-boot:run
```

# API documentation
Documentation for API endpoints with request and response examples you can find [here](https://github.com/boris-ns/onyourmind-service/blob/master/docs/API_DOCS.md).

# Predefined data in the database
In the database, there are two types of users: ```ROLE_ADMIN``` and ```ROLE_USER```.  
Also, there are 3 predefined users as you can see in [import.sql file](https://github.com/boris-ns/onyourmind-service/blob/master/src/main/resources/import.sql).  

Regular users are ```john.doe``` and ```jack.smith```.  
Admin is ```jane.doe```.  
All passwords are ``123``.

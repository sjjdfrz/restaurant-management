![Restaurant Management](https://www.jotform.com/blog/wp-content/uploads/2020/07/online-ordering-for-restaurants-7cb342.png)

<h3 align="center"><b>Restaurant Management</b></h3>


# 📖 Restaurant Management

This project is about developing rest apis for an online restaurant management system.

## 🛠 Built With

* [Java](https://www.java.com/en/)
* [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
* [PostgreSQL](https://www.postgresql.org/)

## 💻 Getting Started
To get a local copy up and running, follow these steps.

### Prerequisites

In order to run this project you need:

* Java 17

  https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

* PostgreSQL

  https://www.postgresql.org/download/

* Maven

  https://maven.apache.org/download.cgi

* Postman

  https://www.postman.com/downloads/

### Setup

1) Clone this repository to your desired folder.
```
git clone https://github.com/sjjdfrz/restaurant-management.git
```

2) Create **application.yml** file.

3) Copy content of **application-prod.yml** file and paste in **application.yml** file.
4) Fill properties with your own data.


### Install
Run this command to install dependencies:
```
mvn clean install
```

### Usage
 First of all, run the program with this command:
```
mvn spring-boot:run
```

After that:

Import **restaurant_management.postman_collection.json** file in postman and do http requests with APIs.

## Concepts:

* Authentication, Authorization and Security (with JWT Token)
* CRUD operations
* Exception handling
* JPA and Hibernate
* Relationships
* Pagination
* Interceptors
* Data Validation
* Testing
* etc
# Student Management System

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-6DB33F?logo=springboot)
![Java](https://img.shields.io/badge/Java-17-007396?logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql)
![JWT](https://img.shields.io/badge/JWT-Authentication-000000?logo=jsonwebtokens)
![Swagger](https://img.shields.io/badge/Swagger-Documentation-85EA2D?logo=swagger)
![Maven](https://img.shields.io/badge/Maven-3.6-C71A36?logo=apache-maven)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)

**Live API Documentation:** [Swagger UI](http://localhost:8080/swagger-ui.html) • **OpenAPI Spec:** [v3/api-docs](http://localhost:8080/v3/api-docs)

A complete REST API for managing student information with secure role-based access control, built with Spring Boot and modern Java technologies.

</div>

## Table of Contents
- [Features](#features)
- [System Architecture](#system-architecture)
- [Tech Stack](#tech-stack)
- [Quick Start](#quick-start)
- [API Documentation](#api-documentation)
- [User Roles & Permissions](#user-roles--permissions)
- [Authentication](#authentication)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [Contributing](#contributing)

## Features

### Security & Access Control
- JWT Authentication for secure login and session management  
- Role-Based Access Control (Admin and Professor roles)  
- Branch-Level Security to restrict data access  
- Spring Security integration  

### Student Management
- Complete CRUD Operations for student records  
- Branch assignment for organizational structure  
- Email notifications for student onboarding  
- Data validation and error handling  

### Branch & User Management
- Multi-branch administration support  
- Admins can create and manage users  
- Permission-based access controls  

### Technical Features
- RESTful API design  
- MySQL database with JPA/Hibernate  
- Auto-generated Swagger documentation  
- Global exception handling  
- Optional SMTP email integration  

## System Architecture

```

Client Request
↓
Spring Security (JWT Filter)
↓
REST Controllers
↓
Service Layer (Business Logic)
↓
Repository Layer (Data Access)
↓
MySQL Database

````

### Data Flow
1. Client sends an HTTP request with JWT token in the Authorization header  
2. Spring Security validates the token and checks permissions  
3. Controller processes and validates input  
4. Service applies business logic  
5. Repository interacts with the database  
6. Response returns with data or error  

## Tech Stack

**Backend Framework**
- Spring Boot 3.2.0  
- Spring Security  
- Spring Data JPA  
- Spring Validation  
- Spring Mail  

**Database & Security**
- MySQL 8.0  
- Hibernate ORM  
- JWT (JJWT)  
- BCrypt password hashing  

**API & Documentation**
- RESTful APIs  
- Swagger/OpenAPI 3  
- Maven build tool  

**Development Tools**
- Java 17  
- Git  
- Postman  

## Quick Start

### Prerequisites
- Java 17+  
- Maven 3.6+  
- MySQL 8.0+  
- Git  

### Step 1: Clone and Setup
```bash
git clone https://github.com/your-username/student-management.git
cd student-management

# Create database
mysql -u root -p -e "CREATE DATABASE student_management;"
````

### Step 2: Configuration

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password

jwt.secret=your-super-secure-jwt-secret-key-minimum-32-characters

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

### Step 3: Run the Application

```bash
mvn clean spring-boot:run
# or
mvn clean package
java -jar target/student-management-1.0.0.jar
```

### Step 4: Verify Installation

* Application: [http://localhost:8080](http://localhost:8080)
* Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* Health Check: [http://localhost:8080/api/health](http://localhost:8080/api/health)

## API Documentation

Once the application is running:

* Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Postman Collection

For quick testing, you can import the ready-to-use Postman collection:

👉 [StudentManagementFull.postman_collection.json](./StudentManagementFull.postman_collection.json)

### Authentication Endpoints

| Method | Endpoint           | Description      | Auth Required |
| ------ | ------------------ | ---------------- | ------------- |
| POST   | `/api/auth/login`  | User login       | No            |
| GET    | `/api/auth/me`     | Get current user | Yes           |
| POST   | `/api/auth/logout` | User logout      | Yes           |

Example:

```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
```

### Student Management

| Method | Endpoint             | Description       | Admin      | Professor       |
| ------ | -------------------- | ----------------- | ---------- | --------------- |
| GET    | `/api/students`      | Get all students  | All        | Own branch      |
| POST   | `/api/students`      | Create student    | Any branch | Own branch only |
| GET    | `/api/students/{id}` | Get student by ID | All        | Own branch      |
| PUT    | `/api/students/{id}` | Update student    | All        | Own branch      |
| DELETE | `/api/students/{id}` | Delete student    | All        | Own branch      |

### Branch Management (Admin Only)

| Method | Endpoint             | Description       |
| ------ | -------------------- | ----------------- |
| GET    | `/api/branches`      | Get all branches  |
| POST   | `/api/branches`      | Create new branch |
| GET    | `/api/branches/{id}` | Get branch by ID  |
| PUT    | `/api/branches/{id}` | Update branch     |
| DELETE | `/api/branches/{id}` | Delete branch     |

### User Management (Admin Only)

| Method | Endpoint          | Description     |
| ------ | ----------------- | --------------- |
| GET    | `/api/users`      | Get all users   |
| POST   | `/api/users`      | Create new user |
| GET    | `/api/users/{id}` | Get user by ID  |
| DELETE | `/api/users/{id}` | Delete user     |

## User Roles & Permissions

**Admin**

* Full access to all data and endpoints
* Can manage branches, users, and students

**Professor**

* Limited to their assigned branch
* Cannot manage users or branches

| Action                     | Admin | Professor |
| -------------------------- | ----- | --------- |
| Manage all students        | Yes   | No        |
| Manage own branch students | Yes   | Yes       |
| Manage branches            | Yes   | No        |
| Manage users               | Yes   | No        |

## Authentication

**JWT Token Flow**

1. Login with username and password to get JWT token
2. Include token in `Authorization: Bearer <token>` header
3. Spring Security validates and authorizes requests

Example:

```bash
curl -X GET "http://localhost:8080/api/students" \
  -H "Authorization: Bearer your.jwt.token.here"
```

## Project Structure

```
student-management/
├── src/main/java/com/example/studentmanagement/
│   ├── config/
│   ├── controller/
│   ├── dto/
│   ├── entity/
│   ├── repository/
│   ├── service/
│   ├── security/
│   └── exception/
├── src/main/resources/
│   └── application.properties
├── pom.xml
└── README.md
```

## Testing

**Using Swagger UI**

1. Start the application
2. Open [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
3. Authorize using JWT token
4. Test endpoints directly

**Default Test Accounts**

```json
{
  "admin": {"username": "admin", "password": "admin123"}
}
```

## Contributing

1. Fork the repository
2. Create a branch (`git checkout -b feature/new-feature`)
3. Commit changes (`git commit -m 'Add new feature'`)
4. Push (`git push origin feature/new-feature`)
5. Create a Pull Request

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Author

* **P J Mugilan** — [GitHub](https://github.com/P-J-Mugilan)

<div align="center">

**If you find this project helpful, please [⭐ give it a star on GitHub](https://github.com/P-J-Mugilan/student-management-system-backend)!**

**Happy Coding!**

</div>

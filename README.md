# Finance Dashboard Application

## Overview
This is a Spring Boot backend application for managing financial records and generating dashboard insights. It supports user authentication, role-based access, and financial data operations.

---

## Features
- User registration and login (JWT based authentication)
- Role-based authorization
- Create, update, delete financial records
- Filter financial records
- Dashboard summary and analytics
- Global exception handling

---

## Tech Stack
- Java
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- Maven

---

## Project Structure

com.finance.finance_dashboard
│
├── controller → REST APIs
├── dto → Data Transfer Objects
├── entity → Database entities
├── repository → JPA repositories
├── services → Business logic
│ └── impl → Service implementations
├── SecurityConfig → JWT & Security configuration
└── FinanceDashboardApplication.java

---

## Main Modules

### Controllers
- User and login APIs
- Financial record APIs
- Dashboard APIs
- Filter APIs

### Services
- User service
- Financial record service
- Dashboard summary service
- Filter service

### Security
- JWT authentication
- Custom user details service
- Security configuration

---

## API Functionalities
- User login and authentication
- Manage financial records
- Filter records by criteria
- View dashboard summaries

---

## How to Run
1. Clone the repository
2. Open in IDE (IntelliJ recommended)
3. Run: FinanceDashboardApplication.java


---

## Notes
- Uses JWT for securing APIs
- Uses role-based access control
- Standard layered architecture followed
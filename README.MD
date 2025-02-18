# Babbangona Commons Library

Babbangona Commons Library is a reusable set of components designed for microservices architectures. It centralizes common functionalities—including security utilities, JWT handling, shared DTOs, and exception handling—that can be used by multiple services (such as User Management and Tenant Management).

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
    - [Security Integration](#security-integration)
    - [Using JWT Utilities](#using-jwt-utilities)
- [Configuration](#configuration)
- [Building the Library](#building-the-library)
- [Contributing](#contributing)
- [License](#license)

## Features

- **JWT Utilities:**  
  Generate and validate JSON Web Tokens (JWT) for stateless authentication.

- **Custom Security Filters:**
    - `JwtAuthenticationFilter`: Authenticates users using username and password and issues JWTs.
    - `JwtAuthorizationFilter`: Intercepts incoming requests to validate JWTs and enforce role-based access control.

- **Spring Security Configurations:**  
  Predefined configuration classes that set up Spring Security 6 for microservices, permitting public access to authentication endpoints and securing all other routes.

- **Shared DTOs and Exceptions:**  
  Common data transfer objects and custom exception classes to provide consistent responses across your services.

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Spring Boot 3.x

## Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/4thworldsoldier/babbangona-commons-library.git
   cd babbangona-commons-library

2. **Configure your project and build the lib:**
   To use Babbangona Commons Library as a dependency in your projects, add it to your `pom.xml`:

```xml
<dependency>
    <groupId>com.babbangona.commons</groupId>
    <artifactId>babbangona-commons-library</artifactId>
    <version>0.0.1</version>
</dependency>

Run the maven Build command 
mvn clean install

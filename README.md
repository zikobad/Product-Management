
# Product Management API

This repository contains a Product Management application developed using Java 17 and Spring Boot 3. The application provides functionalities to manage products, including creating, retrieving, updating, and deleting products.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Testing the API with Postman](#testing-the-api-with-postman)
- [Unit Tests](#unit-tests)

## Features
- Retrieve a list of all products with pagination support.
- Retrieve a specific product by its ID.
- Create a new product with detailed attributes.
- Update an existing product by its ID.
- Delete a product by its ID.
- Handle cases where a product is not found with appropriate error responses.

## Technologies Used
- Java 17
- Spring Boot 3
- PostgreSQL
- Maven
- JUnit (for unit testing)

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/product-management-api.git
   ```

2. Navigate to the project directory:
   ```bash
   cd product-management-api
   ```

3. Install dependencies:
   ```bash
   mvn install
   ```

4. Set up your PostgreSQL database and configure the connection in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/yourdbname
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword
   ```

## Running the Application

To run the application, use the following command:
```bash
mvn spring-boot:run
```

The application should start on `http://localhost:8081`.

## Testing the API with Postman

You can use Postman to test the API endpoints. Follow these steps:

1. **Import the Postman Collection**:
   - Download the Postman collection JSON file from the `postman_collections` folder in this repository.
   - Open Postman and select "Import" to add the collection.

2. **API Endpoints**:
   - **GET /products**: Retrieve all products (pagination supported).
   - **GET /products/{id}**: Retrieve a product by ID. Returns a 404 if the product is not found.
   - **POST /products**: Create a new product. Send a JSON body with product details.
   - **PUT /products/{id}**: Update an existing product by ID. Only the fields provided will be updated.
   - **DELETE /products/{id}**: Delete a product by ID.

3. **Testing**:
   - Run the requests in Postman to test each endpoint. Ensure you check for the correct status codes and responses.

## Unit Tests

Unit tests have been created for the `ProductService` to ensure functionality. You can run the tests using:
```bash
mvn test
```


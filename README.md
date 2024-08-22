# Spring Pharmacy CRUD

This project is a basic CRUD application for managing a pharmacy, developed in Java using the Spring Framework. It was created as part of the Generation Brasil bootcamp and serves as a demonstration of backend development skills using Spring Boot.

## Features

- **Product and Category Management**: The project allows creating, reading, updating, and deleting (CRUD) products and categories within the pharmacy.
- **Relationships**: Implementation of `OneToMany` and `ManyToOne` relationships between Category and Product entities.
- **Validations**: Includes basic validations to ensure data integrity.

## Technologies Used

- **Java**: The programming language used.
- **Spring Boot**: The primary framework for developing the application.
- **Spring Data JPA**: For persistence management and database operations.
- **MySQL**: The database used to store pharmacy data.
- **Maven**: Dependency management and build tool.

## How to Run the Project

### Prerequisites

- **JDK 11 or higher**
- **MySQL** (or another compatible database)
- **Maven**

### Steps to Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/FelipeAJdev/spring-farmacia-crud.git
   cd spring-farmacia-crud
   ```

2. **Configure the database**:
   - Create a MySQL database named `db_crudfarmacia`.
   - Configure the database credentials in the `application.properties` file.

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**:
   - The application will be available at `http://localhost:8080`.

## Endpoints

### Category

- **GET /categories**: Returns all categories.
- **POST /categories**: Creates a new category.
- **PUT /categories/{id}**: Updates an existing category.
- **DELETE /categories/{id}**: Deletes an existing category.

### Product

- **GET /products**: Returns all products.
- **POST /products**: Creates a new product.
- **PUT /products/{id}**: Updates an existing product.
- **DELETE /products/{id}**: Deletes an existing product.

## Contribution

Feel free to open issues and pull requests for improvements and fixes.

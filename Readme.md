Perfect 🚀 Since you already have **Java 17**, **Maven 3.9.9**, and **MongoDB** installed, I’ll guide you step by step to create a **Spring Boot + MongoDB CRUD project from scratch**.

We’ll build a simple **Employee Management CRUD API**.

---

## 1. Create a Spring Boot Project

Run this command in your terminal:

```bash
mvn archetype:generate -DgroupId=com.example -DartifactId=springboot-mongodb-crud -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

Or easier: use **Spring Initializr** (recommended):
👉 Go to [https://start.spring.io](https://start.spring.io)

* Project: **Maven**
* Language: **Java**
* Spring Boot: **3.x** (works with Java 17)
* Dependencies: **Spring Web**, **Spring Data MongoDB**
* Group: `com.example`
* Artifact: `springboot-mongodb-crud`

Download & extract → open in IntelliJ/Eclipse/VS Code.

---

## 2. Add Dependencies (if not already added)

In your `pom.xml`:

```xml
<dependencies>
    <!-- Web dependency -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!-- MongoDB dependency -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>

    <!-- Lombok (optional, to reduce boilerplate code) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

Run:

```bash
mvn clean install
```

---

## 3. Configure MongoDB in `application.properties`

In `src/main/resources/application.properties`:

```properties
spring.application.name=springboot-mongodb-crud
server.port=8080

# MongoDB configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=employee_db
```

---

## 4. Create Model Class

`src/main/java/com/example/springbootmongodbcrud/model/Employee.java`

```java
package com.example.springbootmongodbcrud.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "employees")
public class Employee {

    @Id
    private String id;
    private String name;
    private String email;
    private String department;
}
```

---

## 5. Create Repository Interface

`src/main/java/com/example/springbootmongodbcrud/repository/EmployeeRepository.java`

```java
package com.example.springbootmongodbcrud.repository;

import com.example.springbootmongodbcrud.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
}
```

---

## 6. Create Service Layer

`src/main/java/com/example/springbootmongodbcrud/service/EmployeeService.java`

```java
package com.example.springbootmongodbcrud.service;

import com.example.springbootmongodbcrud.model.Employee;
import com.example.springbootmongodbcrud.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> getAll() {
        return repository.findAll();
    }

    public Employee getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Employee update(String id, Employee employee) {
        Employee existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(employee.getName());
            existing.setEmail(employee.getEmail());
            existing.setDepartment(employee.getDepartment());
            return repository.save(existing);
        }
        return null;
    }

    public String delete(String id) {
        repository.deleteById(id);
        return "Employee with id " + id + " deleted.";
    }
}
```

---

## 7. Create REST Controller

`src/main/java/com/example/springbootmongodbcrud/controller/EmployeeController.java`

```java
package com.example.springbootmongodbcrud.controller;

import com.example.springbootmongodbcrud.model.Employee;
import com.example.springbootmongodbcrud.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return service.save(employee);
    }

    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        return service.update(id, employee);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        return service.delete(id);
    }
}
```

---

## 8. Run the Application

Run:

```bash
mvn spring-boot:run
```

OR run the main class `SpringbootMongodbCrudApplication.java`.

---

## 9. Test with Postman / curl

* **Create Employee (POST)**
  `POST http://localhost:8080/api/employees`

  ```json
  {
    "name": "John Doe",
    "email": "john@example.com",
    "department": "IT"
  }
  ```

* **Get All Employees (GET)**
  `GET http://localhost:8080/api/employees`

* **Get Employee by Id (GET)**
  `GET http://localhost:8080/api/employees/{id}`

* **Update Employee (PUT)**
  `PUT http://localhost:8080/api/employees/{id}`

* **Delete Employee (DELETE)**
  `DELETE http://localhost:8080/api/employees/{id}`

---

✅ Now you have a **Spring Boot + MongoDB CRUD API** ready.
package com.example.springbootmongodbcrud.repository;

import com.example.springbootmongodbcrud.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void save_and_findAll_and_findById() {
        Employee e = new Employee();
        e.setName("Alice_cmd");
        e.setEmail("alice@example.com");
        e.setDepartment("IT");

        Employee saved = repository.save(e);
        assertNotNull(saved.getId());

        List<Employee> all = repository.findAll();
        assertEquals(1, all.size());
        assertEquals("Alice_cmd", all.get(0).getName());

        Optional<Employee> byId = repository.findById(saved.getId());
        assertTrue(byId.isPresent());
        assertEquals("alice@example.com", byId.get().getEmail());
    }

    @Test
    void deleteById_removesDocument() {
        Employee e = new Employee();
        e.setName("Bob");
        e.setEmail("bob@example.com");
        e.setDepartment("HR");

        Employee saved = repository.save(e);
        String id = saved.getId();

        repository.deleteById(id);

        assertFalse(repository.findById(id).isPresent());
        assertEquals(0, repository.findAll().size());
    }
}

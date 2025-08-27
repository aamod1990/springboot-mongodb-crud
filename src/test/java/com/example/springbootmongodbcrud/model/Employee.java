package com.example.springbootmongodbcrud.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testEmployeeGettersAndSetters() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setName("John Doe");
        employee.setEmail("john@example.com");
        employee.setDepartment("IT");

        assertEquals("1", employee.getId());
        assertEquals("John Doe", employee.getName());
        assertEquals("john@example.com", employee.getEmail());
        assertEquals("IT", employee.getDepartment());
    }

    @Test
    void testEmployeeEqualsAndHashCode() {
        Employee emp1 = new Employee();
        emp1.setId("1");
        emp1.setName("John Doe");
        emp1.setEmail("john@example.com");
        emp1.setDepartment("IT");

        Employee emp2 = new Employee();
        emp2.setId("1");
        emp2.setName("John Doe");
        emp2.setEmail("john@example.com");
        emp2.setDepartment("IT");

        assertEquals(emp1, emp2); // Lombok @Data generates equals/hashCode
        assertEquals(emp1.hashCode(), emp2.hashCode());
    }

    @Test
    void testEmployeeToString() {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setName("John Doe");
        employee.setEmail("john@example.com");
        employee.setDepartment("IT");

        String toString = employee.toString();
        assertTrue(toString.contains("John Doe"));
        assertTrue(toString.contains("john@example.com"));
        assertTrue(toString.contains("IT"));
    }
}

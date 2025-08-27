package com.example.springbootmongodbcrud.service;

import com.example.springbootmongodbcrud.model.Employee;
import com.example.springbootmongodbcrud.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    private Employee employee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee = new Employee();
        employee.setId("1");
        employee.setName("John Doe");
        employee.setEmail("john@example.com");
        employee.setDepartment("IT");
    }

    @Test
    void testSaveEmployee() {
        when(repository.save(any(Employee.class))).thenReturn(employee);

        Employee saved = service.save(employee);

        assertNotNull(saved);
        assertEquals("John Doe", saved.getName());
        verify(repository, times(1)).save(employee);
    }

    @Test
    void testGetAllEmployees() {
        when(repository.findAll()).thenReturn(Arrays.asList(employee));

        List<Employee> employees = service.getAll();

        assertEquals(1, employees.size());
        assertEquals("John Doe", employees.get(0).getName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById_Found() {
        when(repository.findById("1")).thenReturn(Optional.of(employee));

        Employee found = service.getById("1");

        assertNotNull(found);
        assertEquals("John Doe", found.getName());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(repository.findById("2")).thenReturn(Optional.empty());

        Employee found = service.getById("2");

        assertNull(found);
        verify(repository, times(1)).findById("2");
    }

    @Test
    void testUpdateEmployee_Found() {
        Employee updated = new Employee();
        updated.setName("Jane Doe");
        updated.setEmail("jane@example.com");
        updated.setDepartment("HR");

        when(repository.findById("1")).thenReturn(Optional.of(employee));
        when(repository.save(any(Employee.class))).thenReturn(updated);

        Employee result = service.update("1", updated);

        assertNotNull(result);
        assertEquals("Jane Doe", result.getName());
        assertEquals("HR", result.getDepartment());
        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_NotFound() {
        Employee updated = new Employee();
        updated.setName("Jane Doe");

        when(repository.findById("2")).thenReturn(Optional.empty());

        Employee result = service.update("2", updated);

        assertNull(result);
        verify(repository, times(1)).findById("2");
        verify(repository, never()).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(repository).deleteById("1");

        String message = service.delete("1");

        assertEquals("Employee with id 1 deleted.", message);
        verify(repository, times(1)).deleteById("1");
    }
}

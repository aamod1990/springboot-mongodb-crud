package com.example.springbootmongodbcrud.controller;

import com.example.springbootmongodbcrud.model.Employee;
import com.example.springbootmongodbcrud.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService service;

    @Autowired
    private ObjectMapper objectMapper; // for JSON serialization

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setId("1");
        employee.setName("John Doe");
        employee.setDepartment("IT");
    }

    @Test
    void testCreateEmployee() throws Exception {
        Mockito.when(service.save(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(employee);
        Mockito.when(service.getAll()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Mockito.when(service.getById("1")).thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee updated = new Employee();
        updated.setId("1");
        updated.setName("Jane Doe");
        updated.setDepartment("HR");

        Mockito.when(service.update(eq("1"), any(Employee.class))).thenReturn(updated);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.department").value("HR"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        Mockito.when(service.delete("1")).thenReturn("Deleted");

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }
}

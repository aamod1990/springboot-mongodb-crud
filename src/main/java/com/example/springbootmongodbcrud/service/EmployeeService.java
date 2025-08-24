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

package com.DM.dairyManagement.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DM.dairyManagement.model.Employee;
import com.DM.dairyManagement.model.OtherExpense;
import com.DM.dairyManagement.repository.EmployeeRepository;
import com.DM.dairyManagement.repository.OtherExpenseRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@Service
public class ExpenseService {
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private OtherExpenseRepository otherExpenseRepository;
    
    // Employee methods
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    // Other expense methods
    public List<OtherExpense> getAllOtherExpenses() {
        return otherExpenseRepository.findAll();
    }
    
    public OtherExpense saveOtherExpense(OtherExpense otherExpense) {
        return otherExpenseRepository.save(otherExpense);
    }
    
    // Total calculations
    public Double getTotalEmployeeExpenses() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }
    
    public Double getTotalOtherExpenses() {
        List<OtherExpense> expenses = otherExpenseRepository.findAll();
        return expenses.stream()
                .mapToDouble(OtherExpense::getTotalAmount)
                .sum();
    }
    
    public Double getGrandTotal() {
        return getTotalEmployeeExpenses() + getTotalOtherExpenses();
    }
    
    public void deleteOtherExpense(Long id) {
        otherExpenseRepository.deleteById(id);
    }

    
    public void deleteEmployee(Long employeeId) {
        // Ensure the employee exists before attempting to delete
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
        } else {
            throw new EntityNotFoundException("Employee with ID " + employeeId + " not found.");
        }
    }
}


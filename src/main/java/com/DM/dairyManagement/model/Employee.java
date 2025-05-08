package com.DM.dairyManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double salary;
    private Double advanceSalary;
    private Double remainingSalary;

    public Employee() {}

    public Employee(String name, Double salary, Double advanceSalary) {
        this.name = name;
        this.salary = salary;
        this.advanceSalary = advanceSalary;
        calculateRemainingSalary();
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
        calculateRemainingSalary();
    }

    public Double getAdvanceSalary() {
        return advanceSalary;
    }

    public void setAdvanceSalary(Double advanceSalary) {
        this.advanceSalary = advanceSalary;
        calculateRemainingSalary();
    }

    public Double getRemainingSalary() {
        return remainingSalary;
    }

    public void setRemainingSalary(Double remainingSalary) {
        this.remainingSalary = remainingSalary;
    }

    private void calculateRemainingSalary() {
        if (this.salary != null && this.advanceSalary != null) {
            this.remainingSalary = this.salary - this.advanceSalary;
        }
    }
}

package com.DM.dairyManagement.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private double ltr;
    private double kg;

    // Getters and Setters
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

    public double getLtr() {
        return ltr;
    }

    public void setLtr(double ltr) {
        this.ltr = ltr;
    }

    public double getKg() {
        return kg;
    }

    public void setKg(double kg) {
        this.kg = kg;
    }

    @Override
    public String toString() {
        return "Unit [id=" + id + ", name=" + name + ", ltr=" + ltr + ", kg=" + kg + "]";
    }
}
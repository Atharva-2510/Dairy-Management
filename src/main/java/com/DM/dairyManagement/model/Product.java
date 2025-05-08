package com.DM.dairyManagement.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private Long companyPrice;
    private Long retailerPrice;
    private Long wholesalerPrice;
    private Long customerPrice;
    private String description;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit1;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stockList = new ArrayList<>();

    // Constructors
    public Product() {}

    public Product(Long id, String name, Long companyPrice, Long retailerPrice, Long wholesalerPrice,
                   Long customerPrice, String description, Unit unit1, Company company, List<Stock> stockList) {
        this.id = id;
        this.name = name;
        this.companyPrice = companyPrice;
        this.retailerPrice = retailerPrice;
        this.wholesalerPrice = wholesalerPrice;
        this.customerPrice = customerPrice;
        this.description = description;
        this.unit1 = unit1;
        this.company = company;
        this.stockList = stockList;
    }

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

    public Long getCompanyPrice() {
        return companyPrice;
    }

    public void setCompanyPrice(Long companyPrice) {
        this.companyPrice = companyPrice;
    }

    public Long getRetailerPrice() {
        return retailerPrice;
    }

    public void setRetailerPrice(Long retailerPrice) {
        this.retailerPrice = retailerPrice;
    }

    public Long getWholesalerPrice() {
        return wholesalerPrice;
    }

    public void setWholesalerPrice(Long wholesalerPrice) {
        this.wholesalerPrice = wholesalerPrice;
    }

    public Long getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(Long customerPrice) {
        this.customerPrice = customerPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Unit getUnit1() {
        return unit1;
    }

    public void setUnit1(Unit unit1) {
        this.unit1 = unit1;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }
//    
//    public void deductStock(Long quantitySold) {
//        for (Stock stock : stockList) {
//            if (stock.getQuantity() >= quantitySold) {
//                stock.setQuantity(stock.getQuantity() - quantitySold);
//                return;
//            }
//        }
//        throw new RuntimeException("Not enough stock available for product: " + this.name);
//    }
}

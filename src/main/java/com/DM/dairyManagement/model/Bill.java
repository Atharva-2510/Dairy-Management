package com.DM.dairyManagement.model;

import jakarta.persistence.*;

@Entity
public class Bill {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY) // Ensure auto-increment
	private Long id;
    private long billNo;
    private String fullName;
    private String date;
    private String mobileNumber;
    private String customerType;
    
    private String item;
    private int qty;
    private double price;
    private double subtotal;
    
    private double cgst;
    private double sgst;
    private double discount;
    
    private double total;
    private double paidAmount;
    private double balanceDue;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public long getBillNo() { return billNo; }
    public void setBillNo(long billNo) { this.billNo = billNo; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getCustomerType() { return customerType; }
    public void setCustomerType(String customerType) { this.customerType = customerType; }

    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getCgst() { return cgst; }
    public void setCgst(double cgst) { this.cgst = cgst; }

    public double getSgst() { return sgst; }
    public void setSgst(double sgst) { this.sgst = sgst; }

    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(double paidAmount) { this.paidAmount = paidAmount; }

    public double getBalanceDue() { return balanceDue; }
    public void setBalanceDue(double balanceDue) { this.balanceDue = balanceDue; }
}

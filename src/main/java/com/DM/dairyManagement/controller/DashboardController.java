package com.DM.dairyManagement.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.service.BillService;
import com.DM.dairyManagement.repository.BillRepository;
import com.DM.dairyManagement.repository.ProductRepository;
import com.DM.dairyManagement.repository.StockRepository;

@Controller
public class DashboardController {

    @Autowired
    private BillService billService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/createBill")
    public String showBillForm(Model model) {
        model.addAttribute("bill", new Bill());
        model.addAttribute("products", productRepository.findAll());
        return "createBill";
    }

    @PostMapping("/createBill")
    public String saveBill(@ModelAttribute Bill bill, RedirectAttributes redirectAttributes) {
        if (Objects.isNull(bill.getQty()) || Objects.isNull(bill.getPrice())) {
            redirectAttributes.addFlashAttribute("error", "Quantity and Price cannot be empty");
            return "redirect:/createBill";
        }

        // Step 1: Update stock before saving bill
        stockRepository.findStockByProductNameIgnoreCase(bill.getItem()).ifPresentOrElse(stock -> {
            int newQty = stock.getQuantity() - bill.getQty();
            if (newQty < 0) newQty = 0; // Optional: Prevent negative stock
            stock.setQuantity(newQty);
            stockRepository.save(stock);
        }, () -> {
            redirectAttributes.addFlashAttribute("error", "Stock not found for product: " + bill.getItem());
        });

        // Step 2: Save Bill
        calculateBillFields(bill);
        billRepository.save(bill);

        redirectAttributes.addFlashAttribute("success", "Bill saved successfully and stock updated!");
        return "redirect:/listBill";
    }

    @PostMapping("/update-bill")
    public String updateBill(@ModelAttribute Bill bill, RedirectAttributes redirectAttributes) {
        if (Objects.isNull(bill.getQty()) || Objects.isNull(bill.getPrice())) {
            redirectAttributes.addFlashAttribute("error", "Quantity and Price cannot be empty");
            return "redirect:/edit-bill/" + bill.getId();
        }

        calculateBillFields(bill);
        billRepository.save(bill);

        redirectAttributes.addFlashAttribute("success", "Bill updated successfully!");
        return "redirect:/listBill";
    }

    private void calculateBillFields(Bill bill) {
        double subtotal = bill.getQty() * bill.getPrice();
        bill.setSubtotal(roundToTwoDecimalPlaces(subtotal));

        double cgst = Objects.nonNull(bill.getCgst()) ? bill.getCgst() : 0;
        double sgst = Objects.nonNull(bill.getSgst()) ? bill.getSgst() : 0;
        double discount = Objects.nonNull(bill.getDiscount()) ? bill.getDiscount() : 0;
        double paidAmount = Objects.nonNull(bill.getPaidAmount()) ? bill.getPaidAmount() : 0;

        double cgstAmount = (cgst * subtotal) / 100;
        double sgstAmount = (sgst * subtotal) / 100;
        double total = subtotal + cgstAmount + sgstAmount - discount;
        double balanceDue = total - paidAmount;

        bill.setTotal(roundToTwoDecimalPlaces(total));
        bill.setBalanceDue(roundToTwoDecimalPlaces(balanceDue));
    }

    private double roundToTwoDecimalPlaces(Double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @GetMapping("/listBill")
    public String viewBills(Model model) {
        List<Bill> bills = billService.getAllBills();
        model.addAttribute("bills", bills);
        return "listBill";
    }

    @GetMapping("/PrintBill/{id}")
    public String printBill(@PathVariable Long id, Model model) {
        Bill bill = billService.getBillById(id);
        model.addAttribute("bill", bill);
        return "printBill";
    }

    @GetMapping("/next-bill-no")
    @ResponseBody
    public Long getNextBillNo() {
        return billService.getNextBillNo();
    }

    @PostMapping("/delete-bill/{id}")
    public String deleteBill(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            billService.deleteBillById(id);
            redirectAttributes.addFlashAttribute("success", "Bill deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting bill: " + e.getMessage());
        }
        return "redirect:/listBill";
    }

    @GetMapping("/edit-bill/{id}")
    public String editBill(@PathVariable Long id, Model model) {
        Bill bill = billService.getBillById(id);
        model.addAttribute("bill", bill);
        model.addAttribute("products", productRepository.findAll());
        return "editBill";
    }

    @GetMapping("/customers")
    public String showCustomerBills(Model model) {
        List<Bill> customerBills = billService.getBillsByType("customer");
        model.addAttribute("bills", customerBills);
        return "customer-bills";
    }

    @GetMapping("/retailers")
    public String showRetailerBills(Model model) {
        List<Bill> retailerBills = billService.getBillsByType("retailer");
        model.addAttribute("bills", retailerBills);
        return "retailer-bills";
    }

    @GetMapping("/wholesalers")
    public String showWholesalerBills(Model model) {
        List<Bill> wholesalerBills = billService.getBillsByType("wholesaler");
        model.addAttribute("bills", wholesalerBills);
        return "wholesaler-bills";
    }
}

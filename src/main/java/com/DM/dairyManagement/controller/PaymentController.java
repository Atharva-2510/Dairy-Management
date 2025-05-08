package com.DM.dairyManagement.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.model.Payment;
import com.DM.dairyManagement.model.PaymentHistory;
import com.DM.dairyManagement.repository.PaymentRepository;
import com.DM.dairyManagement.service.BillService;
import com.DM.dairyManagement.service.PaymentService;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private BillService billService;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @GetMapping("/form")
    public String showPaymentForm(Model model) {
        List<Bill> bills = billService.getAllBills();
        model.addAttribute("customers", bills);
        model.addAttribute("bills", bills);
        return "payment_form";
    }
    
    @PostMapping("/add")
    public String processPayment(
            @RequestParam Long billId,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false, defaultValue = "0.00") BigDecimal paidAmount,
            @RequestParam(required = false, defaultValue = "0.00") BigDecimal dueAmount,
            @RequestParam String paymentMethod,
            @RequestParam(required = false) String accountNo,
            @RequestParam(required = false) String creditCardNo,
            @RequestParam(required = false) String upiId,
            Model model) {

        Bill bill = billService.getBillById(billId);
        if (bill == null) {
            model.addAttribute("error", "Bill not found for ID: " + billId);
            return "payment_form";
        }

        String customerName = bill.getFullName();

        String finalAccountNo = "Bank Transfer".equals(paymentMethod) ? accountNo : null;
        String finalCreditCardNo = "Credit Card".equals(paymentMethod) ? creditCardNo : null;
        String finalUpiId = "UPI".equals(paymentMethod) ? upiId : null;

        Payment payment = new Payment(billId, amount, paidAmount, dueAmount, 
                                      paymentMethod, finalAccountNo, finalCreditCardNo, 
                                      finalUpiId, Payment.PaymentStatus.COMPLETED);
        payment.setName(customerName);
        
        paymentService.savePayment(payment);

        model.addAttribute("message", "Payment successful!");
        return "redirect:/payments/list"; 
    }
    
    @GetMapping("/list")
    public String viewPayments(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            Model model) {
        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        
        // Get payment history
        List<PaymentHistory> paymentHistory;
        if (keyword != null || fromDate != null || toDate != null) {
            paymentHistory = paymentService.searchPaymentHistory(keyword, fromDate, toDate);
        } else {
            paymentHistory = paymentService.getLatestPaymentHistory();
        }
        model.addAttribute("paymentHistory", paymentHistory);
        
        return "payment_list";
    }
    
    @GetMapping("/history")
    public String viewPaymentHistory(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            Model model) {
        
        List<PaymentHistory> paymentHistory;
        if (keyword != null || fromDate != null || toDate != null) {
            paymentHistory = paymentService.searchPaymentHistory(keyword, fromDate, toDate);
        } else {
            paymentHistory = paymentService.getAllPaymentHistory();
        }
        
        model.addAttribute("paymentHistory", paymentHistory);
        return "payment_history";
    }
    
    @GetMapping("/billDetails/{customerName}")
    @ResponseBody
    public List<Bill> getBillDetailsByCustomerName(@PathVariable String customerName) {
        return billService.findBillByCustomerName(customerName);
    }
    
    @GetMapping("/edit/{id}")
    public String editPayment(@PathVariable("id") Long id, Model model) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            model.addAttribute("payment", payment);
            model.addAttribute("amount", payment.getAmount());
            model.addAttribute("paidAmount", payment.getPaidAmount());
            model.addAttribute("dueAmount", payment.getDueAmount());
        }
        return "editPayment";
    }
    
    @PostMapping("/update")
    public String updatePayment(@ModelAttribute Payment payment) {
        paymentService.updatePayment(payment);
        return "redirect:/payments/list";
    }
    
    @PostMapping("/payments/add")
    public String addPayment(@ModelAttribute Payment payment, RedirectAttributes redirectAttributes) {
        try {
            paymentService.save(payment);
            redirectAttributes.addFlashAttribute("success", "Payment added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving payment.");
        }
        return "redirect:/payments/list";
    }
    
    @GetMapping("/history/{paymentId}")
    public String viewPaymentHistoryForPayment(@PathVariable Long paymentId, Model model) {
        List<PaymentHistory> paymentHistory = paymentService.getPaymentHistoryByPaymentId(paymentId);
        model.addAttribute("paymentHistory", paymentHistory);
        model.addAttribute("paymentId", paymentId);
        return "payment_history_by_id";
    }
}
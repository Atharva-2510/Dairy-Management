//package com.DM.dairyManagement.controller;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.DM.dairyManagement.model.Bill;
//import com.DM.dairyManagement.service.BillService;
//
//@Controller
//public class BillController {
//
//    @Autowired
//    private BillService billService;
//
////    @GetMapping("/listBill")
////    public String getBillList(Model model) {
////        List<Bill> bills = billService.getAllBills(); // Fetch data from the service
////        model.addAttribute("bills", bills); // Add bills to the model
////        return "billList"; // Return the Thymeleaf template (billList.html)
////    }
//    
//    @PostMapping("/delete-bill/{id}")
//    public String deleteBill(@PathVariable Long id) {
//        // Call the service method to delete the bill by ID
//        billService.deleteBillById(id);
//        // Redirect to the bill list page after deletion
//        return "redirect:/listBill";
//    }
//    
//    
//}

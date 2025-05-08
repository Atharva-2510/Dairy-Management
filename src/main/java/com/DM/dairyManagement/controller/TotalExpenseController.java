package com.DM.dairyManagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/total-expenses")
public class TotalExpenseController {
    
    @Autowired
    private com.DM.dairyManagement.service.ExpenseService expenseService;
    
    @GetMapping
    public String totalExpensesPage(Model model) {
        // Add all data for the total expenses page
        model.addAttribute("allEmployees", expenseService.getAllEmployees());
        model.addAttribute("allOtherExpenses", expenseService.getAllOtherExpenses());
        model.addAttribute("totalEmployeeExpenses", expenseService.getTotalEmployeeExpenses());
        model.addAttribute("totalOtherExpenses", expenseService.getTotalOtherExpenses());
        model.addAttribute("grandTotal", expenseService.getGrandTotal());
        return "totalExpenses";
    }
}

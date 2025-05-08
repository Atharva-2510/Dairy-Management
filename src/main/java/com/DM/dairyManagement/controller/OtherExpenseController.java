package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.OtherExpense;
import com.DM.dairyManagement.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/other-expenses")
public class OtherExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public String otherExpensesPage(Model model) {
        model.addAttribute("otherExpense", new OtherExpense());
        model.addAttribute("allOtherExpenses", expenseService.getAllOtherExpenses());
        model.addAttribute("totalOtherExpenses", expenseService.getTotalOtherExpenses());
        return "otherExpenses";
    }

    @PostMapping("/save")
    public String saveOtherExpense(@ModelAttribute OtherExpense otherExpense) {
        if (otherExpense.getDate() == null) {
            otherExpense.setDate(LocalDate.now());
        }
        otherExpense.setTotalAmount(
            (otherExpense.getLightBill() != null ? otherExpense.getLightBill() : 0) +
            (otherExpense.getDieselExpense() != null ? otherExpense.getDieselExpense() : 0) +
            (otherExpense.getShopRent() != null ? otherExpense.getShopRent() : 0) +
            (otherExpense.getOtherExpense() != null ? otherExpense.getOtherExpense() : 0)
        );
        expenseService.saveOtherExpense(otherExpense);
        return "redirect:/other-expenses";
    }

    @GetMapping("/delete")
    public String deleteOtherExpense(@RequestParam Long id) {
        expenseService.deleteOtherExpense(id);
        return "redirect:/other-expenses";
    }
}

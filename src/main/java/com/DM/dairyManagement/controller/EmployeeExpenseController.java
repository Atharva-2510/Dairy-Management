package com.DM.dairyManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DM.dairyManagement.model.Employee;
import com.DM.dairyManagement.service.ExpenseService;

@Controller
@RequestMapping("/employee-expenses")
public class EmployeeExpenseController {
    
    @Autowired
    private ExpenseService expenseService;
    
    @GetMapping
    public String employeeExpensesPage(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("allEmployees", expenseService.getAllEmployees());
        model.addAttribute("totalEmployeeExpenses", expenseService.getTotalEmployeeExpenses());
        return "EmployeeExpenses";
    }
    
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes) {
        // Optional: validation for advance greater than salary
        if (employee.getAdvanceSalary() != null && employee.getSalary() != null &&
            employee.getAdvanceSalary() > employee.getSalary()) {
            redirectAttributes.addFlashAttribute("error", "Advance salary cannot be greater than salary.");
            return "redirect:/employee-expenses";
        }

        // No need to manually set remainingSalary; handled in model
        expenseService.saveEmployee(employee);
        redirectAttributes.addFlashAttribute("success", "Employee saved successfully.");
        return "redirect:/employee-expenses";
    }
    
    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam Long id) {
        expenseService.deleteEmployee(id);
        return "redirect:/employee-expenses";
    }

}

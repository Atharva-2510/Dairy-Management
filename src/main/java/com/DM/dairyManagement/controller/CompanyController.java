package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.Company;
import com.DM.dairyManagement.model.Retailer; // ✅ <-- Add this line
import com.DM.dairyManagement.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    // Display all companies
    @GetMapping
    public String listCompanies(Model model) {
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("companies", companies);
        return "companyList"; // View displaying all companies
    }

    // Show form to add a new company
    @GetMapping("/new")
    public String showCompanyForm(Model model) {
        model.addAttribute("company", new Company());
        return "companyForm"; // View for adding a new company
    }

    // Save a new company
    @PostMapping
    public String saveCompany(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/companies"; // Redirect to company list
    }

    // Delete a company
    @PostMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Long id) {
        companyRepository.deleteById(id);
        return "redirect:/companies"; // Redirect to company list after deletion
    }

    // Show form to edit a company
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company id: " + id));
        model.addAttribute("company", company);
        return "companyEditForm"; // View for editing the company
    }

    // Save the edited company
    @PostMapping("/edit/{id}")
    public String editCompany(@PathVariable Long id, @ModelAttribute Company company) {
        company.setId(id); // Set the ID to update the existing company
        companyRepository.save(company); // Save the updated company
        return "redirect:/companies"; // Redirect to company list after saving
    }

    // Show form to add a retailer (probably not needed in CompanyController)
    @GetMapping("/add")
    public String showAddRetailerForm(Model model) {
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("retailer", new Retailer());
        model.addAttribute("companies", companies);
        return "addRetailer";  // View for adding a retailer
    }
}
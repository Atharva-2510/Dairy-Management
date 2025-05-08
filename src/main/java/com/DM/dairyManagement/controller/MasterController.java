package com.DM.dairyManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DM.dairyManagement.model.Master;
import com.DM.dairyManagement.repository.MasterRepository;


import jakarta.validation.Valid;

@Controller
public class MasterController {

    @Autowired
    private MasterRepository masterRepository;

    // List Masters
    @GetMapping("/listMaster")
    public String listMasters(Model model) {
        model.addAttribute("masters", masterRepository.findAll());
        return "listMaster";  // Return the listMaster view
    }

    // Add Master Form
    @GetMapping("/addMaster")
    public String addMasterForm(Model model) {
        model.addAttribute("master", new Master());
        return "addMaster";
    }

    // Handle form submission for adding a Master
    @PostMapping("/addMaster")
    public String addMaster(@Valid @ModelAttribute("master") Master master, 
                            BindingResult result, 
                            RedirectAttributes redirectAttributes) {
        // If there are validation errors, return the form to correct them
        if (result.hasErrors()) {
            return "addMaster"; // Return form with errors
        }

        // Save the master entity directly (no error handling needed now)
        masterRepository.save(master);
        redirectAttributes.addFlashAttribute("successMessage", "Master added successfully!");

        return "redirect:/addMaster";  // Redirect back to the form with success message
    }

    // Edit Master (form view)
    @GetMapping("/editMaster/{id}")
    public String editMasterForm(@PathVariable Long id, Model model) {
        Master master = masterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid master ID: " + id));
        model.addAttribute("master", master);
        return "addMaster";  // Return the addMaster form for editing
    }

    // Save Edited Master
    @PostMapping("/editMaster/{id}")
    public String saveEditedMaster(@PathVariable Long id, @ModelAttribute Master master) {
        Master existingMaster = masterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid master ID: " + id));
        existingMaster.setName(master.getName());
        existingMaster.setDetails(master.getDetails());
        masterRepository.save(existingMaster);
        return "redirect:/listMaster";  // Redirect to the master list after editing
    }

    // Delete Master
    @GetMapping("/deleteMaster/{id}")
    public String deleteMaster(@PathVariable Long id) {
        masterRepository.deleteById(id);  // Delete the master by its ID
        return "redirect:/listMaster";  // Redirect back to the master list after deletion
    }
}

package com.DM.dairyManagement.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.DM.dairyManagement.model.Unit;
import com.DM.dairyManagement.repository.UnitRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/units")
public class UnitController {

    @Autowired
    private UnitRepository unitRepository;

    // Display all units
    @GetMapping
    public String listUnits(Model model) {
        List<Unit> units = unitRepository.findAll();
        model.addAttribute("units", units);
        return "unitList";
    }

    // Show form to add a new unit
    @GetMapping("/new")
    public String showUnitForm(Model model) {
        model.addAttribute("unit", new Unit());
        return "unitForm"; // This should be the name of your HTML form template";
    }

    // Save a new unit
    @PostMapping
    public String saveUnit(@ModelAttribute Unit unit) {
        unitRepository.save(unit);
        return "redirect:/units";
    }

    // Show form to edit a unit
    @GetMapping("/edit/{id}")
    public String editUnit(@PathVariable Long id, Model model) {
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if (unitOptional.isPresent()) {
            model.addAttribute("unit", unitOptional.get());
        } else {
            model.addAttribute("unit", new Unit()); // To prevent null errors
        }
        return "unitEdit"; 
    }

    // Update unit (Save changes)
    @PostMapping("/update/{id}")
    public String updateUnit(@PathVariable Long id, @ModelAttribute Unit updatedUnit) {
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if (unitOptional.isPresent()) {
            Unit unit = unitOptional.get();
            unit.setName(updatedUnit.getName());
            unit.setLtr(updatedUnit.getLtr());
            unit.setKg(updatedUnit.getKg());
            unitRepository.save(unit);
        }
        return "redirect:/units";
    }

    // Delete a unit
    @GetMapping("/delete/{id}")
    public String deleteUnit(@PathVariable Long id) {
        unitRepository.deleteById(id);
        return "redirect:/units";
    }
}
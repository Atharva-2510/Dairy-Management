package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.Sell;
import com.DM.dairyManagement.repository.ProductRepository;
import com.DM.dairyManagement.repository.SellRepository;
import com.DM.dairyManagement.service.SellService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class SellController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellRepository sellRepository ;
    
    @Autowired
    private SellService sellService;

    // Show Sell Form
    @GetMapping("/sell")
    public String showSellForm(Model model) {
        Sell sell = new Sell();
        sell.setDate(LocalDate.now());
        model.addAttribute("sell", sell);
        model.addAttribute("products", productRepository.findAll());
        return "sellForm";
    }

    // Process Sell
    @PostMapping("/sell")
    public String processSell(@ModelAttribute("sell") Sell sell, Model model) {
        if (sell.getQuantity() == null || sell.getQuantity() <= 0) {
            model.addAttribute("error", "Quantity must be greater than 0");
            model.addAttribute("products", productRepository.findAll());
            return "sellForm";
        }

        if (sell.getPrice() == null || sell.getPrice() <= 0) {
            model.addAttribute("error", "Price must be greater than 0");
            model.addAttribute("products", productRepository.findAll());
            return "sellForm";
        }

        double subtotal = sell.getQuantity() * sell.getPrice();
        sell.setSubtotal(subtotal);

        double cgstAmount = (subtotal * Optional.ofNullable(sell.getCgst()).orElse(0.0)) / 100;
        double sgstAmount = (subtotal * Optional.ofNullable(sell.getSgst()).orElse(0.0)) / 100;
        double discount = Optional.ofNullable(sell.getDiscount()).orElse(0.0);

        double total = subtotal + cgstAmount + sgstAmount - discount;
        sell.setTotal(total);

        double paid = Optional.ofNullable(sell.getPaidAmount()).orElse(0.0);
        if (paid > total) {
            model.addAttribute("error", "Paid amount cannot be more than total");
            model.addAttribute("products", productRepository.findAll());
            return "sellForm";
        }

        sell.setBalanceDue(Math.max(0, total - paid));
        sellService.save(sell);

        return "redirect:/sell-history";
    }
    @DeleteMapping("/sell/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteSell(@PathVariable Long id) {
        sellRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


    // View Sell History
    @GetMapping("/sell-history")
    public String viewSellHistory(Model model) {
        model.addAttribute("sales", sellService.getAllSales());
        return "sellHistory";
    }

    // Filter by Date
    @GetMapping("/sell-history/filter")
    public String filterSellHistory(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {
        model.addAttribute("sales", sellService.getSalesBetweenDates(startDate, endDate));
        return "sellHistory";
    }
}

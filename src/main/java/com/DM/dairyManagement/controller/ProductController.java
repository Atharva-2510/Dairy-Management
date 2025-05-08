package com.DM.dairyManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.DM.dairyManagement.model.Company;
import com.DM.dairyManagement.model.Product;
import com.DM.dairyManagement.model.Unit;
import com.DM.dairyManagement.repository.CompanyRepository;
import com.DM.dairyManagement.repository.ProductRepository;
import com.DM.dairyManagement.repository.UnitRepository;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "ProductList";
    }

    @GetMapping("/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        List<Unit> units = unitRepository.findAll();
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("units", units);
        model.addAttribute("companies", companies);
        return "ProductForm";
    }

    @PostMapping
    public String saveProduct(@ModelAttribute Product product) {
        if (product.getUnit1() == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        productRepository.save(product);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product id: " + id));
        List<Unit> units = unitRepository.findAll();
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("product", product);
        model.addAttribute("units", units);
        model.addAttribute("companies", companies);
        return "productEditForm";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute Product product) {
        if (product.getUnit1() == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        product.setId(id);
        productRepository.save(product);
        return "redirect:/products";
    }
}
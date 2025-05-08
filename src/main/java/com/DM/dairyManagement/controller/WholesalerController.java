package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.Company;
import com.DM.dairyManagement.model.Wholesaler;
import com.DM.dairyManagement.repository.CompanyRepository;
import com.DM.dairyManagement.repository.WholesalerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Controller
@RequestMapping("/wholesalers")
public class WholesalerController {

    @Autowired
    private WholesalerRepository wholesalerRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private final String UPLOAD_DIR = "document/";

    @GetMapping("/list")
    public String listWholesalers(Model model) {
        List<Wholesaler> wholesalers = wholesalerRepository.findAll();
        model.addAttribute("wholesalers", wholesalers);
        return "wholesalerList";
    }

    @GetMapping("/add")
    public String showAddWholesalerForm(Model model) {
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("wholesaler", new Wholesaler());
        model.addAttribute("companies", companies);
        return "addWholesaler";
    }

    @PostMapping("/save")
    public String saveWholesaler(@ModelAttribute Wholesaler wholesaler,
                               @RequestParam("adharCardPhoto") MultipartFile adharCardPhoto,
                               @RequestParam("passportPhoto") MultipartFile passportPhoto,
                               @RequestParam("licenceCopy") MultipartFile licenceCopy,
                               @RequestParam("company.id") Long companyId) throws IOException {

        if (!adharCardPhoto.isEmpty()) {
            wholesaler.setAdharCardPath(saveFile(adharCardPhoto));
        }
        if (!passportPhoto.isEmpty()) {
            wholesaler.setPassportPhotoPath(saveFile(passportPhoto));
        }
        if (!licenceCopy.isEmpty()) {
            wholesaler.setLicenceCopyPath(saveFile(licenceCopy));
        }

        Company selectedCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + companyId));
        wholesaler.setCompany(selectedCompany);

        wholesalerRepository.save(wholesaler);
        return "redirect:/wholesalers/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditWholesalerForm(@PathVariable("id") Long id, Model model) {
        Wholesaler wholesaler = wholesalerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid wholesaler ID: " + id));
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("wholesaler", wholesaler);
        model.addAttribute("companies", companies);
        return "editWholesaler";
    }

    @PostMapping("/update/{id}")
    public String updateWholesaler(@PathVariable("id") Long id,
                                 @ModelAttribute Wholesaler wholesaler,
                                 @RequestParam("adharCardPhoto") MultipartFile adharCardPhoto,
                                 @RequestParam("passportPhoto") MultipartFile passportPhoto,
                                 @RequestParam("licenceCopy") MultipartFile licenceCopy,
                                 @RequestParam("company.id") Long companyId) throws IOException {

        Wholesaler existing = wholesalerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid wholesaler ID: " + id));

        existing.setName(wholesaler.getName());
        existing.setEmail(wholesaler.getEmail());
        existing.setMobileNo(wholesaler.getMobileNo());
        existing.setVehicleNo(wholesaler.getVehicleNo());

        Company selectedCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + companyId));
        existing.setCompany(selectedCompany);

        if (!adharCardPhoto.isEmpty()) {
            existing.setAdharCardPath(saveFile(adharCardPhoto));
        }
        if (!passportPhoto.isEmpty()) {
            existing.setPassportPhotoPath(saveFile(passportPhoto));
        }
        if (!licenceCopy.isEmpty()) {
            existing.setLicenceCopyPath(saveFile(licenceCopy));
        }

        wholesalerRepository.save(existing);
        return "redirect:/wholesalers/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteWholesaler(@PathVariable("id") Long id) {
        wholesalerRepository.deleteById(id);
        return "redirect:/wholesalers/list";
    }

    @GetMapping("/uploads/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws IOException {
        Path filePath = Path.of(UPLOAD_DIR, filename).toAbsolutePath();

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/bills/{id}")
    public String showWholesalerBills(@PathVariable("id") Long id, Model model) {
        Wholesaler wholesaler = wholesalerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid wholesaler ID: " + id));
        model.addAttribute("wholesaler", wholesaler);
        return "wholesaler-bills";
    }

    @GetMapping("/retailers/{id}")
    public String showWholesalerRetailers(@PathVariable("id") Long id, Model model) {
        Wholesaler wholesaler = wholesalerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid wholesaler ID: " + id));
        model.addAttribute("wholesaler", wholesaler);
        model.addAttribute("retailers", wholesaler.getRetailers());
        return "wholesaler-retailers";
    }

    private String saveFile(MultipartFile file) throws IOException {
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Path.of(directory.getAbsolutePath(), fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
    

    
    
}
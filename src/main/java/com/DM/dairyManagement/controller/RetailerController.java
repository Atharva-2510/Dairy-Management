// RetailerController.java (Fixed version)
package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.Company;
import com.DM.dairyManagement.model.Retailer;
import com.DM.dairyManagement.repository.CompanyRepository;
import com.DM.dairyManagement.repository.RetailerRepository;
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
@RequestMapping("/retailers")
public class RetailerController {

    @Autowired
    private RetailerRepository retailerRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private final String UPLOAD_DIR = "document/";

    @GetMapping("/list")
    public String listRetailers(Model model) {
        List<Retailer> retailers = retailerRepository.findAll();
        model.addAttribute("retailers", retailers);
        return "retailerList";
    }

    @GetMapping("/add")
    public String showAddRetailerForm(Model model) {
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("retailer", new Retailer());
        model.addAttribute("companies", companies);
        return "addRetailer";
    }

    @PostMapping("/save")
    public String saveRetailer(@ModelAttribute Retailer retailer,
                               @RequestParam("adharCardPhoto") MultipartFile adharCardPhoto,
                               @RequestParam("passportPhoto") MultipartFile passportPhoto,
                               @RequestParam("licenceCopy") MultipartFile licenceCopy,
                               @RequestParam("company.id") Long companyId) throws IOException {

        if (!adharCardPhoto.isEmpty()) {
            retailer.setAdharCardPath(saveFile(adharCardPhoto));
        }
        if (!passportPhoto.isEmpty()) {
            retailer.setPassportPhotoPath(saveFile(passportPhoto));
        }
        if (!licenceCopy.isEmpty()) {
            retailer.setLicenceCopyPath(saveFile(licenceCopy));
        }

        Company selectedCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID: " + companyId));
        retailer.setCompany(selectedCompany);

        retailerRepository.save(retailer);
        return "redirect:/retailers/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditRetailerForm(@PathVariable("id") Long id, Model model) {
        Retailer retailer = retailerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid retailer ID: " + id));
        List<Company> companies = companyRepository.findAll();
        model.addAttribute("retailer", retailer);
        model.addAttribute("companies", companies);
        return "editRetailer";
    }

    @PostMapping("/update/{id}")
    public String updateRetailer(@PathVariable("id") Long id,
                                 @ModelAttribute Retailer retailer,
                                 @RequestParam("adharCardPhoto") MultipartFile adharCardPhoto,
                                 @RequestParam("passportPhoto") MultipartFile passportPhoto,
                                 @RequestParam("licenceCopy") MultipartFile licenceCopy,
                                 @RequestParam("company.id") Long companyId) throws IOException {

        Retailer existing = retailerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid retailer ID: " + id));

        existing.setName(retailer.getName());
        existing.setEmail(retailer.getEmail());
        existing.setMobileNo(retailer.getMobileNo());
        existing.setVehicleNo(retailer.getVehicleNo());

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

        retailerRepository.save(existing);
        return "redirect:/retailers/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRetailer(@PathVariable("id") Long id) {
        retailerRepository.deleteById(id);
        return "redirect:/retailers/list";
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
    public String showRetailerBills(@PathVariable("id") Long id, Model model) {
        Retailer retailer = retailerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid retailer ID: " + id));
        model.addAttribute("retailer", retailer);
        return "retailer-bills";
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

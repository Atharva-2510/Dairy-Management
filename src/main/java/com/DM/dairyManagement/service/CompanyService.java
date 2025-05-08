package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Company;
import com.DM.dairyManagement.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public long getTotalCompanies() {
        return companyRepository.count();
    }

    public List<Company> getLatestCompanies() {
        return companyRepository.findTop5ByOrderByIdDesc();
    }
}

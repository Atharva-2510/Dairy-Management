package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Retailer;
import com.DM.dairyManagement.repository.RetailerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RetailerService {

    @Autowired
    private RetailerRepository retailerRepository;

    public long getTotalRetailers() {
        return retailerRepository.count();
    }

    public List<Retailer> getLatestRetailers() {
        return retailerRepository.findTop5ByOrderByIdDesc();
    }
}

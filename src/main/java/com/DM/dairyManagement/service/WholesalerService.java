package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Wholesaler;
import com.DM.dairyManagement.repository.WholesalerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WholesalerService {
    @Autowired
    private WholesalerRepository wholesalerRepository;
    
    public long getTotalWholesalers() {
        return wholesalerRepository.count();
    }
    
    public List<Wholesaler> getLatestWholesalers() {
        return wholesalerRepository.findTop5ByOrderByIdDesc();
    }
}
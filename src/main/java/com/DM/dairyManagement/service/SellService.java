package com.DM.dairyManagement.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DM.dairyManagement.model.Sell;
import com.DM.dairyManagement.repository.SellRepository;

@Service
public class SellService {

    @Autowired
    private SellRepository sellRepository;

    public void save(Sell sell) {
        sellRepository.save(sell);
    }

    public List<Sell> getAllSales() {
        return sellRepository.findAll();
    }

    public List<Sell> getSalesBetweenDates(LocalDate start, LocalDate end) {
        return sellRepository.findByDateBetween(start, end);
    }
}


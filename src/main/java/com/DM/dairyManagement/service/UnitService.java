package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Unit;
import com.DM.dairyManagement.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    public long getTotalUnits() {
        return unitRepository.count();
    }

    public List<Unit> getLatestUnits() {
        return unitRepository.findTop5ByOrderByIdDesc();
    }
}

package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findTop5ByOrderByIdDesc();
}

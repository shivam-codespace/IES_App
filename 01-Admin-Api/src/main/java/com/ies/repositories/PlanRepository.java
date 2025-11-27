package com.ies.repositories;

import com.ies.entities.PlansEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PlanRepository extends JpaRepository<PlansEntity, Integer> {

    @Modifying
    @Transactional
    @Query("update PlansEntity set activeSw = :status where planId = :planId")
    public Integer changePlanStatus(Integer planId, String status);
}

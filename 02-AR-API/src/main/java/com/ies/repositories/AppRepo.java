package com.ies.repositories;

import com.ies.entities.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AppRepo extends JpaRepository<AppEntity, Long> {

    // 🔹 Fetch all applications (for admin view)
    @Query("from AppEntity")
    List<AppEntity> fetchUserApps();

    // 🔹 Fetch applications by Case Worker (specific userId)
    @Query("from AppEntity where user.userId = :userId")
    List<AppEntity> fetchCwApps(@Param("userId") Integer userId);

    // 🔹 Fetch applications by plan name
    @Query("from AppEntity where planName = :planName")
    List<AppEntity> findByPlanName(@Param("planName") String planName);

    // 🔹 Fetch applications by status
    @Query("from AppEntity where status = :status")
    List<AppEntity> findByStatus(@Param("status") String status);

    // 🔹 Fetch applications by both status and plan name
    @Query("from AppEntity where status = :status and planName = :planName")
    List<AppEntity> findByStatusAndPlanName(@Param("status") String status, @Param("planName") String planName);
}

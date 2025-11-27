package com.ies.repositories;


import com.ies.entities.UserEntity;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	@Modifying
	@Transactional
	@Query("update UserEntity set activeSw=:status where userId=:userId")
    public Integer updateAccStatus(@Param("userId") Integer userId,@Param("status") String status);

    public UserEntity findByEmail(String email);
    
    public UserEntity findByEmailAndPwd(String email, String pwd);
    
    @Query("SELECT u FROM UserEntity u WHERE u.roleId <> 101")
    List<UserEntity> findAllExcept101();;
    
    @Modifying
	@Transactional
    public Integer findRoleIdByEmail(String email);
}

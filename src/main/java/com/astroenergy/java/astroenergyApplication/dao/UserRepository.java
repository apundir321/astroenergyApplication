package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.astroenergy.java.astroenergyApplication.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndDeletedAtIsNull(String email);
    List<User> findByDeletedAtIsNullOrderByIdDesc();
    @Override
    void delete(User user);
    
User findByIdAndDeletedAtIsNull(Long id);
User findByUserProfileIdAndDeletedAtIsNull(int id);
}

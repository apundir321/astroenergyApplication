package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astroenergy.java.astroenergyApplication.model.Common;

public interface CommonRepository extends JpaRepository<Common,Long>{
Common findByIdAndDeletedAtIsNull(Long id);
List<Common> findByDeletedAtIsNullOrderByIdDesc();
}

package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astroenergy.java.astroenergyApplication.model.Blog;

public interface BlogRepo extends JpaRepository<Blog,Long> {
Blog findByStatusAndDeletedAtIsNull(String status);
Blog findByIdAndDeletedAtIsNull(Long id);
List<Blog>findByDeletedAtIsNullOrderByIdDesc();
Blog findBySlugAndDeletedAtIsNull(String slug);
}

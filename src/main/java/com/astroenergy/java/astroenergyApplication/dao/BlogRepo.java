package com.astroenergy.java.astroenergyApplication.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astroenergy.java.astroenergyApplication.model.Blog;

public interface BlogRepo extends JpaRepository<Blog,Long> {
Blog findByStatus(String status);
}

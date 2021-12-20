package com.astroenergy.java.astroenergyApplication.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.astroenergy.java.astroenergyApplication.model.Comment;

public interface CommentRepo extends JpaRepository<Comment,Long> {
List<Comment> findByBlogIdAndDeletedAtIsNullOrderByIdDesc(Long id);
Comment findByIdAndDeletedAtIsNull(Long id);
List<Comment> findByDeletedAtIsNullOrderByIdDesc();

}

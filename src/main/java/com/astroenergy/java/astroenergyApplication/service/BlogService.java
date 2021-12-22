package com.astroenergy.java.astroenergyApplication.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.BlogRepo;
import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Blog;

@Service
public class BlogService {
@Autowired
BlogRepo blogRepo;

public Blog deleteBlog(Long id) throws Exception  {
	try {
		Blog b=blogRepo.findById(id).get();
		b.setDeletedAt(new Date());
		return blogRepo.save(b);
	}
	catch(Exception e) {
		throw e;
	}
}
public Blog getBlogBySlug(String slug) {
	try {
		Blog b=blogRepo.findBySlugAndDeletedAtIsNull(slug);
		return b;
	}
	catch(Exception e) {
		throw e;
	}
}
public Blog addBlog(Blog blog) throws Exception {
	try {
		Blog b=blogRepo.save(blog);
	return b;
	}
	catch(Exception e) {
		throw e;
	}
}
public List<Blog> getAllBlogs() throws Exception {
	try {
	return blogRepo.findByDeletedAtIsNullOrderByIdDesc();
	
	}
	catch(Exception e) {
		throw e;
	}
}
public Blog getBlog(Long id) {
	try {
		Blog b=blogRepo.findByIdAndDeletedAtIsNull(id);
		return b;
	}
	catch(Exception e) {
		throw e;
	}
}
public Blog getBlogByStatus(String status) {
	try {
		Blog b=blogRepo.findByStatusAndDeletedAtIsNull(status);
		return b;
	}
	catch(Exception e) {
		throw e;
	}
}
public Blog updateBlogStatus(Long id,String status) {
	try {
		Blog b=blogRepo.findByIdAndDeletedAtIsNull(id);
		b.setStatus(status);
		return blogRepo.save(b);
		
	}
	catch(Exception e) {
		throw e;
	}
}
}


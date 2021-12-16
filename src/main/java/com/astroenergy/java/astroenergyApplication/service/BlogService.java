package com.astroenergy.java.astroenergyApplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.BlogRepo;
import com.astroenergy.java.astroenergyApplication.model.Blog;

@Service
public class BlogService {
@Autowired
BlogRepo blogRepo;

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
	return blogRepo.findAll();
	
	}
	catch(Exception e) {
		throw e;
	}
}
public Blog getBlog(Long id) {
	try {
		Blog b=blogRepo.findById(id).get();
		return b;
	}
	catch(Exception e) {
		throw e;
	}
}
public Blog getBlogByStatus(String status) {
	try {
		Blog b=blogRepo.findByStatus(status);
		return b;
	}
	catch(Exception e) {
		throw e;
	}
}
}


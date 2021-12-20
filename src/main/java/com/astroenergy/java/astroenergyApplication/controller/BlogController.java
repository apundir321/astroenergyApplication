package com.astroenergy.java.astroenergyApplication.controller;

import java.util.List;

import javax.persistence.Lob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Blog;
import com.astroenergy.java.astroenergyApplication.model.Country;
import com.astroenergy.java.astroenergyApplication.service.BlogService;

@RestController
public class BlogController {
@Autowired
BlogService blogService;

@DeleteMapping("/deleteBlog/{id}")
public ResponseEntity<?> deleteBlog(@PathVariable Long id) {
	try {
	Blog b= blogService.deleteBlog(id);
	 return new ResponseEntity<>(b, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/addBlog")
public ResponseEntity<?> addBlog(@RequestBody Blog blog) {
	try {
	Blog b = blogService.addBlog(blog);
	 return new ResponseEntity<Blog>(b, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/getAllBlogs")
public ResponseEntity<?> getAllBlogs() {
	try {
	List<Blog> b = blogService.getAllBlogs();
	 return new ResponseEntity<>(b, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/getBlog")
public ResponseEntity<?> getBlog(@RequestParam Long id) {
	try {
	Blog b = blogService.getBlog(id);
	 return new ResponseEntity<Blog>(b, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/getBlogBySlug")
public ResponseEntity<?> getBlogBySlug(@RequestParam String slug) {
	try {
	Blog b = blogService.getBlogBySlug(slug);
	 return new ResponseEntity<Blog>(b, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/getBlogByStatus")
public ResponseEntity<?> getBlogByStatus(@RequestParam String status) {
	try {
	Blog b = blogService.getBlogByStatus(status);
	 return new ResponseEntity<Blog>(b, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
}

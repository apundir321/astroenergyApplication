package com.astroenergy.java.astroenergyApplication.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import javax.persistence.Lob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.astroenergy.java.astroenergyApplication.model.Appointment;
import com.astroenergy.java.astroenergyApplication.model.Blog;
import com.astroenergy.java.astroenergyApplication.model.Country;
import com.astroenergy.java.astroenergyApplication.model.UserProfile;
import com.astroenergy.java.astroenergyApplication.service.AWSS3Service;
import com.astroenergy.java.astroenergyApplication.service.BlogService;
import com.astroenergy.java.astroenergyApplication.util.GenericResponse;

@RestController
public class BlogController {
@Autowired
BlogService blogService;
@Autowired
private AWSS3Service awsService;
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
@RequestMapping("/updateBlogStatus")
public ResponseEntity<?> updateBlogStatus(@RequestParam Long id,@RequestParam String status) {
	try {
	Blog b = blogService.updateBlogStatus(id,status);
	 return new ResponseEntity<Blog>(b, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping(value = "/uploadBlogPic", method = RequestMethod.POST)
public ResponseEntity<?> uploadBlogPic(@RequestPart(value= "file" ,required = true) final MultipartFile multipartFile,@RequestParam Long blogId)
		throws Exception {
	Blog blog=null;
	File file = null;
	try {
		blog=blogService.getBlog(blogId);
		awsService.uploadFile(multipartFile, String.valueOf(blog.getId()));
		blog.setImage(multipartFile.getOriginalFilename());
		blogService.addBlog(blog);
	} catch (Exception e) {
		return new ResponseEntity<GenericResponse>(new GenericResponse("Exception in uploading blog pic"+e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<Blog>(blog,HttpStatus.OK);
	
}
@GetMapping("/getBlogPic/{name}/{blogId}")
public ResponseEntity<byte[]> downloadFileByBlogId(@PathVariable String name,@PathVariable Long blogId ) throws Exception {
	Blog blog=null;
	ByteArrayOutputStream downloadInputStream = null;
	blog=blogService.getBlog(blogId);
	try {
		downloadInputStream = awsService.downloadFile(name, blog.getId());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(downloadInputStream.toByteArray());
}
}

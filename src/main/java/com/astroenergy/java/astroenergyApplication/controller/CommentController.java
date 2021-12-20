package com.astroenergy.java.astroenergyApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.astroenergy.java.astroenergyApplication.model.Blog;
import com.astroenergy.java.astroenergyApplication.model.Comment;
import com.astroenergy.java.astroenergyApplication.model.Enquiry;
import com.astroenergy.java.astroenergyApplication.service.CommentService;

@RestController
public class CommentController {
@Autowired
CommentService commentService;
@DeleteMapping("/deleteComment/{id}")
public ResponseEntity<?> deleteComment(@PathVariable Long id) {
	try {
	Comment c= commentService.deleteComment(id);
	 return new ResponseEntity<>(c, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/addComment")
public ResponseEntity<?> addComment(@RequestBody Comment comment) {
	try {
	Comment c = commentService.addComment(comment);
	 return new ResponseEntity<Comment>(c, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/commentViewed")
public ResponseEntity<?> commentViewed(@RequestParam Long id,@RequestParam String viewed) {
	try {
Comment c = commentService.commentViewed(id,viewed);
		return new ResponseEntity<>(c, HttpStatus.OK);
	} catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}


@RequestMapping("/getCommentById")
public ResponseEntity<?> getCommentById(@RequestParam Long id) {
	try {
	Comment c = commentService.getCommentById(id);
	 return new ResponseEntity<Comment>(c, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
@RequestMapping("/getBlogComments")
public ResponseEntity<?> getBlogComments(@RequestParam Long id) {
	try {
	List<Comment> c = commentService.getBlogComments(id);
	 return new ResponseEntity<>(c, HttpStatus.OK);
	}catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
}

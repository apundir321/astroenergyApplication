package com.astroenergy.java.astroenergyApplication.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.CommentRepo;
import com.astroenergy.java.astroenergyApplication.model.Blog;
import com.astroenergy.java.astroenergyApplication.model.Comment;

@Service
public class CommentService {
@Autowired
CommentRepo commentRepo;
public Comment deleteComment(Long id) throws Exception  {
	try {
		Comment c=commentRepo.findByIdAndDeletedAtIsNull(id);
		c.setDeletedAt(new Date());
		return commentRepo.save(c);
	}
	catch(Exception e) {
		throw e;
	}
}
public Comment addComment(Comment comment) throws Exception  {
	try {
		return commentRepo.save(comment);
		
	}
	catch(Exception e) {
		throw e;
	}
}
public Comment getCommentById(Long id) throws Exception{
	try {
		return commentRepo.findByIdAndDeletedAtIsNull(id);
	}
	catch(Exception e) {
		throw e;
	}
}
public List<Comment> getBlogComments(Long id) throws Exception{
	try {
		return commentRepo.findByBlogIdAndDeletedAtIsNullOrderByIdDesc(id);
	}
	catch(Exception e) {
		throw e;
	}
}
public Comment commentViewed(Long id,String viewed) {
	try {
		Comment c=commentRepo.findByIdAndDeletedAtIsNull(id);
		c.setViewed(viewed);
		return commentRepo.save(c);
		
	}
	catch(Exception e) {
		throw e;
	}
}
}

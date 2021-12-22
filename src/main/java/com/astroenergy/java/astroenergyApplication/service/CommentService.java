package com.astroenergy.java.astroenergyApplication.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.astroenergy.java.astroenergyApplication.dao.CommentRepo;
import com.astroenergy.java.astroenergyApplication.model.Blog;
import com.astroenergy.java.astroenergyApplication.model.Comment;
import com.astroenergy.java.astroenergyApplication.model.User;
import com.astroenergy.java.astroenergyApplication.security.UserService;

@Service
public class CommentService {
@Autowired
CommentRepo commentRepo;
@Autowired
UserService userService;
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
		   final User user = userService.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		   if(user!=null) {
			   String name=user.getFirstName()+" "+user.getLastName();
			   comment.setCommentedBy(name);
			   comment.setEmail(user.getEmail());
			   comment.setUser(user);
		   }
		return commentRepo.save(comment);
		
	}
	catch(Exception e) {
		throw e;
	}
}
public Comment updateCommentStatus(Long id,String status) throws Exception {
	try {
		Comment c=commentRepo.findByIdAndDeletedAtIsNull(id);
		c.setStatus(status);
		return commentRepo.save(c);
		
		
	}catch(Exception e) {
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
public List<Comment> getCommentReplies(Long id){
	try {
		return commentRepo.findByReplyOfIdAndDeletedAtIsNullOrderByIdDesc(id);
	}
	catch(Exception e) {
		throw e;
	}
}
}

package com.example.model;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comment {
	
	@Id
	private String id;
	
	private String username;
	private String commentText;
	private HashSet<String> usersThatLikeTheComment;
	private LocalDateTime creationTime;
	
	public Comment() {
		
		this.username = "";
		this.commentText = "";
		this.usersThatLikeTheComment = new HashSet<>();
		this.creationTime = LocalDateTime.MIN;
		
	}	
	
	public Comment(String username, String commentText) {
		
		this.username = username;
		this.commentText = commentText;
		this.usersThatLikeTheComment = new HashSet<>();
		usersThatLikeTheComment.add(username);
		this.creationTime = LocalDateTime.now();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public HashSet<String> getUsersThatLikeTheComment() {
		return usersThatLikeTheComment;
	}

	public void setUsersThatLikeTheComment(HashSet<String> usersThatLikeTheComment) {
		this.usersThatLikeTheComment = usersThatLikeTheComment;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
	
}

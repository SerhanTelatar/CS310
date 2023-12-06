package com.example.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Posts {
	
	@Id
	private String id;
	private String userThatCreatedThePost;
	private String postText;
	private HashSet<String> usersThatLikedThePost;
	private LocalDateTime creationTime;
	
	@DBRef
	private ArrayList<Comment> comments;
	
	
	public Posts() {
		this.userThatCreatedThePost = "";
		this.postText = "";
		this.usersThatLikedThePost = new HashSet<>();
		this.comments = new ArrayList<>();
	    this.creationTime = LocalDateTime.MIN;
	}



	public Posts(String userThatCreatedThePost, String text) {

		this.userThatCreatedThePost = userThatCreatedThePost;
		this.postText = text;
		this.comments = new ArrayList<>();
		this.usersThatLikedThePost = new HashSet<>();
	    this.usersThatLikedThePost.add(userThatCreatedThePost);
	    this.creationTime = LocalDateTime.now();
	}



	public String getUserThatCreatedThePost() {
		return userThatCreatedThePost;
	}



	public void setUserThatCreatedThePost(String userThatCreatedThePost) {
		this.userThatCreatedThePost = userThatCreatedThePost;
	}



	public String getPostText() {
		return postText;
	}



	public void setPostText(String postText) {
		this.postText = postText;
	}



	public HashSet<String> getUsersThatLikedThePost() {
		return usersThatLikedThePost;
	}



	public void setUsersThatLikedThePost(HashSet<String> usersThatLikedThePost) {
		this.usersThatLikedThePost = usersThatLikedThePost;
	}



	public LocalDateTime getCreationTime() {
		return creationTime;
	}



	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}



	public ArrayList<Comment> getComments() {
		return comments;
	}



	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}



	public String getId() {
		return id;
	}



	


	
}

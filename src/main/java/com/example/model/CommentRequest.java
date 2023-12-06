package com.example.model;

public class CommentRequest {
	
	private String id;
	private String text;
	private String user;
	
	public CommentRequest() {
		super();
		this.id = "";
		this.text = "";
		this.user = "";
	}
	
	public CommentRequest(String id, String text, String user) {
		
		this.id = id;
		this.text = text;
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	

}

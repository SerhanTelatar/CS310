package com.example.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.CommentsDAO;
import com.example.dao.PostsDAO;
import com.example.dao.UsersDAO;
import com.example.model.Comment;
import com.example.model.CommentRequest;
import com.example.model.Posts;
import com.example.model.PostsRequest;
import com.example.model.Users;

import org.slf4j.Logger;


import jakarta.annotation.PostConstruct;



@RestController
@EnableMongoRepositories(basePackages = {"com.example.dao"})
public class ProjectController{

	@Autowired private UsersDAO userdao;
	@Autowired private PostsDAO postdao;
	@Autowired private CommentsDAO commentdao;
	
	//private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@PostConstruct
	public void init() {
		
		if (userdao.count() == 0) {
			
			Users user1 = new Users("can", "123");
			Users user2 = new Users("ali", "456");

			userdao.save(user1);
			userdao.save(user2);
		}
		
		if (postdao.count() == 0) {
			
			Iterable<Users> allUsers = userdao.findAll();
	        for (Users user : allUsers) {
	        	
	        	Posts post1 = new Posts(user.getUsername(), "sample post");
	        	postdao.save(post1);
	        }
		}
		
		if(commentdao.count()==0) {
			
			Iterable<Posts> allPosts = postdao.findAll();
	        for (Posts post : allPosts) {
	        	
	        	Comment comment1 = new Comment(post.getUserThatCreatedThePost(), "sample comment");
	        	commentdao.save(comment1);
	        	post.getComments().add(comment1);
	            postdao.save(post);
	        }
			
		}
	}
	
	
	@PostMapping("/signup")
    public String signUp(@RequestBody Users user) {
		
        if (userdao.existsByUsername(user.getUsername())) {
            return "Username already exists. Please choose a different username.";
        } else {
        	
            userdao.save(user);
            return "User signed up successfully!";
        }
    }
	
	@PostMapping("/login")
    public String login(@RequestBody Users user) {
        Users existingUser = userdao.findByUsername(user.getUsername());

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return "Login successful!";
        } else {
            return "Invalid username or password.";
        }
    }
	
	@GetMapping("/posts")
	public List<Posts> posts(){
		return postdao.findAll();
	}
	
	@PostMapping("/posts/createPost")
    public String createPost(@RequestBody PostsRequest postRequest) {
        
        String username = postRequest.getUsername();
        String text = postRequest.getText();

        // Create a new post
        Posts newPost = new Posts(username, text);
        postdao.save(newPost);

        return "Post created successfully!";
    }
	
	@PostMapping("/posts/likePost")
	public String likePost(
	        @RequestBody PostsRequest postRequest) {
		
		String username = postRequest.getUsername();
        String postId = postRequest.getText();
        
       // logger.info("username" + username);
       // logger.info("post id" + postId);
		
	    Optional<Posts> optionalPost = postdao.findById(postId);
	    
	    if (optionalPost.isPresent()) {
	        Posts post = optionalPost.get();
	        HashSet<String> usersThatLikedThePost = post.getUsersThatLikedThePost();

	        if (usersThatLikedThePost.contains(username)) {
	            usersThatLikedThePost.remove(username);
	            postdao.save(post);
	            return "Post unliked by user: " + username;
	        } else {
	            usersThatLikedThePost.add(username);
	            postdao.save(post);
	            return "Post liked by user: " + username;
	        }
	    } else {
	        return "Post not found!";
	    }
	}
	
	@PostMapping("/posts/comment")
    public String addComment(@RequestBody CommentRequest commentRequest) {
        String postId = commentRequest.getId();
        String text = commentRequest.getText();
        String user = commentRequest.getUser();

        // Find the post by ID
        Optional<Posts> optionalPost = postdao.findById(postId);

        if (optionalPost.isPresent()) {
            Posts post = optionalPost.get();

            // Create a new comment
            
            Comment newComment = new Comment(user, text);
            commentdao.save(newComment);
            
            // Add the comment to the post's comment list
            post.getComments().add(newComment);
            // Save the updated post
            postdao.save(post);

            return "Comment added successfully!";
        } else {
            return "Post not found!";
        }
    }
	
	@PostMapping("/comment/likeComment")
	public String likeComment(
	        @RequestBody PostsRequest postRequest) {
		String username = postRequest.getUsername();
        String commentId = postRequest.getText();

	    Optional<Comment> optionalComment = commentdao.findById(commentId);

	    if (optionalComment.isPresent()) {
	        Comment comment = optionalComment.get();
	        HashSet<String> usersThatLikeTheComment = comment.getUsersThatLikeTheComment();

	        if (usersThatLikeTheComment.contains(username)) {
	            usersThatLikeTheComment.remove(username);
	            commentdao.save(comment);
	            return "Comment unliked by user: " + username;
	        } else {
	            usersThatLikeTheComment.add(username);
	            commentdao.save(comment);
	            return "Comment liked by user: " + username;
	        }
	    } else {
	        return "Comment not found!";
	    }
	}
	
	
}
	


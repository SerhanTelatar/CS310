package com.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Posts;

public interface PostsDAO extends MongoRepository<Posts, String>{
	

}

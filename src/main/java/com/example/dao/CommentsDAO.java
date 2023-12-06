package com.example.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.model.Comment;

public interface CommentsDAO extends MongoRepository<Comment, String>{

}

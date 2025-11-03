package com.example.ticketing.service;

import com.example.ticketing.model.Comment;
import com.example.ticketing.repo.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepo;
    public CommentService(CommentRepository commentRepo){this.commentRepo = commentRepo;}
    public Comment save(Comment c){return commentRepo.save(c);}
}

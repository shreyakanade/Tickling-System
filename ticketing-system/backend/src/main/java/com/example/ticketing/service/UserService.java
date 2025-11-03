package com.example.ticketing.service;

import com.example.ticketing.model.User;
import com.example.ticketing.repo.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepo;
    public UserService(UserRepository userRepo){this.userRepo = userRepo;}
    public Optional<User> findByUsername(String u){return userRepo.findByUsername(u);}
    public Optional<User> findByToken(String t){return userRepo.findByToken(t);}
    public User save(User u){return userRepo.save(u);}
    public List<User> findAll(){return userRepo.findAll();}
    public Optional<User> findById(Long id){return userRepo.findById(id);}
}

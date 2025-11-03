package com.example.ticketing.controller;

import com.example.ticketing.model.User;
import com.example.ticketing.model.Role;
import com.example.ticketing.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService){this.userService = userService;}

    @PostMapping("/register")
    public Map<String,Object> register(@RequestBody Map<String,String> body){
        String username = body.get("username");
        String password = body.get("password");
        if(username==null || password==null) return Map.of("error","username and password required");
        if(userService.findByUsername(username).isPresent()) return Map.of("error","username exists");
        User u = new User();
        u.setUsername(username);
        u.setPassword(password); // NOTE: plaintext for demo only
        u.setRole(Role.USER);
        userService.save(u);
        return Map.of("ok", true, "userId", u.getId());
    }

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody Map<String,String> body){
        String username = body.get("username");
        String password = body.get("password");
        Optional<User> ou = userService.findByUsername(username);
        if(ou.isEmpty() || !ou.get().getPassword().equals(password)) return Map.of("error","invalid credentials");
        User u = ou.get();
        String token = UUID.randomUUID().toString();
        u.setToken(token);
        userService.save(u);
        Map<String,Object> resp = new HashMap<>();
        resp.put("token", token);
        resp.put("userId", u.getId());
        resp.put("role", u.getRole());
        resp.put("username", u.getUsername());
        return resp;
    }

    @PostMapping("/logout")
    public Map<String,Object> logout(@RequestHeader("Authorization") String auth){
        if(auth==null || !auth.startsWith("Bearer ")) return Map.of("error","missing token");
        String t = auth.substring(7);
        Optional<User> ou = userService.findByToken(t);
        if(ou.isEmpty()) return Map.of("error","invalid token");
        User u = ou.get();
        u.setToken(null);
        userService.save(u);
        return Map.of("ok", true);
    }
}

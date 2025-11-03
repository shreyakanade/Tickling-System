package com.example.ticketing.controller;

import com.example.ticketing.model.*;
import com.example.ticketing.service.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final TicketService ticketService;

    public AdminController(UserService userService, TicketService ticketService){
        this.userService = userService;
        this.ticketService = ticketService;
    }

    private Optional<User> getUserFromToken(String auth){
        if(auth==null || !auth.startsWith("Bearer ")) return Optional.empty();
        String t = auth.substring(7);
        return userService.findByToken(t);
    }

    @GetMapping("/users")
    public Object allUsers(@RequestHeader("Authorization") String auth){
        Optional<User> ou = getUserFromToken(auth);
        if(ou.isEmpty() || ou.get().getRole()!=Role.ADMIN) return Map.of("error","forbidden");
        return userService.findAll();
    }

    @PostMapping("/users/create")
    public Object createUser(@RequestHeader("Authorization") String auth, @RequestBody Map<String,String> body){
        Optional<User> ou = getUserFromToken(auth);
        if(ou.isEmpty() || ou.get().getRole()!=Role.ADMIN) return Map.of("error","forbidden");
        User u = new User();
        u.setUsername(body.get("username"));
        u.setPassword(body.get("password"));
        try{
            u.setRole(Role.valueOf(body.getOrDefault("role","USER")));
        }catch(Exception e){u.setRole(Role.USER);}
        userService.save(u);
        return u;
    }

    @PostMapping("/tickets/force/{id}/status")
    public Object forceStatus(@RequestHeader("Authorization") String auth, @PathVariable Long id, @RequestBody Map<String,String> body){
        Optional<User> ou = getUserFromToken(auth);
        if(ou.isEmpty() || ou.get().getRole()!=Role.ADMIN) return Map.of("error","forbidden");
        Optional<Ticket> ot = ticketService.findById(id);
        if(ot.isEmpty()) return Map.of("error","not found");
        Ticket t = ot.get();
        try{
            t.setStatus(Status.valueOf(body.getOrDefault("status","OPEN")));
            ticketService.save(t);
            return t;
        }catch(Exception e){ return Map.of("error","invalid status");}
    }

    @GetMapping("/tickets")
    public Object allTickets(@RequestHeader("Authorization") String auth){
        Optional<User> ou = getUserFromToken(auth);
        if(ou.isEmpty() || ou.get().getRole()!=Role.ADMIN) return Map.of("error","forbidden");
        return ticketService.findAll();
    }
}

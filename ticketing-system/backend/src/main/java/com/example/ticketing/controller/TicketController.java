package com.example.ticketing.controller;

import com.example.ticketing.model.*;
import com.example.ticketing.service.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final UserService userService;
    private final TicketService ticketService;
    private final CommentService commentService;

    public TicketController(UserService userService, TicketService ticketService, CommentService commentService){
        this.userService = userService;
        this.ticketService = ticketService;
        this.commentService = commentService;
    }

    private Optional<User> getUserFromToken(String auth){
        if(auth==null || !auth.startsWith("Bearer ")) return Optional.empty();
        String t = auth.substring(7);
        return userService.findByToken(t);
    }

    @PostMapping("/create")
    public Object createTicket(@RequestHeader("Authorization") String auth, @RequestBody Map<String,String> body){
        Optional<User> ou = getUserFromToken(auth);
        if(ou.isEmpty()) return Map.of("error","unauthorized");
        User u = ou.get();
        Ticket ticket = new Ticket();
        ticket.setSubject(body.getOrDefault("subject",""));
        ticket.setDescription(body.getOrDefault("description",""));
        String p = body.getOrDefault("priority","MEDIUM");
        ticket.setPriority(Priority.valueOf(p));
        ticket.setOwner(u);
        ticketService.save(ticket);
        return ticket;
    }

    @GetMapping("/my")
    public Object myTickets(@RequestHeader("Authorization") String auth){
        Optional<User> ou = getUserFromToken(auth);
        if(ou.isEmpty()) return Map.of("error","unauthorized");
        return ticketService.findByOwnerId(ou.get().getId());
    }

    @GetMapping("/{id}")
    public Object getTicket(@RequestHeader("Authorization") String auth, @PathVariable Long id){
        Optional<User> ou = getUserFromToken(auth);
        if(ou.isEmpty()) return Map.of("error","unauthorized");
        Optional<Ticket> t = ticketService.findById(id);
        if(t.isEmpty()) return Map.of("error","not found");
        Ticket ticket = t.get();
        User requester = ou.get();
        // ensure only owner, assignee, agent, or admin can view
        if(ticket.getOwner()!=null && ticket.getOwner().getId().equals(requester.getId())) return ticket;
        if(ticket.getAssignee()!=null && ticket.getAssignee().getId().equals(requester.getId())) return ticket;
        if(requester.getRole()==Role.AGENT || requester.getRole()==Role.ADMIN) return ticket;
        return Map.of("error","forbidden");
    }

    @PostMapping("/{id}/comment")
    public Object addComment(@RequestHeader("Authorization") String auth, @PathVariable Long id, @RequestBody Map<String,String> body){
        Optional<User> ou = getUserFromToken(auth);
        if(ou.isEmpty()) return Map.of("error","unauthorized");
        Optional<Ticket> ot = ticketService.findById(id);
        if(ot.isEmpty()) return Map.of("error","ticket not found");
        Ticket ticket = ot.get();
        User u = ou.get();
        // permission: owner, assignee, agent, admin
        boolean allowed = false;
        if(ticket.getOwner()!=null && ticket.getOwner().getId().equals(u.getId())) allowed=true;
        if(ticket.getAssignee()!=null && ticket.getAssignee().getId().equals(u.getId())) allowed=true;
        if(u.getRole()==Role.AGENT || u.getRole()==Role.ADMIN) allowed=true;
        if(!allowed) return Map.of("error","forbidden");
        Comment c = new Comment();
        c.setTicket(ticket);
        c.setAuthor(u);
        c.setText(body.getOrDefault("text",""));
        commentService.save(c);
        ticket.getComments().add(c);
        ticketService.save(ticket);
        return c;
    }

    @PostMapping("/{id}/status")
    public Object changeStatus(@RequestHeader("Authorization") String auth, @PathVariable Long id, @RequestBody Map<String,String> body){
        Optional<User> ou = getUserFromToken(auth);
        if(ou.isEmpty()) return Map.of("error","unauthorized");
        Optional<Ticket> ot = ticketService.findById(id);
        if(ot.isEmpty()) return Map.of("error","ticket not found");
        Ticket ticket = ot.get();
        User u = ou.get();
        // Only assignee, agent, or admin can change status
        if(!(u.getRole()==Role.AGENT || u.getRole()==Role.ADMIN ||
             (ticket.getAssignee()!=null && ticket.getAssignee().getId().equals(u.getId())))){
            return Map.of("error","forbidden");
        }
        try{
            Status s = Status.valueOf(body.getOrDefault("status","OPEN"));
            ticket.setStatus(s);
            ticketService.save(ticket);
            return ticket;
        }catch(Exception e){
            return Map.of("error","invalid status");
        }
    }

    @PostMapping("/{id}/assign")
    public Object assign(@RequestHeader("Authorization") String auth, @PathVariable Long id, @RequestBody Map<String,String> body){
        Optional<User> ou = getUserFromToken(auth);
        if(ou.isEmpty()) return Map.of("error","unauthorized");
        User requester = ou.get();
        Optional<Ticket> ot = ticketService.findById(id);
        if(ot.isEmpty()) return Map.of("error","ticket not found");
        Ticket ticket = ot.get();
        // allow owner to reassign only if owner or admin or agent
        if(!(requester.getRole()==Role.ADMIN || requester.getRole()==Role.AGENT || ticket.getOwner().getId().equals(requester.getId()))){
            return Map.of("error","forbidden");
        }
        Long agentId = Long.parseLong(body.getOrDefault("assigneeId","0"));
        Optional<User> au = userService.findById(agentId);
        if(au.isEmpty()) return Map.of("error","assignee not found");
        ticket.setAssignee(au.get());
        ticketService.save(ticket);
        return ticket;
    }
}

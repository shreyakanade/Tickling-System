package com.example.ticketing.model;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@Entity
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    @Column(columnDefinition="TEXT")
    private String description;
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;
    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN;
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private User owner;

    @ManyToOne
    private User assignee;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // getters and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    public String getSubject(){return subject;}
    public void setSubject(String s){this.subject = s;}
    public String getDescription(){return description;}
    public void setDescription(String d){this.description = d;}
    public Priority getPriority(){return priority;}
    public void setPriority(Priority p){this.priority = p;}
    public Status getStatus(){return status;}
    public void setStatus(Status s){this.status = s;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime t){this.createdAt = t;}
    public User getOwner(){return owner;}
    public void setOwner(User u){this.owner = u;}
    public User getAssignee(){return assignee;}
    public void setAssignee(User a){this.assignee = a;}
    public List<Comment> getComments(){return comments;}
    public void setComments(List<Comment> c){this.comments = c;}
}

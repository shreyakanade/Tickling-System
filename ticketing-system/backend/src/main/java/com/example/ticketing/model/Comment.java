package com.example.ticketing.model;

import javax.persistence.*;
import java.time.*;

@Entity
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Ticket ticket;
    @ManyToOne
    private User author;
    @Column(columnDefinition="TEXT")
    private String text;
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    public Ticket getTicket(){return ticket;}
    public void setTicket(Ticket t){this.ticket = t;}
    public User getAuthor(){return author;}
    public void setAuthor(User a){this.author = a;}
    public String getText(){return text;}
    public void setText(String t){this.text = t;}
    public LocalDateTime getCreatedAt(){return createdAt;}
    public void setCreatedAt(LocalDateTime t){this.createdAt = t;}
}

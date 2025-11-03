package com.example.ticketing.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Column(unique=true)
    private String token; // simple bearer token for demo

    // getters and setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    public String getUsername(){return username;}
    public void setUsername(String u){this.username = u;}
    public String getPassword(){return password;}
    public void setPassword(String p){this.password = p;}
    public Role getRole(){return role;}
    public void setRole(Role r){this.role = r;}
    public String getToken(){return token;}
    public void setToken(String t){this.token = t;}
}

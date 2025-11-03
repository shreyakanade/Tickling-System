package com.example.ticketing.service;

import com.example.ticketing.model.Ticket;
import com.example.ticketing.repo.TicketRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TicketService {
    private final TicketRepository ticketRepo;
    public TicketService(TicketRepository ticketRepo){this.ticketRepo = ticketRepo;}
    public Ticket save(Ticket t){return ticketRepo.save(t);}
    public Optional<Ticket> findById(Long id){return ticketRepo.findById(id);}
    public List<Ticket> findByOwnerId(Long ownerId){return ticketRepo.findByOwnerId(ownerId);}
    public List<Ticket> findAll(){return ticketRepo.findAll();}
}

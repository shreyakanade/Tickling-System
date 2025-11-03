package com.example.ticketing.repo;

import com.example.ticketing.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByOwnerId(Long ownerId);
}

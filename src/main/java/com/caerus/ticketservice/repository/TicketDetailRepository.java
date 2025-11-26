package com.caerus.ticketservice.repository;

import com.caerus.ticketservice.domain.TicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketDetailRepository extends JpaRepository<TicketDetail, Long> {}

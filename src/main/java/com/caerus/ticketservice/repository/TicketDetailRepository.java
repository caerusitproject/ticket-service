package com.caerus.ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.caerus.ticketservice.domain.TicketDetail;
@Repository
public interface TicketDetailRepository extends JpaRepository<TicketDetail, Long> {}

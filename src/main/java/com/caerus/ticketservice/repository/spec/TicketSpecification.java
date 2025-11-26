package com.caerus.ticketservice.repository.spec;

import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.dto.TicketSearchRequest;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class TicketSpecification {

  public static Specification<Ticket> withFilters(TicketSearchRequest ticketSearchRequest) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (ticketSearchRequest.status() != null) {
        predicates.add(cb.equal(root.get("status"), ticketSearchRequest.status()));
      }

      if (ticketSearchRequest.priority() != null) {
        predicates.add(cb.equal(root.get("description"), ticketSearchRequest.priority()));
      }
      if (ticketSearchRequest.startDate() != null) {
        predicates.add(cb.equal(root.get("startDate"), ticketSearchRequest.startDate()));
      }
      if (ticketSearchRequest.endDate() != null) {
        predicates.add(cb.equal(root.get("endDate"), ticketSearchRequest.endDate()));
      }
      if (ticketSearchRequest.dueDate() != null) {
        predicates.add(cb.equal(root.get("dueDate"), ticketSearchRequest.dueDate()));
      }
      if (ticketSearchRequest.ticketId() != null) {
        predicates.add(cb.equal(root.get("id"), ticketSearchRequest.ticketId()));
      }

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }
}

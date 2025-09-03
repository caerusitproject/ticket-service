package com.caerus.ticketservice.repository.spec;

import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.dto.TicketSearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class TicketSpecification {

    public static Specification<Ticket> withFilters(TicketSearchRequest ticketSearchRequest) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (ticketSearchRequest.status() != null) {
                predicates.add(cb.equal(root.get("status"), ticketSearchRequest.status()));
            }

            if (ticketSearchRequest.priority() != null) {
                predicates.add(cb.equal(root.get("priority"), ticketSearchRequest.priority()));
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

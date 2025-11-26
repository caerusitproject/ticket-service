package com.caerus.ticketservice.repository.spec;

import com.caerus.ticketservice.domain.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategorySpecification {
  public Specification<Category> search(String search) {
    if (search == null || search.isBlank()) {
      return null;
    }
    String like = "%" + search.toLowerCase() + "%";
    return (root, query, cb) ->
        cb.or(
            cb.like(cb.lower(root.get("categoryName")), like),
            cb.like(cb.lower(root.get("categoryCode")), like));
  }

  public Specification<Category> deletedEquals(Boolean deleted) {
    return (root, query, cb) -> cb.equal(root.get("deleted"), deleted);
  }
}

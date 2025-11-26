package com.caerus.ticketservice.repository.spec;

import com.caerus.ticketservice.domain.Subcategory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class SubcategorySpecification {
  public Specification<Subcategory> byCategory(Long categoryId) {
    return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
  }

  public Specification<Subcategory> search(String search) {
    if (search == null || search.isBlank()) {
      return null;
    }
    String like = "%" + search.toLowerCase() + "%";
    return (root, query, cb) ->
        cb.or(cb.like(cb.lower(root.get("name")), like), cb.like(cb.lower(root.get("code")), like));
  }

  public Specification<Subcategory> deletedEquals(Boolean deleted) {
    return (root, query, cb) -> cb.equal(root.get("deleted"), deleted);
  }
}

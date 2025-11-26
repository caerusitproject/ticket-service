package com.caerus.ticketservice.repository.spec;

import com.caerus.ticketservice.domain.Asset;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AssetSpecification {

  public Specification<Asset> search(String search) {
    if (search == null || search.isBlank()) {
      return null;
    }
    String like = "%" + search.toLowerCase() + "%";
    return (root, query, cb) ->
        cb.or(
            cb.like(cb.lower(root.get("assetTag")), like),
            cb.like(cb.lower(root.get("assetName")), like));
  }

  public Specification<Asset> deletedEquals(Boolean deleted) {
    return (root, query, cb) -> cb.equal(root.get("deleted"), deleted);
  }
}

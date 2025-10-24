package com.caerus.ticketservice.repository;

import com.caerus.ticketservice.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Long>, JpaSpecificationExecutor<Subcategory> {
}

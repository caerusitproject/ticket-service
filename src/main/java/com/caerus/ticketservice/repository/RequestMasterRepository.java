package com.caerus.ticketservice.repository;

import com.caerus.ticketservice.domain.RequestMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestMasterRepository extends JpaRepository<RequestMaster, Long> {}

package com.caerus.ticketservice.repository;

import com.caerus.ticketservice.domain.ResponseMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseMasterRepository extends JpaRepository<ResponseMaster, Long> {}

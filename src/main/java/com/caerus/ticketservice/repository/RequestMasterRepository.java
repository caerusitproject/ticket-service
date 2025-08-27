package com.caerus.ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerus.ticketservice.domain.RequestMaster;

public interface RequestMasterRepository extends JpaRepository<RequestMaster, Long> {}

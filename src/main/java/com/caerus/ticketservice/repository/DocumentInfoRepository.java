package com.caerus.ticketservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.caerus.ticketservice.domain.DocumentInfo;
@Repository
public interface DocumentInfoRepository extends JpaRepository<DocumentInfo, Integer> {}

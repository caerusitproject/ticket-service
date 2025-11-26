package com.caerus.ticketservice.repository;

import com.caerus.ticketservice.domain.TimeAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeAnalysisRepository extends JpaRepository<TimeAnalysis, Long> {}

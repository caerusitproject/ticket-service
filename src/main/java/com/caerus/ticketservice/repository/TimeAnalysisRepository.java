package com.caerus.ticketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerus.ticketservice.domain.TimeAnalysis;

public interface TimeAnalysisRepository extends JpaRepository<TimeAnalysis, Long> {}

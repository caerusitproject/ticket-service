package com.caerus.ticketservice.service;

import com.caerus.ticketservice.dto.DocumentInfoDto;
import com.caerus.ticketservice.dto.DocumentInfoRequestDto;

public interface DocumentInfoService {
    DocumentInfoDto patchDocumentById(Long id, DocumentInfoRequestDto documentRequestDto);

    void replaceDocumentsForTicket(com.caerus.ticketservice.domain.Ticket ticket, java.util.List<com.caerus.ticketservice.domain.DocumentInfo> newDocs);
}

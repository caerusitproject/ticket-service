package com.caerus.ticketservice.service;

import com.caerus.ticketservice.dto.DocumentInfoDto;
import com.caerus.ticketservice.dto.DocumentInfoRequestDto;

public interface DocumentInfoService {
    DocumentInfoDto patchDocumentById(Long id, DocumentInfoRequestDto documentRequestDto);

}

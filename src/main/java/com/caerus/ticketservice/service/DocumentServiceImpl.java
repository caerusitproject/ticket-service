package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.DocumentInfo;
import com.caerus.ticketservice.domain.Ticket;
import com.caerus.ticketservice.dto.DocumentInfoDto;
import com.caerus.ticketservice.dto.DocumentInfoRequestDto;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.mapper.DocumentInfoMapper;
import com.caerus.ticketservice.repository.DocumentInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentInfoService {

  private final DocumentInfoRepository documentInfoRepository;
  private final DocumentInfoMapper documentInfoMapper;

  @Override
  public DocumentInfoDto patchDocumentById(Long id, DocumentInfoRequestDto documentRequestDto) {
    DocumentInfo documentInfo =
        documentInfoRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Document not found with id: " + id));

    documentInfoMapper.patchDocumentInfoFromDto(documentRequestDto, documentInfo);
    return documentInfoMapper.toDto(documentInfoRepository.save(documentInfo));
  }

  @Override
  @Transactional
  public void replaceDocumentsForTicket(Ticket ticket, List<DocumentInfo> newDocs) {

    ticket.getDocuments().clear();

    for (DocumentInfo doc : newDocs) {
      doc.setTicket(ticket);
      ticket.getDocuments().add(doc);
    }
  }
}

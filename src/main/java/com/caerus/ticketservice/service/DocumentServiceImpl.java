package com.caerus.ticketservice.service;

import com.caerus.ticketservice.domain.DocumentInfo;
import com.caerus.ticketservice.dto.DocumentInfoDto;
import com.caerus.ticketservice.dto.DocumentInfoRequestDto;
import com.caerus.ticketservice.exception.NotFoundException;
import com.caerus.ticketservice.mapper.DocumentInfoMapper;
import com.caerus.ticketservice.repository.DocumentInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentInfoService {

    private final DocumentInfoRepository documentInfoRepository;
    private final DocumentInfoMapper documentInfoMapper;

    @Override
    public DocumentInfoDto patchDocumentById(Long id, DocumentInfoRequestDto documentRequestDto) {
        DocumentInfo documentInfo = documentInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Document not found with id: " + id));

        documentInfoMapper.patchDocumentInfoFromDto(documentRequestDto, documentInfo);
        return documentInfoMapper.toDto(documentInfoRepository.save(documentInfo));
    }
}

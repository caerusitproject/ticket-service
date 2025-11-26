package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.ApiResponse;
import com.caerus.ticketservice.dto.DocumentInfoDto;
import com.caerus.ticketservice.dto.DocumentInfoRequestDto;
import com.caerus.ticketservice.service.DocumentInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentInfoController {

  private final DocumentInfoService documentInfoService;

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<DocumentInfoDto>> patchDocument(
      @PathVariable Long id, @RequestBody DocumentInfoRequestDto documentRequestDto) {
    DocumentInfoDto updatedDocument = documentInfoService.patchDocumentById(id, documentRequestDto);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success("Document updated successfully", updatedDocument));
  }
}

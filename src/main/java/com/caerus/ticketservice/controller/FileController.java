package com.caerus.ticketservice.controller;

import com.caerus.ticketservice.dto.ApiResponse;
import com.caerus.ticketservice.dto.FileUploadResponse;
import com.caerus.ticketservice.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileStorageService fileStorageService;

    @PostMapping("/temp/drafts/{contextId}")
    public ResponseEntity<ApiResponse<List<FileUploadResponse>>> uploadTicketDocument(
            @PathVariable String contextId,
            @RequestParam("files") List<MultipartFile> files) {

        if (files.isEmpty() || contextId.isBlank()) {
            return ResponseEntity.badRequest().body(ApiResponse.failure("Invalid request"));
        }

        List<FileUploadResponse> uploadedFiles = files.stream()
                .map(file -> new FileUploadResponse(
                        fileStorageService.storeFile(file, "temp/drafts/" + contextId),
                        file.getSize()))
                .toList();

        return ResponseEntity.ok(ApiResponse.success("Files uploaded successfully", uploadedFiles));
    }

    @GetMapping("/tickets/{ticketId}/documents/{fileName:.+}")
    public ResponseEntity<Resource> serveTicketDocument(
            @PathVariable Long ticketId,
            @PathVariable String fileName) throws IOException {

        String relativePath = "tickets/" + ticketId + "/documents/" + fileName;
        Resource file = fileStorageService.loadFile(relativePath);

        if (!file.exists() || !file.isReadable()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(file.getFile().toPath());
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .cacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic())
                .body(file);
    }

    @DeleteMapping("/temp/drafts/{contextId}/{fileName:.+}")
    public ResponseEntity<Void> deleteDraftFile(
            @PathVariable String contextId,
            @PathVariable String fileName) {

        String relativePath = "temp/drafts/" + contextId + "/" + fileName;
        fileStorageService.deleteFile(relativePath);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/tickets/{ticketId}/{fileName:.+}")
    public ResponseEntity<Void> deleteTicketFile(
            @PathVariable Long ticketId,
            @PathVariable String fileName) {

        String relativePath = "tickets/" + ticketId + "/" + fileName;
        fileStorageService.deleteFile(relativePath);
        return ResponseEntity.noContent().build();
    }

}

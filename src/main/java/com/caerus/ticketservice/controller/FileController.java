package com.caerus.ticketservice.controller;

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
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final FileStorageService fileStorageService;

    @PostMapping("/tickets/documents")
    public ResponseEntity<String> uploadTicketDocument(
            @RequestParam("file") MultipartFile file) {

        String fileUrl = fileStorageService.storeFile(file, "temp/documents");
        return ResponseEntity.ok(fileUrl);
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

    @PostMapping("/tickets/editor-image")
    public ResponseEntity<Map<String, Object>> uploadEditorImage(@RequestParam("upload") MultipartFile image) {
        String fileUrl = fileStorageService.storeFile(image, "temp/editor");

        Map<String, Object> response = Map.of(
                "uploaded", true,
                "url", fileUrl
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tickets/{ticketId}/editor/{fileName:.+}")
    public ResponseEntity<Void> deleteTicketDocument(
            @PathVariable Long ticketId,
            @PathVariable String fileName) {

        String relativePath = "tickets/" + ticketId + "/editor/" + fileName;
        fileStorageService.deleteFile(relativePath);
        return ResponseEntity.noContent().build();
    }

}

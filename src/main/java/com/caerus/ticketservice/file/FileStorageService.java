package com.caerus.ticketservice.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file, String relativePath);

    Resource loadFile(String relativePath);

    void moveTempFilesToTicketFolder(Long ticketId);
}

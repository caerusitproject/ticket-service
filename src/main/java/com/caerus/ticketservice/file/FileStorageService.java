package com.caerus.ticketservice.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface FileStorageService {
    String storeFile(MultipartFile file, String relativePath);

    Resource loadFile(String relativePath);

    Map<String, String> moveTempFilesToTicketFolder(Long ticketId, List<String> fileUrls);

    void deleteFile(String relativePath);
}

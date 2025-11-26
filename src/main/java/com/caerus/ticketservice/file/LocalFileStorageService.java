package com.caerus.ticketservice.file;

import com.caerus.ticketservice.exception.file.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class LocalFileStorageService implements FileStorageService {

  private final Path rootLocation;
  private static final long MAX_FILE_SIZE_BYTES = 50 * 1024 * 1024; // 50 MB

  public LocalFileStorageService(@Value("${file.upload-dir:uploads}") String uploadDir) {
    this.rootLocation = Path.of(uploadDir).toAbsolutePath().normalize();
    try {
      Files.createDirectories(rootLocation);
      log.info("Initialized file storage at: {}", rootLocation);
    } catch (IOException e) {
      log.error("Could not initialize upload directory", e);
      throw new FileInitializationException("Could not initialize upload directory", e);
    }
  }

  @Override
  public String storeFile(MultipartFile file, String relativePath) {
    try {
      if (file.isEmpty()) {
        throw new EmptyFileUploadException("Failed to store empty file");
      }

      if (file.getSize() > MAX_FILE_SIZE_BYTES) {
        throw new FileSizeExceededException("File size exceeds the maximum allowed size");
      }
      Path destinationDir = rootLocation.resolve(relativePath).normalize();
      Files.createDirectories(destinationDir);

      String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
      Path targetPath = destinationDir.resolve(uniqueFileName);

      Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
      log.info("Stored file: {}", targetPath);

      return "/uploads/" + relativePath + "/" + uniqueFileName;
    } catch (IOException ex) {
      throw new FileStorageException("Failed to store file", ex);
    }
  }

  private String generateUniqueFileName(String originalFilename) {
    String extension =
        Optional.ofNullable(originalFilename)
            .filter(name -> name.contains("."))
            .map(name -> name.substring(name.lastIndexOf(".")))
            .orElse("");
    return UUID.randomUUID() + extension;
  }

  @Override
  public Resource loadFile(String relativePath) {
    try {
      Path filePath = rootLocation.resolve(relativePath).normalize();
      if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
        throw new FileNotFoundException("File not found: " + relativePath);
      }
      return new FileSystemResource(filePath);
    } catch (InvalidPathException e) {
      throw new FileSecurityException("Invalid file path: " + relativePath + " : " + e);
    }
  }

  @Override
  public Map<String, String> moveTempFilesToTicketFolder(Long ticketId, List<String> fileUrls) {
    if (fileUrls == null || fileUrls.isEmpty()) {
      log.info("No temp files to move for ticket {}", ticketId);
      return Map.of();
    }
    Path ticketDir = rootLocation.resolve("tickets").resolve(ticketId.toString());

    try {
      Files.createDirectories(ticketDir);
    } catch (IOException e) {
      throw new FileMoveException("Failed to create ticket directory for ticket " + ticketId, e);
    }

    Map<String, String> movedFileMap = new HashMap<>();

    for (String fileUrl : fileUrls) {
      try {
        String relativePath = fileUrl.replaceFirst("^/uploads/", "");

        Path sourcePath = rootLocation.resolve(relativePath).normalize();

        if (!Files.exists(sourcePath)) {
          log.warn("Source file not found: {}", sourcePath);
          continue;
        }

        Path destinationPath = ticketDir.resolve(sourcePath.getFileName());
        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        log.info("Moved {} → {}", sourcePath, destinationPath);

        String newUrl = "/uploads/tickets/" + ticketId + "/" + destinationPath.getFileName();
        movedFileMap.put(fileUrl, newUrl);

      } catch (Exception e) {
        log.error("Failed to move file from URL: {}", fileUrl, e);
        throw new FileMoveException("Failed to move file from URL: " + fileUrl, e);
      }
    }

    return movedFileMap;
  }

  @Override
  public void deleteFile(String relativePath) {
    try {
      Path filePath = rootLocation.resolve(relativePath).normalize();
      if (!filePath.startsWith(rootLocation)) {
        log.error("Unauthorized file deletion attempt: {}", relativePath);
        throw new FileSecurityException("Attempt to delete file outside of upload directory");
      }
      if (Files.exists(filePath)) {
        Files.delete(filePath);
        log.info("Deleted file successfully: {}", filePath);
      } else {
        log.warn("File not found for deletion: {}", filePath);
        throw new FileNotFoundException("File not found: " + relativePath);
      }

    } catch (IOException e) {
      log.error("Failed to delete file: {}", relativePath, e);
      throw new FileDeletionException("Failed to delete file: " + relativePath, e);
    }
  }
}

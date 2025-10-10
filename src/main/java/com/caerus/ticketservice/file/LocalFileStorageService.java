package com.caerus.ticketservice.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Service
public class LocalFileStorageService implements FileStorageService {

    private final Path rootLocation;

    public LocalFileStorageService(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize upload directory", e);
        }
    }

    @Override
    public String storeFile(MultipartFile file, String relativePath) {
        try {
            Path destinationDir = rootLocation.resolve(relativePath).normalize();
            Files.createDirectories(destinationDir);

            String extension = "";
            String originalName = file.getOriginalFilename();
            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }

            String uniqueFileName = UUID.randomUUID() + extension;
            Path targetPath = destinationDir.resolve(uniqueFileName);

            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            log.info("Stored file: {}", targetPath);

            return "/uploads/" + relativePath + "/" + uniqueFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to store file", ex);
        }
    }

    @Override
    public Resource loadFile(String relativePath) {
        Path filePath = rootLocation.resolve(relativePath).normalize();
        return new FileSystemResource(filePath);
    }

    @Override
    public void moveTempFilesToTicketFolder(Long ticketId) {
        Path tempDir = rootLocation.resolve("temp");
        Path targetDir = rootLocation.resolve("tickets/" + ticketId);

        try (Stream<Path> files = Files.walk(tempDir)) {
            files.filter(Files::isRegularFile).forEach(file -> {
                try {
                    Path relative = tempDir.relativize(file);
                    Path destination = targetDir.resolve(relative);
                    Files.createDirectories(destination.getParent());
                    Files.move(file, destination, StandardCopyOption.REPLACE_EXISTING);
                    log.info("Moved {} → {}", file, destination);
                } catch (IOException e) {
                    log.error("Failed to move file {}", file, e);
                }
            });
        } catch (IOException e) {
            log.warn("No temp files found to move for ticket {}", ticketId);
        }
    }

//    public void deleteFile(String relativePath) {
//        try {
//            Files.deleteIfExists(Paths.get(fileStorageProperties.getUploadDir()).resolve(relativePath).normalize());
//        } catch (IOException e) {
//            log.warn("Failed to delete file {}", relativePath, e);
//        }
//    }

}

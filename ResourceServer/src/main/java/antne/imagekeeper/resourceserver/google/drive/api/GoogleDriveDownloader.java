package antne.imagekeeper.resourceserver.google.drive.api;

import com.google.api.services.drive.Drive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@Slf4j
public class GoogleDriveDownloader {

    private final Drive driveService;

    @Autowired
    public GoogleDriveDownloader(Drive driveService) {
        this.driveService = driveService;
    }

    public byte[] downloadFileBytesFromDrive(String fileId) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = driveService.files().get(fileId).executeMediaAsInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            log.info("Successfully download image {}", fileId);
        }

        return outputStream.toByteArray();

    }
}


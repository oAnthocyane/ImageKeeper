package antne.imagekeeper.resourceserver.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
public class ImageCreator {

    public static File createTemporaryImageFile(byte[] imageBytes, String fileExtension) throws IOException {
        File tempFile = File.createTempFile("tempImage", "." + fileExtension);

        Files.write(tempFile.toPath(), imageBytes);
        log.info("Write bytes to temporary image");
        return tempFile;
    }

}


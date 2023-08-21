package antne.imagekeeper.telegrambot.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ImageCreator {

    public static File createTemporaryImageFile(byte[] imageBytes, String fileExtension) throws IOException {
        File tempFile = File.createTempFile("tempImage", "." + fileExtension);

        Files.write(tempFile.toPath(), imageBytes);
        return tempFile;
    }

}

package antne.imagekeeper.telegrambot.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

public class ImageDownloader {

    public static byte[] downloadPhoto(PhotoSize photo, String botToken) {
        String photoFileId = photo.getFileId();
        String photoUrl = getPhotoUrl(botToken, photoFileId);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(photoUrl, byte[].class);
        return response.getBody();
    }

    public static String getPhotoUrl(String botToken, String photoFileId) {
        return String.format("https://api.telegram.org/bot%s/getFile?file_id=%s", botToken, photoFileId);
    }
}

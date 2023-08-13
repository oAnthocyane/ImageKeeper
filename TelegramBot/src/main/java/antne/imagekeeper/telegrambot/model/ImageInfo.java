package antne.imagekeeper.telegrambot.model;

import lombok.Data;

import java.util.Date;

@Data
public class ImageInfo {
    private Long id;

    private String uniqPhrase;

    private String[] keysPhrase;

    private String pathToImage;

    private User user;

    private Group group;

    private Date createdAt;
}

package antne.imagekeeper.resourceserver.model.data;

import lombok.Data;

import java.util.List;

@Data
public class ImageInfoDTO {
    private String uniqPhrase;
    private List<String> keysPhrase;
    private long userId;
    private List<String> groupsName;
    private byte[] photo;
}

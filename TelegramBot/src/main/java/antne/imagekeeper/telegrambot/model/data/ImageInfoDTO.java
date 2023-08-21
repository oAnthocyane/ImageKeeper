package antne.imagekeeper.telegrambot.model.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class ImageInfoDTO {
    private String uniqPhrase;
    private List<String> keysPhrase;
    private long userId;
    private List<String> groupsName;
    private byte[] photo;

    @Override
    public String toString() {
        return "ImageInfoDTO{" +
                "uniqPhrase='" + uniqPhrase + '\'' +
                ", keysPhrase=" + keysPhrase +
                ", userId=" + userId +
                ", groupsName=" + groupsName +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}

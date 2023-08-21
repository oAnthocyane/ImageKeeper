package antne.imagekeeper.telegrambot.properties.server;

import lombok.Data;
import lombok.Getter;

@Data
public class API {
    private String addUser;
    private String getGroups;
    private String addGroup;
    private String leaveGroup;
    private String joinGroup;
    private String addImage;
    private String findByUniqPhrase;
}

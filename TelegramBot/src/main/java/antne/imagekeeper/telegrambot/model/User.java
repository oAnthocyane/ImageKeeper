package antne.imagekeeper.telegrambot.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    private Long id;

    private Long userId;

    @NotBlank(message = "Field must not be blank")
    private String username;

    private Long chatId;

    private List<Group> groups = new ArrayList<>();

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group){
        groups.remove(group);
    }
}

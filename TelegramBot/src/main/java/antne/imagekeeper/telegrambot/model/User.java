package antne.imagekeeper.telegrambot.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {

    private Long id;

    private Long userId;

    private String username;

    private Long chatId;

    private List<Group> groups = new ArrayList<>();

}

package antne.imagekeeper.telegrambot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Group {

    private Long id;

    @NotBlank(message = "Field must not be blank")
    private String name;

    @NotBlank(message = "Field must not be blank")
    private String password;

    private User adminUser;

    private String linkToJoin;

    private List<User> users = new ArrayList<>();


}


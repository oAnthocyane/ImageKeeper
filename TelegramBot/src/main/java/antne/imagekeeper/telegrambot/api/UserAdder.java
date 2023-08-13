package antne.imagekeeper.telegrambot.api;

import antne.imagekeeper.telegrambot.api.dataSender.DataPostSender;
import antne.imagekeeper.telegrambot.model.User;
import antne.imagekeeper.telegrambot.properties.Config;
import org.springframework.core.ParameterizedTypeReference;

public class UserAdder extends DataPostSender<Long> {

    public UserAdder(Object... params) {
        super(params);
    }

    public void sendUser(User user){
        ParameterizedTypeReference<ApiResponse<Long>> responseType = new ParameterizedTypeReference<>() {};
        send(user, url, responseType);
    }

    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer()
                .getResource().getApi().getAddUser();
    }
}

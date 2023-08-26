package antne.imagekeeper.telegrambot.api;

import antne.imagekeeper.telegrambot.api.dataSender.DataPostSender;
import antne.imagekeeper.telegrambot.properties.Config;
import org.springframework.core.ParameterizedTypeReference;

public class GroupLeaver extends DataPostSender<Boolean> {

    public GroupLeaver(Object... params) {
        super(params);
    }

    public void refactorGroup() {
        ParameterizedTypeReference<ApiResponse<Boolean>> responseType = new ParameterizedTypeReference<>() {
        };
        send(url, responseType);
    }

    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer()
                .getResource().getApi().getLeaveGroup();
    }
}

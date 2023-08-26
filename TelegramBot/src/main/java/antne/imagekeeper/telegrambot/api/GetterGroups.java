package antne.imagekeeper.telegrambot.api;

import antne.imagekeeper.telegrambot.api.dataSender.DataGetSender;
import antne.imagekeeper.telegrambot.model.Group;
import antne.imagekeeper.telegrambot.properties.Config;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class GetterGroups extends DataGetSender<List<Group>> {

    public GetterGroups(Object... params) {
        super(params);
    }


    public void sendRequest() {
        ParameterizedTypeReference<ApiResponse<List<Group>>> responseType = new ParameterizedTypeReference<>() {
        };
        send(url, responseType);
    }

    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer().getResource()
                .getApi().getGetGroups();
    }
}

package antne.imagekeeper.telegrambot.api;

import antne.imagekeeper.telegrambot.api.dataSender.DataPostSender;
import antne.imagekeeper.telegrambot.model.Group;
import antne.imagekeeper.telegrambot.properties.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;

public class GroupAdder extends DataPostSender<Long> {

    public GroupAdder(Object... params) {
        super(params);
    }
    public void sendGroup(Group group){
        ParameterizedTypeReference<ApiResponse<Long>> responseType = new ParameterizedTypeReference<>() {};
        send(group, url, responseType);
    }

    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer().getResource()
                .getApi().getAddGroup();
    }
}

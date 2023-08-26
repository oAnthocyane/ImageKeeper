package antne.imagekeeper.telegrambot.api;

import antne.imagekeeper.telegrambot.properties.Config;

public class GroupJoiner extends GroupLeaver {

    public GroupJoiner(Object... params) {
        super(params);
    }


    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer()
                .getResource().getApi().getJoinGroup();
    }
}

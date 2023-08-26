package antne.imagekeeper.telegrambot.api.finder;

import antne.imagekeeper.telegrambot.properties.Config;

public class FinderByUniqPhraseAndAllGroup extends FinderPhotos {
    public FinderByUniqPhraseAndAllGroup(Object... params) {
        super(params);
    }

    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer().getResource()
                .getApi().getFindByUniqPhraseAndUserAndAllGroups();
    }
}

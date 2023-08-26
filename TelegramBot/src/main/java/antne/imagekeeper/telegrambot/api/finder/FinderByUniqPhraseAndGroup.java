package antne.imagekeeper.telegrambot.api.finder;

import antne.imagekeeper.telegrambot.properties.Config;

public class FinderByUniqPhraseAndGroup extends FinderPhotos {
    public FinderByUniqPhraseAndGroup(Object... params) {
        super(params);
    }

    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer().getResource()
                .getApi().getFindByUniqPhraseAndUserAndGroups();
    }
}

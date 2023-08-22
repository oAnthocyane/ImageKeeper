package antne.imagekeeper.telegrambot.api.finder;

import antne.imagekeeper.telegrambot.properties.Config;

public class FinderByKeyPhrasesAndAllGroups extends FinderPhotos{

    public FinderByKeyPhrasesAndAllGroups(Object... params) {
        super(params);
    }

    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer().getResource()
                .getApi().getFindByKeyPhrasesAndAllGroup();
    }
}

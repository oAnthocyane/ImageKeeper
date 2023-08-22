package antne.imagekeeper.telegrambot.api.finder;

import antne.imagekeeper.telegrambot.properties.Config;

public class finderByKeyPhraseAndGroups extends FinderPhotos{
    public finderByKeyPhraseAndGroups(Object... params) {
        super(params);
    }

    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer().getResource()
                .getApi().getFindByKeyPhrasesAndGroup();
    }
}

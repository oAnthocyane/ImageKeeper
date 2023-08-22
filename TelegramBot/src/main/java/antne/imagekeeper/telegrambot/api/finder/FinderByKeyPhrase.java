package antne.imagekeeper.telegrambot.api.finder;

import antne.imagekeeper.telegrambot.properties.Config;


public class FinderByKeyPhrase extends FinderPhotos {
    public FinderByKeyPhrase(Object... params){
        super(params);
    }

    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer().getResource().
                getApi().getFindByKeyPhrase();
    }


}

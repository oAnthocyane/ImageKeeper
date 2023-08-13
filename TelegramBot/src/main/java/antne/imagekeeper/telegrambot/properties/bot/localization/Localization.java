package antne.imagekeeper.telegrambot.properties.bot.localization;

import antne.imagekeeper.telegrambot.properties.bot.localization.languages.En;
import antne.imagekeeper.telegrambot.properties.bot.localization.languages.Language;
import antne.imagekeeper.telegrambot.properties.bot.localization.languages.Ru;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Localization {

    private Ru ru;

    private En en;

    private Map<String, Language> languages;

    public void init(){
        languages = new HashMap<>();
        languages.put("ru", ru);
        languages.put("en", en);
    }

    public Language getLanguage(String language){
        return languages.get(language);
    }

    public boolean containsLanguage(String language){
        return languages.containsKey(language);
    }
}

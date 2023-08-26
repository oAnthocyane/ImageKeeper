package antne.imagekeeper.telegrambot.localization;

import antne.imagekeeper.telegrambot.properties.Config;
import antne.imagekeeper.telegrambot.properties.bot.localization.Localization;
import antne.imagekeeper.telegrambot.properties.bot.localization.languages.Language;

public class CurrentLanguage {

    private static Localization localization = Config.getSettings().getBot().getInfo().getLocalization();

    private static Language currentLanguage = localization.getEn();


    public static Language getCurrentLanguage() {
        return currentLanguage;
    }

    public static void setCurrentLanguage(String language) {
        CurrentLanguage.currentLanguage = localization.getLanguage(language);
    }

    public static boolean hasCurrentLanguage(String language) {
        return localization.containsLanguage(language);
    }


}

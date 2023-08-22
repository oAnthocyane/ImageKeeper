package antne.imagekeeper.telegrambot.properties.bot.localization.languages;

import lombok.Data;

@Data
public class Ru extends Language{
    private String infoAboutBot;
    private String commandStart;
    private String commandHelp;
    private String commandChangeLanguage;
    private String commandCreateGroup;
    private String commandLeaveGroup;
    private String commandJoinGroup;
    private String commandGetGroups;
    private String commandFindByUniqPhrase;
    private String commandFindByKeyPhrase;
    private String commandFindByUniqPhraseAndAllGroups;
    private String commandFindByUniqPhraseAndGroups;
    private String commandAddGroup;
    private String commandFindByKeyPhrasesAndGroups;
    private String commandFindByKeyPhrasesAndAllGroups;
    private String changeLanguageSuccessful;
    private String changeLanguageError;
    private String canNotAddImage;
    private String image;
    private String yourGroups;
    private String notAllArguments;
    private String onlyOneImageCanAdd;
    private String greatCreateGroup;
    private String groupExist;
    private String imageNotExist;
    private String done;
    private String groupNotExist;
}

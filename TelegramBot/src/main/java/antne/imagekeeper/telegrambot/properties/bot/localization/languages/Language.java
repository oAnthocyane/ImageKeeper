package antne.imagekeeper.telegrambot.properties.bot.localization.languages;

abstract public class Language {
    abstract public String getInfoAboutBot();

    abstract public String getCommandStart();

    abstract public String getCommandHelp();

    abstract public String getCommandChangeLanguage();

    abstract public String getCommandCreateGroup();

    abstract public String getCommandLeaveGroup();

    abstract public String getCommandJoinGroup();

    abstract public String getCommandGetGroups();

    abstract public String getCommandAddGroup();

    abstract public String getChangeLanguageSuccessful();

    abstract public String getChangeLanguageError();

    abstract public String getCommandFindByUniqPhrase();

    abstract public String getCommandFindByKeyPhrase();

    abstract public String getCommandFindByUniqPhraseAndAllGroups();

    abstract public String getCommandFindByUniqPhraseAndGroups();

    abstract public String getCommandFindByKeyPhrasesAndGroups();

    abstract public String getCommandFindByKeyPhrasesAndAllGroups();

    abstract public String getCommandFind();

    abstract public String getCommandNotExist();

    abstract public String getCanNotAddImage();

    abstract public String getImage();

    abstract public String getOnlyOneImageCanAdd();

    abstract public String getYourGroups();

    abstract public String getNotAllArguments();

    abstract public String getGreatCreateGroup();

    abstract public String getGroupExist();

    abstract public String getImageNotExist();

    abstract public String getDone();

    abstract public String getGroupNotExist();

}

package antne.imagekeeper.telegrambot.properties.bot.localization.languages;

import lombok.Data;

@Data
public class En extends Language{
    private String infoAboutBot;
    private String commandStart;
    private String commandHelp;
    private String commandChangeLanguage;
    private String commandCreateGroup;
    private String commandLeaveGroup;
    private String commandJoinGroup;
    private String commandGetGroups;
    private String changeLanguageSuccessful;
    private String changeLanguageError;
    private String yourGroups;
    private String greatCreateGroup;
    private String groupExist;
    private String notAllArguments;
    private String done;
    private String userNotInGroup;
}

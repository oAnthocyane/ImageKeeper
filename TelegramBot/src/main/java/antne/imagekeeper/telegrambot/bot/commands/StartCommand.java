package antne.imagekeeper.telegrambot.bot.commands;

import antne.imagekeeper.telegrambot.api.UserAdder;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import antne.imagekeeper.telegrambot.model.User;
import antne.imagekeeper.telegrambot.properties.Config;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand implements IBotCommand {

    private final String urlServer = Config.getSettings().getServer().getResource()
            .getUrl();
    private final String urlAddUser = Config.getSettings().getServer().getResource()
            .getApi().getAddUser();

    private final String url = urlServer + urlAddUser;

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandStart();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
        Long id = message.getFrom().getId();
        String username = message.getFrom().getUserName();
        Long chatID = message.getChatId();
        User newUser = new User();
        newUser.setUserId(id);
        newUser.setUsername(username);
        newUser.setChatId(chatID);
        UserAdder userAdder = new UserAdder();
        userAdder.sendUser(newUser);
        IBotCommand helpCommand = new HelpCommand();
        helpCommand.processMessage(absSender, message, strings);
    }
}

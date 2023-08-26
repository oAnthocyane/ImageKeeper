package antne.imagekeeper.telegrambot.bot.commands;

import antne.imagekeeper.telegrambot.api.GetterGroups;
import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import antne.imagekeeper.telegrambot.model.Group;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class GetGroupsCommand implements IBotCommand {


    @Override
    public String getCommandIdentifier() {
        return "get_groups";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandGetGroups();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        Long id = message.getFrom().getId();

        GetterGroups getterGroups = new GetterGroups(id);
        getterGroups.sendRequest();
        List<Group> groups = getterGroups.getApiResponse().getData();
        StringBuilder sendText = new StringBuilder();
        sendText.append(CurrentLanguage.getCurrentLanguage().getYourGroups());
        for (Group group : groups) {
            sendText.append("\n").append(group.getName());
        }

        try {
            MessageSender.sendMessage(absSender, message.getChatId(), sendText.toString());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

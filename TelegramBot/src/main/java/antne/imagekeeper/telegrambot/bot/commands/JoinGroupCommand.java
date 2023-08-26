package antne.imagekeeper.telegrambot.bot.commands;

import antne.imagekeeper.telegrambot.api.GroupJoiner;
import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class JoinGroupCommand implements IBotCommand {

    @Override
    public String getCommandIdentifier() {
        return "join_group";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandJoinGroup();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        String sendText;
        if (arguments.length < 2) sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();
        else {
            String groupName = arguments[0];
            String groupPassword = arguments[1];
            Long userId = message.getFrom().getId();
            GroupJoiner groupJoiner = new GroupJoiner(groupName, groupPassword, userId);
            groupJoiner.refactorGroup();
            if (groupJoiner.isSuccessfullyResponse()) sendText = CurrentLanguage.getCurrentLanguage()
                    .getDone();
            else sendText = CurrentLanguage.getCurrentLanguage().getGroupNotExist();
        }

        try {
            MessageSender.sendMessage(absSender, message.getChatId(), sendText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

package antne.imagekeeper.telegrambot.bot.commands;

import antne.imagekeeper.telegrambot.bot.TelegramBot;
import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import antne.imagekeeper.telegrambot.properties.Config;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collection;

public class HelpCommand implements IBotCommand {

    @Override
    public String getCommandIdentifier() {
        return "help";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandHelp();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {

        String messageBot = CurrentLanguage.getCurrentLanguage().getInfoAboutBot();

        Collection<IBotCommand> registeredCommands = TelegramBot.getInstance().getRegisteredCommands();
        StringBuilder commandsDescription = new StringBuilder();
        commandsDescription.append("\n\n");
        for (IBotCommand commandBot: registeredCommands) {
            commandsDescription.append("/").append(commandBot.getCommandIdentifier())
                    .append(" ").append(commandBot.getDescription()).append("\n");
        }
        messageBot += commandsDescription.toString();

        try {
            MessageSender.sendMessage(absSender, message.getChatId(), messageBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

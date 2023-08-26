package antne.imagekeeper.telegrambot.bot.commands;

import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ChangeLanguageCommand implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "change_language";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandChangeLanguage();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        String language = arguments[0];
        String sendText;
        if (CurrentLanguage.hasCurrentLanguage(language)) {
            CurrentLanguage.setCurrentLanguage(language);
            sendText = CurrentLanguage.getCurrentLanguage().getChangeLanguageSuccessful();
        } else {
            sendText = CurrentLanguage.getCurrentLanguage().getChangeLanguageError();
        }
        try {
            MessageSender.sendMessage(absSender, message.getChatId(), sendText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

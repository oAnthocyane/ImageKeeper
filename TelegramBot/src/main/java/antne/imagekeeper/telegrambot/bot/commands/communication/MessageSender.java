package antne.imagekeeper.telegrambot.bot.commands.communication;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageSender {
    public static void sendMessage(AbsSender absSender, long chatId, String sendText) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(sendText);
        absSender.execute(sendMessage);
    }
}

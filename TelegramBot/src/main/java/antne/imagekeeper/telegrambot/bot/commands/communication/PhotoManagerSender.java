package antne.imagekeeper.telegrambot.bot.commands.communication;

import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

public class PhotoManagerSender {
    public static void send(AbsSender absSender, Long chatId, List<byte[]> bytePhotos) throws IOException, TelegramApiException {
        if (bytePhotos.size() == 0)
            MessageSender.sendMessage(absSender, chatId, CurrentLanguage.getCurrentLanguage().getImageNotExist());
        else if (bytePhotos.size() == 1) PhotoSender.sendPhoto(absSender, chatId.toString(), bytePhotos.get(0));
        else if (bytePhotos.size() < 11) MediaGroupSender
                .sendMediaGroup(absSender, chatId, bytePhotos);
        else throw new RuntimeException("Pageable must be between from 2 to 10");
    }
}

package antne.imagekeeper.telegrambot.bot.commands.communication;

import antne.imagekeeper.telegrambot.utils.ImageCreator;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

public class PhotoSender {

    public static void sendPhoto(AbsSender absSender, String chatId, byte[] bytePhoto) throws IOException, TelegramApiException {
        File photo = ImageCreator.createTemporaryImageFile(bytePhoto, "jpg");
        sendPhoto(absSender, chatId, photo);
    }

    public static void sendPhoto(AbsSender absSender, String chatId, File photo) throws TelegramApiException{
        SendPhoto sendPhoto = new SendPhoto(chatId, new InputFile(photo));
        absSender.execute(sendPhoto);
    }
}

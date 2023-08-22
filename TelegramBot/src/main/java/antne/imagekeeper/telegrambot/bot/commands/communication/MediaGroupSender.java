package antne.imagekeeper.telegrambot.bot.commands.communication;

import antne.imagekeeper.telegrambot.utils.ImageCreator;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaGroupSender {
    public static void sendMediaGroup(AbsSender absSender, long chatId, List<byte[]> bytePhotos)
            throws IOException, TelegramApiException{
        List<InputMedia> inputMediaPhotos = new ArrayList<>();

        for (int i = 0; i < bytePhotos.size(); i++) {
            File photo = ImageCreator.createTemporaryImageFile(bytePhotos.get(i), "jpg");
            InputMediaPhoto inputMediaPhoto = new InputMediaPhoto();
            inputMediaPhoto.setMedia(photo, i + ".jpg");
            inputMediaPhotos.add(inputMediaPhoto);
        }
        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setChatId(chatId);
        sendMediaGroup.setMedias(inputMediaPhotos);
        absSender.execute(sendMediaGroup);

    }
}

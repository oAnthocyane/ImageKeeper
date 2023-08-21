package antne.imagekeeper.telegrambot.bot.commands.find;

import antne.imagekeeper.telegrambot.api.finder.FinderByKeyPhrase;
import antne.imagekeeper.telegrambot.bot.commands.communication.MediaGroupSender;
import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.bot.commands.communication.PhotoSender;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import antne.imagekeeper.telegrambot.utils.ImageCreator;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FindByKeyPhrase implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "findByKeyPhrase";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandFindByKeyPhrase();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        if (arguments.length == 0){
            String sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();
            try{
                MessageSender.sendMessage(absSender, message.getChatId(), sendText);
            }catch (TelegramApiException e){
                throw new RuntimeException(e);
            }
        }
        else {
            List<String> keyPhrases = Arrays.stream(arguments).toList();
            long id = message.getFrom().getId();
            FinderByKeyPhrase finder = new FinderByKeyPhrase(id);
            finder.find(keyPhrases);

            if(finder.isSuccessfullyResponse()){
                List<byte[]> bytePhotos = finder.getApiResponse().getData();

                if(bytePhotos.size() > 1 && bytePhotos.size() < 11)
                    try {
                        MediaGroupSender.sendMediaGroup(absSender, message, bytePhotos);
                    }catch (IOException | TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                else if(bytePhotos.size() == 1){
                    try {
                        PhotoSender.sendPhoto(absSender, message, bytePhotos.get(0));
                    }catch (IOException | TelegramApiException e){
                        throw new RuntimeException(e);
                    }

                }else {
                    throw new RuntimeException("Pageable more 10");
                }
            }
            else System.out.println(finder.getApiResponse().getHttpStatus());
        }
    }


}

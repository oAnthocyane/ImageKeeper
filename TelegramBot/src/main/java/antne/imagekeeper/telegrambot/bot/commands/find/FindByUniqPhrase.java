package antne.imagekeeper.telegrambot.bot.commands.find;

import antne.imagekeeper.telegrambot.api.finder.FinderByUniqPhrase;
import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.bot.commands.communication.PhotoSender;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import antne.imagekeeper.telegrambot.properties.Config;
import antne.imagekeeper.telegrambot.utils.ImageCreator;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

public class FindByUniqPhrase implements IBotCommand {


    @Override
    public String getCommandIdentifier() {
        return "findByUniqPhrase";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandFindByUniqPhrase();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        String sendText = null;
        boolean needSendMessage = true;
        if(arguments.length == 0) sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();
        else {
            long userId = message.getFrom().getId();
            String uniqPhrase = arguments[0];
            FinderByUniqPhrase finder = new FinderByUniqPhrase(uniqPhrase, userId);
            finder.find();
            if(finder.isSuccessfullyResponse()){
                needSendMessage = false;
                byte[] photo = finder.getApiResponse().getData();
                try {
                    PhotoSender.sendPhoto(absSender, message, photo);
                } catch (IOException | TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }else {
                sendText = CurrentLanguage.getCurrentLanguage().getImageNotExist();
            }

        }
        if (needSendMessage){
            try {
                MessageSender.sendMessage(absSender, message.getChatId(), sendText);
            }catch (TelegramApiException e){
                throw new RuntimeException(e);
            }
        }
    }
}

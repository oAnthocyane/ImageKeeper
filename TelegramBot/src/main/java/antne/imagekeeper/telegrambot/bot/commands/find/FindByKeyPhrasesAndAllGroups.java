package antne.imagekeeper.telegrambot.bot.commands.find;

import antne.imagekeeper.telegrambot.api.finder.FinderByKeyPhrasesAndAllGroups;
import antne.imagekeeper.telegrambot.api.finder.FinderPhotos;
import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.bot.commands.communication.PhotoManagerSender;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FindByKeyPhrasesAndAllGroup implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "findByKeyPhrasesAndAllGroups";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandFindByKeyPhrasesAndAllGroups();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        boolean needMessage = true;
        String sendText = null;
        if(arguments.length == 0) sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();
        else {
            long id = message.getFrom().getId();
            FinderPhotos finder = new FinderByKeyPhrasesAndAllGroups(id);
            List<String> keyPhrases = Arrays.stream(arguments).toList();
            finder.addRequestParams("keyPhrases", keyPhrases);
            finder.find();
            if(finder.isSuccessfullyResponse()){
                needMessage = false;
                List<byte[]> photos = finder.getApiResponse().getData();
                try {
                    PhotoManagerSender.send(absSender, message.getChatId(), photos);
                } catch (IOException | TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }else {
                sendText = CurrentLanguage.getCurrentLanguage().getImageNotExist();
            }
        }
        if(needMessage){
            try {
                MessageSender.sendMessage(absSender, message.getChatId(), sendText);
            }catch (TelegramApiException e){
                throw new RuntimeException(e);
            }
        }
    }
}

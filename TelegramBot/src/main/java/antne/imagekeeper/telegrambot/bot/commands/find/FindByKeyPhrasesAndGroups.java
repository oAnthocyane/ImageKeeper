package antne.imagekeeper.telegrambot.bot.commands.find;

import antne.imagekeeper.telegrambot.api.finder.finderByKeyPhraseAndGroups;
import antne.imagekeeper.telegrambot.api.finder.FinderPhotos;
import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.bot.commands.communication.PhotoManagerSender;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import antne.imagekeeper.telegrambot.utils.FlagParser;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FindByKeyPhrasesAndGroups implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "findByKeyPhrasesAndGroups";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandFindByKeyPhrasesAndGroups();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        boolean needMessage = true;
        String sendText = null;
        if(arguments.length < 2) sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();
        else {
            Map<String, List<String>> flagKeys = FlagParser.parse(arguments);
            if(!flagKeys.containsKey("-k") || !flagKeys.containsKey("-g"))
                sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();
            else {
                long id = message.getFrom().getId();

                List<String> keyPhrases = flagKeys.get("-k");
                List<String> groupNames = flagKeys.get("-g");

                FinderPhotos finder = new finderByKeyPhraseAndGroups(id);
                finder.addRequestParams("groupName", groupNames);
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
                }else sendText = CurrentLanguage.getCurrentLanguage().getImageNotExist();
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

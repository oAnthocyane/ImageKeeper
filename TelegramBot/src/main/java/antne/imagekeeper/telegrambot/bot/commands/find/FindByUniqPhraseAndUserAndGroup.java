package antne.imagekeeper.telegrambot.bot.commands.find;

import antne.imagekeeper.telegrambot.api.finder.FinderByUniqPhraseAndGroup;
import antne.imagekeeper.telegrambot.api.finder.FinderPhotos;
import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.bot.commands.communication.PhotoManagerSender;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

public class FindByUniqPhraseAndUserAndGroup implements IBotCommand {

    @Override
    public String getCommandIdentifier() {
        return "findByUniqPhraseAndGroups";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandFindByUniqPhraseAndGroups();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        String sendText = null;
        boolean needMessage = true;
        if (arguments.length < 2){
            sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();

        }else {
            long id = message.getFrom().getId();
            String uniqPhrase = arguments[0];
            List<String> groupNames = List.of(arguments).subList(1, arguments.length);
            FinderPhotos finder = new FinderByUniqPhraseAndGroup(id, uniqPhrase);
            finder.addRequestParams("groupName", groupNames);
            finder.find();
            if(finder.isSuccessfullyResponse()){
                needMessage = false;
                List<byte[]> bytePhotos = finder.getApiResponse().getData();
                try {
                    PhotoManagerSender.send(absSender, message.getChatId(), bytePhotos);
                }catch (TelegramApiException | IOException e){
                    throw new RuntimeException(e);
                }
            }else {
                sendText = CurrentLanguage.getCurrentLanguage().getCanNotAddImage();
            }
        }
        if(needMessage) {
            try {
                MessageSender.sendMessage(absSender, message.getChatId(), sendText);
            }catch (TelegramApiException e){
                throw new RuntimeException(e);
            }
        }
    }
}

package antne.imagekeeper.telegrambot.bot.commands.find;

import antne.imagekeeper.telegrambot.api.finder.FinderByUniqPhraseAndAllGroup;
import antne.imagekeeper.telegrambot.api.finder.FinderPhotos;
import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.bot.commands.communication.PhotoManagerSender;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

@Slf4j
public class FindByUniqPhraseAndUserAndAllGroups implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return "findByUniqPhraseAndAllGroups";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandFindByUniqPhraseAndAllGroups();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

        if(arguments.length < 1) {
            String sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();
            try {
                MessageSender.sendMessage(absSender, message.getChatId(), sendText);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        else {
            String uniqPhrase = arguments[0];
            Long id = message.getFrom().getId();
            FinderPhotos finder = new FinderByUniqPhraseAndAllGroup(id, uniqPhrase);
            finder.find();
            if(finder.isSuccessfullyResponse()){
                List<byte[]> bytePhotos = finder.getApiResponse().getData();
                try {
                    PhotoManagerSender.send(absSender, message.getChatId(), bytePhotos);
                }catch (TelegramApiException | IOException e){
                    throw new RuntimeException(e);
                }
            }else log.error("Error http-status: {}", finder.getApiResponse().getHttpStatus());
        }
    }
}

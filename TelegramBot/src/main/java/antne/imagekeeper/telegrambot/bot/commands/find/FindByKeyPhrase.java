package antne.imagekeeper.telegrambot.bot.commands.find;

import antne.imagekeeper.telegrambot.api.finder.FinderByKeyPhrase;
import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.bot.commands.communication.PhotoManagerSender;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
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
        if (arguments.length == 0) {
            String sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();
            try {
                MessageSender.sendMessage(absSender, message.getChatId(), sendText);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            List<String> keyPhrases = Arrays.stream(arguments).toList();
            Long id = message.getFrom().getId();
            FinderByKeyPhrase finder = new FinderByKeyPhrase(id);
            finder.addRequestParams("keysPhrase", keyPhrases);
            finder.find();

            if (finder.isSuccessfullyResponse()) {
                List<byte[]> bytePhotos = finder.getApiResponse().getData();

                try {
                    PhotoManagerSender.send(absSender, id, bytePhotos);
                } catch (TelegramApiException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


}

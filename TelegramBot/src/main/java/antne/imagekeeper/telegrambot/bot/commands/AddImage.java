package antne.imagekeeper.telegrambot.bot.commands;

import antne.imagekeeper.telegrambot.api.ImageAdder;
import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.utils.FlagParser;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import antne.imagekeeper.telegrambot.model.data.ImageInfoDTO;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddImage implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return CurrentLanguage.getCurrentLanguage().getImage();
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage()
                .getCommandAddGroup();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {

    }


    public void execute(AbsSender absSender, Message message, InputStream streamPhoto, String[] arguments) {

        String sendText;
        if (arguments.length < 1) sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();
        else {
            String uniqPhrase = arguments[0];
            Map<String, List<String>> parsedPhrase = FlagParser.parse(arguments);
            List<String> keysPhrase = parsedPhrase.containsKey("-k") ? parsedPhrase.get("-k") : new ArrayList<>();
            List<String> groupsName = parsedPhrase.containsKey("-g") ? parsedPhrase.get("-g") : new ArrayList<>();

            try {
                byte[] bytePhoto = streamPhoto.readAllBytes();
                ImageInfoDTO imageInfoDTO = new ImageInfoDTO(uniqPhrase, keysPhrase, message.getFrom().getId(),
                        groupsName, bytePhoto);

                ImageAdder imageAdder = new ImageAdder();
                imageAdder.sendImage(imageInfoDTO);
                if(imageAdder.isSuccessfullyResponse())
                    sendText = CurrentLanguage.getCurrentLanguage().getDone();
                else{
                    sendText = CurrentLanguage.getCurrentLanguage().getCanNotAddImage();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }
        try {
            MessageSender.sendMessage(absSender, message.getChatId(), sendText);
        }catch (TelegramApiException e){
            throw new RuntimeException(e);
        }
    }

}

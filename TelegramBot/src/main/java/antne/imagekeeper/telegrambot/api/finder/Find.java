package antne.imagekeeper.telegrambot.api.finder;

import antne.imagekeeper.telegrambot.bot.commands.communication.MessageSender;
import antne.imagekeeper.telegrambot.bot.commands.find.*;
import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Find implements IBotCommand {
    @Override
    public String getCommandIdentifier() {
        return   "find";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandFind();

    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        boolean needMessage = true;
        String sendText = null;
        if(arguments.length < 2){
            sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();
        }else {
            String commandFlag = arguments[0];
            Map<String, IBotCommand> findCommands = getFindCommands();
            if(!findCommands.containsKey(commandFlag))
                sendText = CurrentLanguage.getCurrentLanguage().getCommandNotExist();
            else {
                needMessage = false;

                String[] argumentsForCommand = Arrays.copyOfRange(arguments, 1, arguments.length);

                IBotCommand foundCommand = findCommands.get(commandFlag);
                foundCommand.processMessage(absSender, message, argumentsForCommand);
            }
        }
        if (needMessage){
            try {
                MessageSender.sendMessage(absSender, message.getChatId(), sendText);
            }catch (TelegramApiException e){
                throw new RuntimeException(e);
            }
        }
    }

    private Map<String, IBotCommand> getFindCommands(){
        Map<String, IBotCommand> findCommands = new HashMap<>();

        findCommands.put("-u", new FindByUniqPhrase());
        findCommands.put("-ug", new FindByUniqPhraseAndGroups());
        findCommands.put("-uag", new FindByUniqPhraseAndAllGroups());
        findCommands.put("-k", new FindByKeyPhrase());
        findCommands.put("-kg", new FindByKeyPhrasesAndGroups());
        findCommands.put("-kag", new FindByKeyPhrasesAndAllGroups());

        return findCommands;
    }
}

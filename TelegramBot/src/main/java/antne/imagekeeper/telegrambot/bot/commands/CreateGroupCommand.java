package antne.imagekeeper.telegrambot.bot.commands;

import antne.imagekeeper.telegrambot.localization.CurrentLanguage;
import antne.imagekeeper.telegrambot.model.Group;
import antne.imagekeeper.telegrambot.api.GroupAdder;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class CreateGroupCommand implements IBotCommand {


    @Override
    public String getCommandIdentifier() {
        return "create_group";
    }

    @Override
    public String getDescription() {
        return CurrentLanguage.getCurrentLanguage().getCommandCreateGroup();
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        String sendText;
        if(arguments.length < 2)
            sendText = CurrentLanguage.getCurrentLanguage().getNotAllArguments();
        else {
            String nameGroup = arguments[0];
            String passwordGroup = arguments[1];
            Group group = new Group();
            group.setName(nameGroup);
            group.setPassword(passwordGroup);
            Long userId = message.getFrom().getId();
            GroupAdder groupAdder = new GroupAdder(userId);
            groupAdder.sendGroup(group);
            if(groupAdder.isSuccessfullyResponse()) {
                sendText = CurrentLanguage.getCurrentLanguage().getGreatCreateGroup();
            }
            else sendText = CurrentLanguage.getCurrentLanguage().getGroupExist();
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(sendText);

        try {
            absSender.execute(sendMessage);
        }catch (TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
}

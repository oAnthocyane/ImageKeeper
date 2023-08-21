package antne.imagekeeper.telegrambot.bot;

import antne.imagekeeper.telegrambot.bot.commands.*;
import antne.imagekeeper.telegrambot.bot.commands.find.FindByUniqPhrase;
import antne.imagekeeper.telegrambot.properties.Config;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.InputStream;
import java.util.List;

public class TelegramBot extends TelegramLongPollingCommandBot {

    private static TelegramBot instance;
    private static TelegramBotsApi telegramBotsApi;

    {
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            registerBot();
            registerCommands();
        } catch (TelegramApiException e) {
            throw new RuntimeException("Telegram Bot initialization error: " + e.getMessage());
        }
    }

    private TelegramBot(){
        super();
    }
    public static TelegramBot getInstance(){
        if(instance == null) instance = new TelegramBot();
        return instance;
    }

    public void registerBot() {
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Telegram API initialization error: " + e.getMessage());
        }
    }

    private void registerCommands() {
        register(new StartCommand());
        register(new HelpCommand());
        register(new ChangeLanguageCommand());
        register(new CreateGroupCommand());
        register(new LeaveGroupCommand());
        register(new JoinGroupCommand());
        register(new GetGroupsCommand());
        register(new AddImage());
        register(new FindByUniqPhrase());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if(update.hasMessage() && update.getMessage().hasPhoto()
                && update.getMessage().getCaption() != null) {
            Message message = update.getMessage();
            List<PhotoSize> photos = message.getPhoto();
            PhotoSize photo = photos.get(photos.size() - 1);
            String photoId = photo.getFileId();
            GetFile getFile = new GetFile();
            getFile.setFileId(photoId);
            try {
                String filePath = execute(getFile).getFilePath();
                InputStream streamPhoto = downloadFileAsStream(filePath);
                AddImage addImage = new AddImage();
                addImage.execute(this, message, streamPhoto, message.getCaption().split(" "));
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }

        }
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = update.getMessage().getChatId().toString();
            String messageText = update.getMessage().getText();

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("You said: " + messageText);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public String getBotUsername() {
        return Config.getSettings().getBot().getAuth().getUsername();
    }

    @Override
    public String getBotToken() {
        return Config.getSettings().getBot().getAuth().getToken();
    }

}

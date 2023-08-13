package antne.imagekeeper.telegrambot.properties;


import antne.imagekeeper.telegrambot.properties.bot.Bot;
import antne.imagekeeper.telegrambot.properties.server.Server;
import lombok.Data;

@Data
public class Settings {
    private Bot bot;

    private Server server;


}

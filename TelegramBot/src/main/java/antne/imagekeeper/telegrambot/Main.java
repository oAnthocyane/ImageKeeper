package antne.imagekeeper.telegrambot;

import antne.imagekeeper.telegrambot.bot.TelegramBot;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBot bot = TelegramBot.getInstance();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }


    }
}
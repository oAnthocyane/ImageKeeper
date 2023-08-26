package antne.imagekeeper.telegrambot.properties.server;

import lombok.Data;


@Data
public class Resource {
    private String url;
    private API api;
    private Security security;
}

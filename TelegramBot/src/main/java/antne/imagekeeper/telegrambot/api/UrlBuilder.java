package antne.imagekeeper.telegrambot.api;

import antne.imagekeeper.telegrambot.properties.Config;
import org.springframework.web.util.UriComponentsBuilder;

public class UrlBuilder {

    private static final String urlResourceServer = Config.getSettings().getServer()
            .getResource().getUrl();
    public static String build(String url, Object... params){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        return urlResourceServer + builder.buildAndExpand(params).toUriString();
    }
}

package antne.imagekeeper.telegrambot.api.finder;

import antne.imagekeeper.telegrambot.api.ApiResponse;
import antne.imagekeeper.telegrambot.api.dataSender.DataGetSender;
import antne.imagekeeper.telegrambot.properties.Config;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class FinderByKeyPhrase extends DataGetSender<List<byte[]>> {
    public FinderByKeyPhrase(Object... params){
        super(params);
    }

    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer().getResource().
                getApi().getFindByKeyPhrase();
    }

    public void find(List<String> keyPhrases){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        for(String keyPhrase : keyPhrases){
            builder.queryParam("keysPhrase", keyPhrase);
        }
        url = builder.build().toUriString();
        System.out.println(url);
        ParameterizedTypeReference<ApiResponse<List<byte[]>>> responseType = new ParameterizedTypeReference<>() {};
        send(url, responseType);
    }
}

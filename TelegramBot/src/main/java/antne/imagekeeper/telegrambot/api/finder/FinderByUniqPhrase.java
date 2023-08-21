package antne.imagekeeper.telegrambot.api.finder;

import antne.imagekeeper.telegrambot.api.ApiResponse;
import antne.imagekeeper.telegrambot.api.dataSender.DataGetSender;
import antne.imagekeeper.telegrambot.bot.commands.find.FindByUniqPhrase;
import antne.imagekeeper.telegrambot.properties.Config;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;

public class FinderByUniqPhrase extends DataGetSender<byte[]> {

    public FinderByUniqPhrase(Object... params){
        super(params);
    }
    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer().getResource().getApi().getFindByUniqPhrase();
    }

    public void find(){
        ParameterizedTypeReference<ApiResponse<byte[]>> responseType = new ParameterizedTypeReference<>() {};
        send(url, responseType);
    }

}

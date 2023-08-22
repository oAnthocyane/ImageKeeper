package antne.imagekeeper.telegrambot.api.dataSender;

import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

abstract public class DataGetWithReqParamsSender<Returned> extends DataGetSender<Returned> {

    public DataGetWithReqParamsSender(Object... params){
        super(params);
    }
    public void addRequestParams(String nameParam, List<String> params){
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        for(String param : params) builder.queryParam(nameParam, param);
        url = builder.build().toUriString();
    }
}

package antne.imagekeeper.telegrambot.api.finder;

import antne.imagekeeper.telegrambot.api.ApiResponse;
import antne.imagekeeper.telegrambot.api.dataSender.DataGetWithReqParamsSender;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

abstract public class FinderPhotos extends DataGetWithReqParamsSender<List<byte[]>> {
    public FinderPhotos(Object... params) {
        super(params);
    }

    public void find() {
        ParameterizedTypeReference<ApiResponse<List<byte[]>>> responseType = new ParameterizedTypeReference<>() {
        };
        send(url, responseType);
    }
}

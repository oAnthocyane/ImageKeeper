package antne.imagekeeper.telegrambot.api;

import antne.imagekeeper.telegrambot.api.dataSender.DataPostSender;
import antne.imagekeeper.telegrambot.model.data.ImageInfoDTO;
import antne.imagekeeper.telegrambot.properties.Config;
import org.springframework.core.ParameterizedTypeReference;

public class ImageAdder extends DataPostSender<Long> {

    public void sendImage(ImageInfoDTO imageInfoDTO){
        ParameterizedTypeReference<ApiResponse<Long>> responseType = new ParameterizedTypeReference<>() {};
        send(imageInfoDTO, url, responseType);
    }
    @Override
    public String getCurrentUrl() {
        return Config.getSettings().getServer().getResource().getApi()
                .getAddImage();
    }
}

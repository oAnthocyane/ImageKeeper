package antne.imagekeeper.telegrambot.api.dataSender;


import antne.imagekeeper.telegrambot.api.ApiResponse;
import antne.imagekeeper.telegrambot.exceptions.ResponseError;
import antne.imagekeeper.telegrambot.model.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
abstract public class DataGetSender <Returned> extends DataSender<Returned>{

    public DataGetSender(Object... params){
        super(params);
    }

    protected void send(String url, ParameterizedTypeReference<ApiResponse<Returned>> responseType){
        log.info("Send GET-request to: {}", url);
        RestTemplate restTemplate = buildRestTemplate(CONST_TIMEOUT);
        try {
            responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, null, responseType);
            apiResponse = responseEntity.getBody();

        }catch (HttpClientErrorException serverError){
            HttpStatus httpStatus = serverError.getStatusCode();
            String messageError = serverError.getResponseBodyAsString();
            apiResponse.setHttpStatus(httpStatus);
            apiResponse.setMessage(messageError);
        }
    }



}

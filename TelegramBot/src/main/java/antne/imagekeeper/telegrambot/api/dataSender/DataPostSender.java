package antne.imagekeeper.telegrambot.api.dataSender;

import antne.imagekeeper.telegrambot.api.ApiResponse;
import antne.imagekeeper.telegrambot.exceptions.RestRequestFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
abstract public class DataPostSender<Returned> extends DataSender<Returned>{

    public DataPostSender(Object... params){
        super(params);
    }

    protected <Sent> void send(Sent data, String url, ParameterizedTypeReference<ApiResponse<Returned>> responseType) {
        send(data, url, responseType, CONST_TIMEOUT);
    }

    protected void send(String url, ParameterizedTypeReference<ApiResponse<Returned>>responseType){
        send(null, url, responseType, CONST_TIMEOUT);
    }


    protected <Sent> void send(Sent data, String url, ParameterizedTypeReference<ApiResponse<Returned>> responseType, int timeout) {
        log.info("Send POST-request to: {}", url);
        HttpEntity<Sent> request = buildHttpEntity(data);

        RestTemplate restTemplate = buildRestTemplate(timeout);

        try {
            responseEntity = restTemplate.exchange(
                    url, HttpMethod.POST, request, responseType
            );
            apiResponse = responseEntity.getBody();
        }catch (HttpClientErrorException serverError){
            HttpStatus httpStatus = serverError.getStatusCode();
            String errorMessage = serverError.getResponseBodyAsString();
            apiResponse.setHttpStatus(httpStatus);
            apiResponse.setMessage(errorMessage);
        }
        catch (ResourceAccessException e){
            throw new RestRequestFailedException("Timeout exceeded: " + e.getMessage(), e);
        }
    }

}

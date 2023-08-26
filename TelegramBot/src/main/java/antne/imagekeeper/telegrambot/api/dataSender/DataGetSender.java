package antne.imagekeeper.telegrambot.api.dataSender;


import antne.imagekeeper.telegrambot.api.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
abstract public class DataGetSender<Returned> extends DataSender<Returned> {

    public DataGetSender(Object... params) {
        super(params);
    }

    protected void send(String url, ParameterizedTypeReference<ApiResponse<Returned>> responseType) {
        log.info("Send GET-request to: {}", url);
        RestTemplate restTemplate = buildRestTemplate(CONST_TIMEOUT);
        try {
            responseEntity = restTemplate.exchange(
                    url, HttpMethod.GET, null, responseType);
            apiResponse = responseEntity.getBody();
            log.info("Successfully GET-request to: {}", url);
        } catch (HttpClientErrorException serverError) {
            HttpStatus httpStatus = serverError.getStatusCode();
            String errorMessage = serverError.getResponseBodyAsString();
            apiResponse.setHttpStatus(httpStatus);
            apiResponse.setMessage(errorMessage);
            log.error("Error POST-request to: {} with status: {} and error: {}", url, httpStatus, errorMessage);
        }
    }


}

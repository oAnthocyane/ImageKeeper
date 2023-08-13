package antne.imagekeeper.telegrambot.api.dataSender;

import antne.imagekeeper.telegrambot.api.ApiResponse;
import antne.imagekeeper.telegrambot.api.UrlBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.*;


abstract public class DataSender<Returned> {

    protected ApiResponse<Returned> apiResponse = new ApiResponse<>();

    protected ResponseEntity<ApiResponse<Returned>> responseEntity;
    protected final int CONST_TIMEOUT = 500;

    protected String url;

    public DataSender(Object... params){
        this.url = UrlBuilder.build(getCurrentUrl(), params);
    }

    abstract public String getCurrentUrl();

    protected RestTemplate buildRestTemplate(int timeout){
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(timeout);
        httpRequestFactory.setConnectTimeout(timeout);

        return new RestTemplate(httpRequestFactory);
    }

    protected <Sent> HttpEntity<Sent> buildHttpEntity(Sent data){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(data, headers);
    }


    public ApiResponse<Returned> getApiResponse(){
        return apiResponse;
    }


    public boolean isSuccessfullyResponse(){
        return apiResponse.getHttpStatus().is2xxSuccessful();
    }

}

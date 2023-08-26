package antne.imagekeeper.telegrambot.api.dataSender;

import antne.imagekeeper.telegrambot.api.ApiResponse;
import antne.imagekeeper.telegrambot.api.UrlBuilder;
import antne.imagekeeper.telegrambot.properties.Config;
import antne.imagekeeper.telegrambot.properties.server.Security;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.*;


abstract public class DataSender<Returned> {

    protected ApiResponse<Returned> apiResponse = new ApiResponse<>();

    protected ResponseEntity<ApiResponse<Returned>> responseEntity;
    protected final int CONST_TIMEOUT = 500;

    private final Security security = Config.getSettings().getServer().getResource().getSecurity();

    private final String username = security.getUsername();

    private final String password = security.getPassword();

    protected String url;

    public DataSender(Object... params){
        this.url = UrlBuilder.build(getCurrentUrl(), params);
    }

    abstract public String getCurrentUrl();

    protected RestTemplate buildRestTemplate(int timeout){
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(timeout);
        httpRequestFactory.setConnectTimeout(timeout);

        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));

        httpRequestFactory.setHttpClient(
                HttpClients.custom()
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .build());


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

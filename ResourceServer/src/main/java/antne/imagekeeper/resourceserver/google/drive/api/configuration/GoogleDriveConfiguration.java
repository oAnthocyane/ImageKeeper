package antne.imagekeeper.resourceserver.google.drive.api.configuration;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

@Configuration
@Slf4j
public class GoogleDriveConfiguration {

    @Value("${google.drive.application-name}")
    private String APPLICATION_NAME;

    @Value("${google.drive.credentials-path}")
    private String CREDENTIALS_PATH;

    @Bean
    public Drive driveService(){
        final InputStream SERVICE_ACCOUNT_JSON_PATH = getClass().getResourceAsStream(CREDENTIALS_PATH);
        try {
            GoogleCredentials credentials = ServiceAccountCredentials.fromStream(SERVICE_ACCOUNT_JSON_PATH)
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));

            return new Drive.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    new HttpCredentialsAdapter(credentials))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        }catch (IOException ioException){
          log.error("Not found CREDENTIALS_PATH in resource path", ioException);
          return null;
        }
    }

}

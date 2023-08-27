package antne.imagekeeper.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Resourse server application.
 */
@SpringBootApplication
public class ResourceServerApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */

    // TODO: Doing Spring Admin, git.properties fot info. Deploy project. 460 page - git. ROLE to actuator - 470 page.
    public static void main(String[] args) {
        SpringApplication.run(ResourceServerApplication.class, args);
    }

}

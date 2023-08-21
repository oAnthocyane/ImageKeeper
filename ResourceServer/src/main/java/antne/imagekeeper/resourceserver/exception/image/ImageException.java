package antne.imagekeeper.resourceserver.exception.image;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ImageException {
    private final String message;
    private final Throwable throwable;
    private final String urlImage;
    private final HttpStatus httpStatus;
}

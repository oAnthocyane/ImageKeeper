package antne.imagekeeper.resourceserver.exception.image;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ImageExceptionHandler {

    @ExceptionHandler(value = ImageIOException.class)
    public ResponseEntity<Object> handleImageIOException(ImageIOException imageIOException){
        ImageException imageException = new ImageException(
                imageIOException.getMessage(),
                imageIOException.getCause(),
                imageIOException.getUrlImage(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(imageException, imageException.getHttpStatus());
    }
}

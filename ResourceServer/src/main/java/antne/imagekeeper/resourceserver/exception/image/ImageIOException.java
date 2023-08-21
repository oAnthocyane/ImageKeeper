package antne.imagekeeper.resourceserver.exception.image;

import java.io.IOException;

public class ImageIOException extends RuntimeException {
    private final String urlImage;

    public ImageIOException(String message, String urlImage) {
        super(message);
        this.urlImage = urlImage;
    }

    public ImageIOException(String message, Throwable cause, String urlImage) {
        super(message, cause);
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }
}

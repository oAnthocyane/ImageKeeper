package antne.imagekeeper.telegrambot.exceptions;

public class RestRequestFailedException extends RuntimeException {
    public RestRequestFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

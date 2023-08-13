package antne.imagekeeper.resourceserver.exception;

import antne.imagekeeper.resourceserver.model.ModelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * The type Database exception.
 */
@Data
@AllArgsConstructor
public class DatabaseException {
    private final String message;
    private final Throwable throwable;
    private final ModelType type;
    private final HttpStatus httpStatus;
}

package antne.imagekeeper.resourceserver.exception.database;

import antne.imagekeeper.resourceserver.exception.object.ObjectExistException;
import antne.imagekeeper.resourceserver.exception.object.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Database exception handler.
 */
@ControllerAdvice
public class DatabaseExceptionHandler {
    /**
     * Handle database not found exception response entity.
     *
     * @param objectNotFoundException the object not found exception
     * @return the response entity
     */
    @ExceptionHandler(value = ObjectNotFoundException.class)
    public ResponseEntity<Object> handleDatabaseNotFoundException(ObjectNotFoundException objectNotFoundException){
        DatabaseException databaseException = new DatabaseException(
                objectNotFoundException.getMessage(),
                objectNotFoundException.getCause(),
                objectNotFoundException.getType(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(databaseException, databaseException.getHttpStatus());
    }

    /**
     * Handle user exist exception response entity.
     *
     * @param objectExistException the object exist exception
     * @return the response entity
     */
    @ExceptionHandler(value = ObjectExistException.class)
    public ResponseEntity<Object> handleUserExistException(ObjectExistException objectExistException){
        DatabaseException databaseException = new DatabaseException(
                objectExistException.getMessage(),
                objectExistException.getCause(),
                objectExistException.getType(),
                HttpStatus.CONFLICT
        );
        return new ResponseEntity<>(databaseException, databaseException.getHttpStatus());
    }
}

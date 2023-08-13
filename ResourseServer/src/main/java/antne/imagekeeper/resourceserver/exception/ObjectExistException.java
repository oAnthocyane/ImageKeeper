package antne.imagekeeper.resourceserver.exception;

import antne.imagekeeper.resourceserver.model.ModelType;

/**
 * The type Object exist exception.
 */
public class ObjectExistException extends RuntimeException{

    private final ModelType type;

    /**
     * Instantiates a new Object exist exception.
     *
     * @param message the message
     * @param type    the type
     */
    public ObjectExistException(String message, ModelType type){
        super(message);
        this.type = type;
    }

    /**
     * Instantiates a new Object exist exception.
     *
     * @param message the message
     * @param cause   the cause
     * @param type    the type
     */
    public ObjectExistException(String message, Throwable cause, ModelType type){
        super(message, cause);
        this.type = type;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public ModelType getType() {
        return type;
    }
}

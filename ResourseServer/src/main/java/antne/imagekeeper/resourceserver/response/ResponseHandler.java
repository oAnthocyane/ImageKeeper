package antne.imagekeeper.resourceserver.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Response handler.
 */
public class ResponseHandler {
    /**
     * Response builder response entity.
     *
     * @param message      the message
     * @param httpStatus   the http status
     * @param responseData the response data
     * @return the response entity
     */
    public static ResponseEntity<Object> responseBuilder(String message, HttpStatus httpStatus, Object responseData){
        Map<String, Object> response = new HashMap<>();
        response.put("data", responseData);
        response.put("message", message);
        response.put("httpStatus", httpStatus);

        return new ResponseEntity<>(response, httpStatus);
    }
}

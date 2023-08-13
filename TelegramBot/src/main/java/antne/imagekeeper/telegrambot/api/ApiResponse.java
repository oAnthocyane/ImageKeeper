package antne.imagekeeper.telegrambot.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ApiResponse<T>{
    private T data;
    private String message;
    private HttpStatus httpStatus;

    @JsonProperty("data")
    public void setData(T data) {
        this.data = data;
    }
}

package spring_boot_security.exceptionHandler;

import lombok.Data;

@Data
public class UserErrorResponse {

    private String message;
    private long timeStamp;

    public UserErrorResponse(String message, long timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }
}

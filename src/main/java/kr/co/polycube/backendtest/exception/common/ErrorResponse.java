package kr.co.polycube.backendtest.exception.common;

import kr.co.polycube.backendtest.exception.message.DefaultErrorMessage;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String reason;

    private ErrorResponse(DefaultErrorMessage errorMessage) {
        this.reason = errorMessage.getMessage();
    }

    public static ErrorResponse from(DefaultErrorMessage errorMessage) {
        return new ErrorResponse(errorMessage);
    }
}

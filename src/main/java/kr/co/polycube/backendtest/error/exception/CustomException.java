package kr.co.polycube.backendtest.error.exception;

import kr.co.polycube.backendtest.error.message.DefaultErrorMessage;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final DefaultErrorMessage errorMessage;

    public CustomException(DefaultErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

}

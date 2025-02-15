package kr.co.polycube.backendtest.error.exception;

import kr.co.polycube.backendtest.error.message.DefaultErrorMessage;
import lombok.Getter;

@Getter
public class DataNotFoundException extends CustomException{
    private final Long causeId;

    public DataNotFoundException(DefaultErrorMessage errorMessage, Long id) {
        super(errorMessage);
        causeId = id;
    }

    public String getRequestParam() {
        return "id: " + causeId;
    }
}

package kr.co.polycube.backendtest.exception.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DefaultErrorMessage {
    DEFAULT_MESSAGE("서버 에러입니다. 자세한 사항은 문의 부탁드립니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    REQUEST_URL_NOT_FOUND("요청하신 URL을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    ;


    private final String message;
    private final HttpStatus httpStatusCode;

    DefaultErrorMessage(String message, HttpStatus httpStatusCode) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}

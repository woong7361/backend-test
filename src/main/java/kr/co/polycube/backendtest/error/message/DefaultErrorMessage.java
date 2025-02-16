package kr.co.polycube.backendtest.error.message;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum DefaultErrorMessage {
    DEFAULT_MESSAGE("서버 에러입니다. 자세한 사항은 문의 부탁드립니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    REQUEST_URL_NOT_FOUND("요청하신 URL을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_URL("올바르지 못한 URL입니다.", HttpStatus.BAD_REQUEST),

    USER_NOT_FOUND("요청하신 회원을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),


    LOTTO_ISSUE_ERROR("로또 발급이 잘못되었습니다.", HttpStatus.BAD_REQUEST),

    ;


    private final String message;
    private final HttpStatus httpStatusCode;

    DefaultErrorMessage(String message, HttpStatus httpStatusCode) {
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}

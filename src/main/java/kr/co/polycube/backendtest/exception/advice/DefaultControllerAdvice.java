package kr.co.polycube.backendtest.exception.advice;

import kr.co.polycube.backendtest.exception.common.CustomException;
import kr.co.polycube.backendtest.exception.common.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static kr.co.polycube.backendtest.exception.message.DefaultErrorMessage.*;

@RestControllerAdvice
@Slf4j
public class DefaultControllerAdvice {

    /**
     * 사용자 지정 에러 발생
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionAdvice(CustomException exception) {
        log.debug("custom exception thrown : {}", exception.getMessage());

        return ResponseEntity
                .status(exception.getErrorMessage().getHttpStatusCode())
                .body(ErrorResponse.of(exception.getErrorMessage()));
    }

    /**
     * 존재하지 않는 URL로 요청이 들어왔을때 404 에러 발생
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> noResourceFoundExceptionAdvice(NoResourceFoundException exception) {
        log.debug("invalid request message: {}, uri: {}, method: {}",
                exception.getMessage(), exception.getResourcePath(), exception.getHttpMethod());

        return ResponseEntity
                .status(REQUEST_URL_NOT_FOUND.getHttpStatusCode())
                .body(ErrorResponse.of(REQUEST_URL_NOT_FOUND));
    }


    /**
     *  정해둔 exception 외의 에러가 발생시 500 에러 발생
     */
//    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> defaultExceptionAdvice(Exception exception) {
        log.info("un expected error please check server: {}", exception.getMessage());
        log.debug("{}", exception);

        return ResponseEntity
                .status(DEFAULT_MESSAGE.getHttpStatusCode())
                .body(ErrorResponse.of(DEFAULT_MESSAGE));
    }
}

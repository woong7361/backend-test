package kr.co.polycube.backendtest.error.advice;

import kr.co.polycube.backendtest.error.exception.CustomException;
import kr.co.polycube.backendtest.error.dto.ErrorResponse;
import kr.co.polycube.backendtest.error.exception.DataNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static kr.co.polycube.backendtest.error.message.DefaultErrorMessage.*;

@RestControllerAdvice
@Slf4j
public class DefaultControllerAdvice {

    /**
     *  클라이언트가 요청한 데이터가 존재하지 않을때 400 에러 발생
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> customExceptionAdvice(DataNotFoundException exception) {
        log.debug("error message : {}, parameter {}", exception.getMessage(), exception.getRequestParam());

        return ResponseEntity
                .status(exception.getErrorMessage().getHttpStatusCode())
                .body(ErrorResponse.from(exception.getErrorMessage()));
    }

    /**
     * 존재하지 않는 URL로 요청이 들어왔을때 404 에러 발생
     */
    @ExceptionHandler(value = {NoResourceFoundException.class})
    public ResponseEntity<ErrorResponse> noResourceFoundExceptionAdvice(NoResourceFoundException exception) {
        log.debug("invalid request message: {}, uri: {}, method: {}",
                exception.getMessage(), exception.getResourcePath(), exception.getHttpMethod());

        return ResponseEntity
                .status(REQUEST_URL_NOT_FOUND.getHttpStatusCode())
                .body(ErrorResponse.from(REQUEST_URL_NOT_FOUND));
    }

    /**
     * 존재하지만 다른 HTTP method로 접근이 들어왔을때 404 에러 발생
     */
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorResponse> noResourceFoundExceptionAdvice(HttpRequestMethodNotSupportedException exception) {
        log.debug("invalid request message: {}, uri: {}, method: {}",
                exception.getMessage(), exception.getHeaders().getLocation(), exception.getMethod());

        return ResponseEntity
                .status(REQUEST_URL_NOT_FOUND.getHttpStatusCode())
                .body(ErrorResponse.from(REQUEST_URL_NOT_FOUND));
    }


    /**
     *  정해둔 exception 외의 에러가 발생시 500 에러 발생
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> defaultExceptionAdvice(Exception exception) {
        log.info("un expected error please check server: {}", exception.getMessage());
        log.debug("{}", exception);

        return ResponseEntity
                .status(DEFAULT_MESSAGE.getHttpStatusCode())
                .body(ErrorResponse.from(DEFAULT_MESSAGE));
    }
}

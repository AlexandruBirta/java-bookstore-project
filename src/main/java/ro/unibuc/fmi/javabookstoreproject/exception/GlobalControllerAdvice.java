package ro.unibuc.fmi.javabookstoreproject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.ConnectException;
import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<ApiError> handleApiException(ApiException e) {

        log.error(e.getErrorMessage());

        return new ResponseEntity<>(
                ApiError.builder()
                        .errorMessage(e.getErrorMessage())
                        .httpStatus(e.getExceptionStatus().getHttpStatus())
                        .timestamp(LocalDateTime.now())
                        .build(),
                e.getExceptionStatus().getHttpStatus()
        );
    }

    @ExceptionHandler(value = {ConnectException.class})
    public ResponseEntity<ApiError> handleConnectException(ConnectException e) {

        log.error(e.getMessage() + " HTTP Status " + HttpStatus.SERVICE_UNAVAILABLE);

        return new ResponseEntity<>(
                ApiError.builder()
                        .errorMessage(e.getMessage())
                        .httpStatus(HttpStatus.SERVICE_UNAVAILABLE)
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {

        log.error(e.getMessage() + " HTTP Status " + HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(
                ApiError.builder()
                        .errorMessage(e.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<ApiError> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {

        log.error(e.getMessage() + " HTTP Status " + HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(
                ApiError.builder()
                        .errorMessage(e.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        log.error(e.getMessage() + " HTTP Status " + HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(
                ApiError.builder()
                        .errorMessage(e.getMessage())
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
}


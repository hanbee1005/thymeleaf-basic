package hello.thymeleaf.exception.exhandler.advice;

import hello.thymeleaf.exception.exception.UserException;
import hello.thymeleaf.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExceptionHandler(IllegalArgumentException exception) {
        log.error("[exceptionHandler] ex", exception);
        return new ErrorResult("BAD", exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> UserExceptionHandler(UserException exception) {
        log.error("[exceptionHandler] ex", exception);
        return new ResponseEntity<>(new ErrorResult("USER-EX", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exceptionHandler(Exception exception) {
        log.error("[exceptionHandler] ex", exception);
        return new ErrorResult("EX", "내부 오류");
    }
}

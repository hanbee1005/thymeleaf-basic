package hello.thymeleaf.exception.api;

import hello.thymeleaf.exception.exception.UserException;
import hello.thymeleaf.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/exception")
public class ApiExceptionV2Controller {

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

    @GetMapping("/api2/members/{id}")
    public ApiExceptionController.MemberDto getMember(@PathVariable String id) {
        if("ex".equals(id)) {
            throw new RuntimeException("잘못된 사용자");
        }

        if("bad".equals(id)) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if("user-ex".equals(id)) {
            throw new UserException("사용자 오류");
        }

        return new ApiExceptionController.MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}

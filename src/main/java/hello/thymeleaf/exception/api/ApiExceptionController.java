package hello.thymeleaf.exception.api;

import hello.thymeleaf.exception.exception.BadRequestException;
import hello.thymeleaf.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/exception")
public class ApiExceptionController {
    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable String id) {
        if("ex".equals(id)) {
            throw new RuntimeException("잘못된 사용자");
        }

        if("bad".equals(id)) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if("user-ex".equals(id)) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }

    @GetMapping("api/response-status-ex-1")
    public String responseStatusEx1() {
        throw new BadRequestException();
    }

    @GetMapping("api/response-status-ex-2")
    public String responseStatusEx2() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}

package hello.thymeleaf.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}

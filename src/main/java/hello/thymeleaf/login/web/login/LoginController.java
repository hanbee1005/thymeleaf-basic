package hello.thymeleaf.login.web.login;

import hello.thymeleaf.login.domain.login.LoginService;
import hello.thymeleaf.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "/login/login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/login/login/loginForm";
        }

        try {
            Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
            // TODO 로그인 성공 처리

            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/login/login/loginForm";
        }
    }
}

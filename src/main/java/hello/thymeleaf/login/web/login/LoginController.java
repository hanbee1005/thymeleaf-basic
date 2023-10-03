package hello.thymeleaf.login.web.login;

import hello.thymeleaf.login.domain.login.LoginService;
import hello.thymeleaf.login.domain.member.Member;
import hello.thymeleaf.login.web.SessionConst;
import hello.thymeleaf.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "/login/login/loginForm";
    }

//    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "/login/login/loginForm";
        }

        try {
            Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

            // 로그인 성공 처리
            // 쿠키에 시간 정보를 주지 않으면 세션 쿠기 (브라우저 종료 시 모두 종료)
            Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
            response.addCookie(idCookie);

            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/login/login/loginForm";
        }
    }

//    @PostMapping("/login")
    public String loginV2(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "/login/login/loginForm";
        }

        try {
            Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

            // 로그인 성공 처리
            // 세션 관리자를 통해 세션을 생성하고 회원 데이터를 보관
            sessionManager.createSession(loginMember, response);

            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/login/login/loginForm";
        }
    }

    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "/login/login/loginForm";
        }

        try {
            Member loginMember = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

            // 로그인 성공 처리
            // 세션이 있으면 있는 세션을 반환, 없으면 신규 세션을 생성
            HttpSession session = request.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
            session.setMaxInactiveInterval(60); // 특정 세션 타임아웃 설정 (이걸 안하면 글로벌 설정이 먹힘)

            return "redirect:/home";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/login/login/loginForm";
        }
    }

//    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/home";
    }

//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expireSession(request);
        return "redirect:/home";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        // 있는 세션을 반환, 없으면 null
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/home";
    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

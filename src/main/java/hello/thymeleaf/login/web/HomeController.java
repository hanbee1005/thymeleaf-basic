package hello.thymeleaf.login.web;

import hello.thymeleaf.login.domain.member.Member;
import hello.thymeleaf.login.domain.member.MemberRepository;
import hello.thymeleaf.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/home")
    public String home() {
        return "login/home";
    }

//    @GetMapping("/home")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
        if (memberId == null) {
            return "login/home";
        }

        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) {
            return "login/home";
        }

        model.addAttribute("member", loginMember);
        return "login/loginHome";
    }

//    @GetMapping("/home")
    public String homeLoginV2(HttpServletRequest request, Model model) {
        // 세션 관리자에 저장된 회원 정보를 조회
        Member loginMember = (Member) sessionManager.getSession(request);

        if (loginMember == null) {
            return "login/home";
        }

        model.addAttribute("member", loginMember);
        return "login/loginHome";
    }

    @GetMapping("/home")
    public String homeLoginV3(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "login/home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        if (loginMember == null) {
            return "login/home";
        }

        model.addAttribute("member", loginMember);
        return "login/loginHome";
    }
}
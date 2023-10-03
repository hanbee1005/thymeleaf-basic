package hello.thymeleaf.login.web;

import hello.thymeleaf.login.domain.member.Member;
import hello.thymeleaf.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;

//    @GetMapping("/home")
    public String home() {
        return "login/home";
    }

    @GetMapping("/home")
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
}
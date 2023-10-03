package hello.thymeleaf.login.web;

import hello.thymeleaf.login.domain.item.LoginItem;
import hello.thymeleaf.login.domain.item.LoginItemRepository;
import hello.thymeleaf.login.domain.member.Member;
import hello.thymeleaf.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInitForLogin {

    private final LoginItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new LoginItem("itemA", 10000, 10));
        itemRepository.save(new LoginItem("itemB", 20000, 20));

        Member member = new Member();
        member.setLoginId("test");
        member.setPassword("test!");
        member.setName("테스터");
        memberRepository.save(member);
    }

}
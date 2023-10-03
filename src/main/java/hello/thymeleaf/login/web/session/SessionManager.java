package hello.thymeleaf.login.web.session;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private final Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     * sessionId 생성
     * 세션 저장소에 sessionId와 보관할 값 저장
     * sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
     */
    public void createSession(Object value, HttpServletResponse response) {
        String uuid = UUID.randomUUID().toString();
        sessionStore.put(uuid, value);
        response.addCookie(new Cookie(SESSION_COOKIE_NAME, uuid));
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request) {
        return findCookie(request, SESSION_COOKIE_NAME)
                .map(c -> sessionStore.get(c.getValue()))
                .orElse(null);
    }

    /**
     * 세션 만료
     */
    public void expireSession(HttpServletRequest request) {
        findCookie(request, SESSION_COOKIE_NAME)
                .ifPresent(c -> sessionStore.remove(c.getValue()));
    }

    private Optional<Cookie> findCookie(HttpServletRequest request, String cookieName) {
        if (ObjectUtils.isEmpty(request.getCookies())) {
            return Optional.empty();
        }

        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(cookieName))
                .findAny();
    }
}

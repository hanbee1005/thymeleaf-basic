# Thymeleaf
- [공식 사이트](https://www.thymeleaf.org/)
- [공식 메뉴얼 - 기본 기능](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- [공식 메뉴얼 - 스프링 통합](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html)

### 타임리프 특징
- 서버 사이드 HTML 렌터링 (SSR)
  - 백엔드 서버에서 HTML을 동적으로 렌더링
- 네츄럴 템플릿
  - 순수 HTML을 유지하면서 뷰 템플릿도 사용 가능
- 스프링 통합 지원

### 타임리프 기본 기능
- 사용 선언
    ```html
    <html xmlns:th="http://www.thymeleaf.org">
  ```
- 기본 표현식
    ```
    - 간단한 표현:
        - 변수 표현식: ${...}
        - 선택 변수 표현식: *{...}
        - 메시지 표현식: #{...}
        - 링크 URL 표현식: @{...}
        - 조각 표현식: ~{...}
    - 리터럴
        - 텍스트: 'one text', 'Another one!', ...
        - 숫자: 0, 34, 3.0, 12.3, ...
        - 불린: true, false
        - 널: null
        - 리터럴 토큰: one, sometext, main, ...
    - 문자 연산:
        - 문자 합치기: +
        - 리터럴 대체: |The name is ${name}|
    - 산술 연산:
        - Binary operators: +, -, *, /, %
        - Minus sign (unary operator): -
    - 불린 연산:
        - Binary operators: and, or
        - Boolean negation (unary operator): !, not
    - 비교와 동등:
        - 비교: >, <, >=, <=, (gt, lt, ge, le)
        - 동등 연산: ==, != (eq, ne)
    - 조건 연산:
        - If-then: (if) ? (then)
        - If-then-else: (if) ? (then) : (else)
        - Default: (value) ?: (default value)
    - 특별한 토큰:
        - No-Operation: _
  ```
  
### 텍스트 - text, utext
HTML의 콘텐츠(content)에 데이터를 출력하고 싶을 때
```html
<span th:text="${data}">기본 텍스트</span>
```
HTML의 태그 속성이 아니라 HTML 콘텐츠 영역 안에서 데이터를 직접 출력하고 싶다면
```html
컨텐츠 안에서 직접 출력하기 = [[${data}]]
```
- Escape
  - HTML 문서는 ```< , >``` 같은 특수 문자를 기반으로 정의한다. 따라서 뷰 템플릿으로 HTML 화면을 생성할 떄는 출력하는 데이터에 이러한 특수 문자가 있는 것을 주의해야 한다.
  - 타임리프가 제공하는 ```th:text```, ```[[...]]``` 는 기본적으로 이스케이프를 제공한다.
  - 언이스케이프하려면 ```th:utext```, ```[()]``` 로 사용하면 된다.
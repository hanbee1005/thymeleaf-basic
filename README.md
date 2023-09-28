# Thymeleaf
- [공식 사이트](https://www.thymeleaf.org/)
- [공식 메뉴얼 - 기본 기능](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- [공식 메뉴얼 - 스프링 통합](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html)

<details><summary>기본 기능</summary>
<p>

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

### 변수 - SpringEL
타임리프에서 변수를 사용할 때는 변수 표현식을 사용
- 변수 표현식: ```${...}```
- 그리고 이 변수 표현식에는 SpringEL이라는 스프링이 제공하는 표현식을 사용할 수 있다.
#### Object
- ```user.username```: user의 username을 프로퍼티 접근 -> ```user.getUsername()```과 같음
- ```user['username']```: 위와 같음
- ```user.getUsername()```: user의 getUsername()을 직접 호출
#### List
- ```users[0].username```: List에서 첫번째 회원을 찾고 username을 프로퍼티 접근 -> ```user[0].getUsername()```과 같음
- ```user[0]['username']```: 위와 같음
- ```user[0].getUsername()```: 리스트에서 첫번째 회원을 찾고 메서드를 직접 호출
#### Map
- ```userMap['userA'].username```: Map에서 userA를 찾고 username을 프로퍼티 접근 -> ```userMap['userA'].getUsername()```과 같음
- ```userMap['userA']['username']```: 위와 같음
- ```userMap['userA'].getUsername()```: Map에서 userA를 찾고 메서드를 직접 호출
#### 지역 변수
- ```th:with``` 사용

### 기본 객체들
- ```${#request}```
- ```${#response}```
- ```${#session}```
- ```${#servletContext}```
- ```${#locale}```
- 편의 객체
  - HTTP 요청 파라미터 접근 ```${param.xxx}```
  - HTTP 세션 접근 ```${session.xxx}```
  - 스프링 빈 접근 ```${@helloBean.hello('Spring!')}```

### 유틸리티 객체와 날짜
- ```#message``` : 메시지, 국제화 처리
- ```#uris``` : URI 이스케이프 지원
- ```#dates``` : ```java.util.Date``` 서식 지원
- ```#calendars``` : ```java.util.Calendar``` 서식 지원
- ```#temporals``` : 자바 8 날짜 서식 지원
- ```#numbers``` : 숫자 서식 지원
- ```#strings``` : 문자 관련 편의 기능
- ```#objects``` : 객체 관련 기능 제공
- ```#bools``` : boolean 관련 기능 제공
- ```#arrays``` : 배열 관련 기능 제공
- ```#lists, #sets, #maps``` : 컬렉션 관련 기능 제공
- ```#ids``` : 아이디 처리 관련 기능 제공
#### 자바 8 날짜
타임리프에서 자바 8 날짜인 ```LocalDate, LocalDateTime, Instant``` 를 사용하려면 추가 라이브러리(```thymeleaf-extra-java8time```)가 필요하지만, 스프링부트 타임리프를 사용하면 자동으로 추가

### URL 링크
타임리프에서 URL을 생성할 때는 ```@{...}``` 문법을 사용

### 리터럴 (Literals)
- 타임리프에서 문자 리터럴은 항상 작은 따옴표로 감싸야함 (공백없이 이어지는 경우 생략 가능)

### 속성 값 설정
- 속성 설정
  - ```th:*``` 속성을 지정하면 타임리프는 기존 속성을 ```th:*``` 로 지정한 속성으로 대체한다. 기존 속성이 없다면 새로 만든다.
  - ```<input type="text" name="mock" th:name="userA" />``` -> 타임리프 랜더링 후 ```<input type="text" name="userA" />```
- 속성 추가
  - ```th:attrappend``` : 속성 값의 값에 값을 추가한다.
  - ```th:attrprepend``` : 속성 값의 뒤에 값을 추가한다.
  - ```th:classappend``` : class 속성에 자연스럽게 추가한다.
- checked 처리
  - 타임리프의 ```th:checked``` 는 값이 ```false``` 인 경우 ```cheched``` 속성 자체를 제거

### 자바스크립트 인라인
- 텍스트 랜더링
  - ```var username = [[${user.username}]];```
    - 인라인 사용 전 -> ```var username = userA;```
    - 인라인 사용 후 -> ```var username = "userA";```
- 자바스크립트 내추럴 템플릿
  - ```var username = /*[[${user.username}]]*/ "test username";```
    - 인라인 사용 전 -> ```var username = /*userA*/ "test username";```
    - 인라인 사용 후 -> ```var username = "userA";```
- 객체
  - JSON 으로 자동 변환
  - ```var user = [[${user}]];```
    - 인라인 사용 전 -> ```var user = BasicController.User(username=userA, age=10);```
    - 인라인 사용 후 -> ```var user = {"username": "userA", "age": 10};```

### 템플릿 조각
웹 페이지를 개발할 때는 공통 영역이 많이 있다. 예를 들어 상단 영역이나 하단 영역, 좌측 카테고리 등등 여러 페이지에서 함께 사용하는 영역들이 있다.
타임리프는 이런 공통 영역을 위해 템플릿 조각과 레이아웃 기능을 지원한다.
    </p>
</details>

<details><summary>스프링 통합 폼</summary>
<p>

### 입력 폼 처리
- ```th:object="${item}"``` : form에서 사용할 객체를 지정한다. 선택 변수식```*{...}```을 적용할 수 있다.
- ```th:field="*{itemName}"```
  - ```*{itemName} == ${item.itemName}```
  - id, name, value 속성을 모두 자동으로 만들어준다.

### 체크 박스
- 체크 박스를 체크하면 HTML Form에서 ```open = on```이라는 값을 넘기고 스프링에서 ```on```이라는 문자를 ```true``` 타입으로 변환한다.
- 체크 박스 자체를 선택하지 않으면 ```open```이라는 필드 자체가 서버로 전송되지 않는다.
- 꼼수로 hidden 필드를 적용하여 체크 박스 미 체크 시 SpringMVC가 ```_open``` 값을 확인하여 ```false```를 인식할 수 있다.
- ```th:field="*{open}"``` 을 사용하면 타임리프가 자동으로 hidden 필드를 추가해준다. 체크가 된 경우 ```checked``` 속성 또한 추가해준다.
- 멀티 체크 박스의 경우도 hidden 필드를 자동으로 생성하게 되어 아무 것도 선택하지 않았을 때 빈 배열이 전달된다.

### 라디오 버튼
- 라디오 버튼은 체크 박스와 비슷하지만 null 값을 허용하고 수정 시에도 한번 체크한 값을 뺄 수 없기 때문에 hidden 필드를 생성하지 않는다.
- 타임리프에서 스프링 EL 문법으로 직접 enum 에 접근할 수 있는데 ```$(T(hello.thymleaf.itemservice.domain.item.itemType).values())```
- 하지만 위와 같이 하는 경우 enum 의 패키지 변경이 html 에도 영향이 있으므로 추천하지는 않는다.

### 셀렉트 박스
- 셀렉트 박스도 라디오와 비슷하게 적용하여 개발할 수 있습니다.

</p>
</details>
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

<details><summary>메시지, 국제화</summary>
<p>

### 메시지
- 스프링 사용 시 MessageSource 라는 인터페이스를 지정해주어야 한다.
  ```java
  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("messages", "errors");
    messageSource.setDefaultEncoding("utf-8");
    return messageSource;
  }
  ```
  - ```basenames```: 설정 파일의 이름을 지정한다.
    - ```messages```로 지정하면 ```messages.properties``` 파일을 읽어서 사용한다.
    - 파일의 위치는 ```/resources/messages.properties``` 에 두면 된다.
  - ```defaultEncoding```: 인코딩 정보를 지정한다.
- 하지만 스프링 부트를 사용하면 자동으로 MessageSource 를 빈으로 등록한다.
- 스프링 부트 메시지 소스 설정
  ```yaml
  spring:
    messages:
      basename: messages, config.i18n.messages
  ``` 
  + 기본값은 ```spring.messages.basename=messages```
  + MessageSource 를 빈으로 등록하지 않고, 스프링 부트와 관련된 별도의 설정을 하지 않으면 messages 라는 이름으로 기본 등록된다.
  + 따라서 messages.properties, messages_ko.properties, messages_en.properties 파일만 등록하면 자동으로 인식된다.
- 애플리케이션에 적용하기
  + thymeleaf 에서 적용 시 다음과 같이 사용할 수 있음
    - ```<label th:text="#{page.items}"></label>```
    - ```<p th:text="#{hello.name(${item.itemName})}"></p>```

### 국제화
- message 설정에 맞춰 messages.properties, messages_en.properties 등과 같이 파일을 만들어 사용할 수 있다.
- 기본 Locale 설정은 웹 브라우저의 Accept-Language 헤더 값을 사용한다.
- Locale 선택 방식을 변경하고 싶다면 ```LocaleResolver``` 인터페이스 구현체를 변경하면 된다.
- 기본적으로 Accept-Language 는 ```AcceptHeaderLocaleResolver``` 를 사용한다.
</p>
</details>

<details><summary>검증</summary>
<p>

### 검증 v1
- 일반적인 상품 저장 로직은 PRG(Post - Redirect - Get) 방식으로 동작하지만
- 검증이 맞지 않을 때 모델에 검증 데이터를 담아서 다시 폼을 보여줄 수 있도록 개발되어야 한다.
- 참고 **Safe Navigation Operation**
  + ```error?```는 error 가 null 일 때 NPE 가 발생하는 대신 null 로 처리하여 이후 로직이 실행되지 않도록 한다.
- 참고 **@ModelAttribute**
  + 자동으로 model 에 데이터를 추가하는데 기본적으로 type 이름으로 키가 잡히기 때문에 변경하려면
  + ```@ModelAttribute("name")```과 같이 이름을 지정해주면 된다.

### 검증 v2
- ```BindingResult```
  + 스프링이 제공하는 검증 요류를 보관하는 객체이다. 검증 오류가 발생하면 여기에 보관하면 된다.
  + ```BindingResult```가 있으면 ```@ModelAttribute```에 데이터 바인딩 시 오류가 발생해도 컨트롤러가 호출된다.
  + 예) ```@ModelAttribute```에 바인딩 시 타입 오류가 발생하면?
    - ```BindingResult```가 없으면 -> 400 오류가 발생하면서 컨트롤러가 호출되지 않고 오류 페이지로 이동
    - ```BindingResult```가 있으면 -> 오류 정보(```FieldError```)를 ```BindingResult```에 담아서 컨트롤러 정상 호출
  + ```BindingResult```에 검증 오류를 적용하는 3가지 방법
    - ```@ModelAttribute```의 객체에 타입 오류 등으로 바인딩이 실패하는 경우에 스프링이 ```FieldError``` 생성해서 ```BindingResult```에 넣어준다.
    - 개발자가 직접 넣어준다.
    - ```Validator``` 사용
  + 타입 오류 확인
    - 숫자가 입력되어야 할 곳에 문자를 입력해서 타입을 다르게 해서 ```BindingResult```를 호출하고 ```bindingResult```의 값을 확인해보자.
  + 주의
    - ```BindingResult```는 검증할 대상 바로 다음에 와야 한다. 순서가 중요하다. 예를 들어 ```@ModelAttribute Item item``` 바로 다음에 ```BindingResult```가 와야 한다.
    - ```BindingResult```는 Model 에 자동으로 포함된다.
  + ```BindingResult```와 ```Errors```
    - ```org.springframework.validation.Errors```
    - ```org.springframework.validation.BindingResult```
    - ```BindingResult```는 인터페이스이고 ```Errors``` 인터페이스를 상속받고 있다.
    - 실제 넘어오는 구현체는 ```BeanPropertyBindingResult```라는 것인데 둘다 구현하고 있으므로 ```BindingResult``` 대신에 ```Errors```를 사용해도 된다.
    - ```Errors``` 인터페이스는 단순한 오류 저장과 조회 기능을 제공한다.
    - ```BindingResult```는 여기에 더해서 추가적인 기능들을 제공한다. ```addError()```도 ```BindingResult```가 제공하므로 여기서는 ```BindingResult```를 사용하자. 주로 관례상 ```BindingResult```를 많이 사용한다.
- ```FieldError```, ```ObjectError```
  + ```FieldError```는 두가지 생성자를 제공한다.
    ```java
    public FieldError(String objectName, String field, String defaultMessage);
    public FieldError(String objectName, String field, @Nullable Object rejectedValue, boolean bindingFailure,
            @Nullable String[] codes, @Nullable Object[] arguments, @Nullable String defaultMessage);
    ```
    - 파라미터 목록
      + ```objectName```: 오류가 발생한 객체 이름
      + ```field```: 오류 필드
      + ```rejectValue```: 사용자가 입력한 값(거절된 값)
      + ```bindingFailure```: 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
      + ```codes```: 메시지 코드
      + ```arguments```: 메시지에 사용하는 인자
      + ```defaultMessage```: 기본 오류 메시지
    - 오류 발생 시 사용자 입력 값 유지
      + 사용자의 입력 데이터가 컨트롤러의 ```@ModelAttribute```에 바인딩 되는 시점에 오류가 발생하면 모델 객체에 사용자 입력 값을 유지하기 어렵다.
      + 예를 들어 가격에 숫자가 아닌 문자가 입력된다면 가격은 Integer 타입으로 문자를 보관할 수 있는 방법이 없다.
      + 그래서 오류가 발생한 경우 사용자 입력 값을 보관하는 별도의 방법이 필요하다. 그리고 이렇게 보관된 사용자 입력 값을 검증 오류 발생 시 다시 화면에 노출한다.
      + ```FieldError```는 오류 발생 시 사용자 입력 값을 저장하는 기능을 제공한다.
    - 타임리프의 사용자 입력 값 유지
      + ```th:field="*{price}"```
      + 타임리프의 ```th:field```는 정상 상황에는 모델의 값을 사용하지만, 오류가 발생하면 ```FieldError```에서 보관된 값을 사용해서 값을 출력한다.
    - 스프링 바인딩 오류 처리
      + 타입 오류로 바인딩에 실패하면 스프링은 ```FieldError```를 생성하면서 사용자가 입력한 값을 넣어둔다. 그리고 해당 오류를 ```BindingResult```에 담아서 컨트롤러를 호출한다.
      + 따라서 타입 오류 같은 바인딩 오류 시에도 사용자의 오류 메시지를 정상 출력할 수 있다.
  + ```ObjectError```

### 오류 코드와 메시지 처리
- ```FieldError```, ```ObjectError``` 의 생성자 활용
  + ```codes```: ```required.item.itemName```를 사용해서 메시지 코드를 지정한다. 메시지 코드는 하나가 아니라 배열로 여러 값을 전달할 수 있는데 순서대로 매칭해서 처음 매칭되는 메시지를 사용한다.
  + ```arguments```: ```new Object[]{1000, 1000000}```를 사용해서 코드의 ```{0}, {1}```로 치환할 값을 전달한다.
- ```BindingResult```의 ```rejectValue()```, ```reject()``` 활용
  + 컨트롤러에서 ```BindingResult```는 검증해야 할 객체인 target 바로 뒤에 온다. 따라서 ```BindingResult```는 이미 본인이 검증해야할 객체인 target을 알고 있다.
  + ```rejectValue()```, ```reject()```를 사용할 수 있다.
  + ```field```: 오류 필드명
  + ```errorCode```: 오류 코드(이 오류 코드는 메시지에 등록된 코드가 아니다. 뒤에서 설명할 MessageResolver를 위한 에러 코드다.)
  + ```errorArgs```: 오류 메시지에서 ```{0}```을 치환하기 위한 값
  + ```defaultMessage```: 오류 메시지를 찾을 수 없을 때 사용하는 기본 메시지
  + 에러 메시지 코드를 범용적으로 적어도 객체명과 필드명을 조합한 구체적인 메시지가 있는지 확인하고 없으면 범용적 메시지를 사용한다. -> 스프링은 ```MessageCodesResolver```라는 것으로 이러한 기능을 지원한다.
- ```MessageCodesResolver```
  + 검증 오류 코드로 메시지 코드들을 생성한다.
  + ```MessageCodesResolver```는 인터페이스고 ```DefaultMessageCodesResolver```는 기본 구현체이다.
  + 주로 ```ObjectError```, ```FieldError```와 함께 사용한다.
  + 기본 메시지 생성 규칙
    - 객체 오류
      + code + "." + object name
      + code
    - 필드 오류
      + code + "." + object name + "." + field name
      + code + "." + field name
      + code + "." + field type
      + code
  + 동작 방식
    - ```rejectValue()```, ```reject```는 내부에서 ```MessageCodesResolver```를 사용한다. 여기에서 메시지 코드를 생성한다.
    - ```FieldError```, ```ObjectError```의 생성자를 보면, 오류 코드를 하나가 아니라 여러 오류 코드를 가질 수 있다. ```MessageCodesResolver```를 통해서 생성된 순서대로 오류 코드를 보관한다.
- 
</p>
</details>
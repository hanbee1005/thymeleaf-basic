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
- ```ValidationUtils``` 사용
  + ```ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");```과 같이 사용할 수 있다.
- 스프링이 직접 작성한 메시지 코드
  + 스프링은 타입 오류가 발생하면 ```typeMismatch```라는 오류 코드를 사용한다.
- ```WebDataBinder``` 사용하기
  + 스프링의 파라미터 바인딩 역할을 해주고 검증 기능도 내부에 포함한다.
  + ```@InitBinder```로 ```WebDataBinder```에 검증기를 추가하면 해당 컨트롤러에서는 검증기를 자동으로 적용할 수 있다.
  + 그리고 검증할 대상 앞에 ```@Validated``` 를 추가한다.
  + 동작 방식
    - ```@Validated```는 검증기를 실행하라는 애노테이션이다.
    - 이 애노테이션이 붙으면 앞서 ```WebDataBinder```에 등록한 검증기를 찾아서 실행한다.
    - 여러 검증기를 등록하는 경우 ```supports()```를 통해 적절한 검증기를 찾아 실행하게 된다.
  + 글로벌 설정은 다음과 같이 진행
    ```java
    @Configuration
    public class validationConfig implements WebMvcConfig {
        @Override
        public Validator validator() {
            return new ItemValidator();
        }
    }
    ```
</p>
</details>

<details><summary>Bean Validation</summary>
<p>

### Bean Validation
- 특정 구현체가 아니라 Bean Validation 2.0 (JSR-380) 이라는 기술 표준이다. 쉽게 말해 검증 애노테이션과 여러 인터페이스의 모음이다.
- 마치 JPA가 표준 기술이고 그 구현체로 하이버네이트가 있는 것과 같다.
- 일반적으로 사용하는 구현체는 하이버네이트 validation이다. (ORM과 관련 있는 것은 아니다.)
- 하이버네이트 validation 관련 링크
  + 공식 사이트: https://hibernate.org/validator/
  + 공식 메뉴얼: https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/?v=8.0
  + 검증 애노테이션 모음: https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/?v=8.0#validator-defineconstraints-spec

### 검증 애노테이션 적용
- 의존성 추가 (build.gradle)
  ```java
  implementation 'org.springframework.boot:spring-boot-starter-validation'
  ```
- 객체에 애노테이션 추가
  + ```@NotBlank```: 빈값 + 공백만 있는 경우를 허용하지 않는다.
  + ```@NotNull```: null 을 허용하지 않는다.
  + ```@Range(min = 1000, max = 1000000)```: 범위 안의 값이어야 한다.
  + ```@Max(9999)```: 최대 9999까지만 허용한다.
- 참고
  + ```javax.validation.constraints.NotNull```
  + ```org.hibernate.validator.constraints.Range```
  + ```javax.validation```으로 시작하면 특정 구현에 관계 없이 제공되는 표준 인터페이스이고, ```org.hibernate.validator```이면 하이버네이트 validator 구현체를 사용할 때만 제공되는 검증 기능이다.

### 동작 방식
- 스프링부트가 ```spring-boot-starter-validation``` 라이브러리를 넣으면 자동으로 Bean Validator를 인지하고 스프링에 통합한다.
- 스프링부트는 자동으로 글로벌 Validator를 등록한다.
  + ```LocalValidatorFactoryBean```을 글로벌 Validator로 등록한다. 이 Validator는 ```@NotNull``` 같은 애노테이션을 보고 검증을 수행한다.
  + 이렇게 글로벌 Validator가 적용되어 있기 때문에, ```@Valid```, ```@Validated```만 적용하면 된다.
  + 검증 오류가 발생하면, ```FieldError```, ```ObjectError```를 생성해서 ```BindingResult```에 담아준다.
  + 주의!
    - 직접 글로벌 Validator를 등록하면 스프링부트는 Bean Validator를 글로벌 Validator로 등록하지 않아 애노테이션 기반의 빈 검증기가 동작하지 않는다.
- 참고
  + 검증 시 ```@Validated```, ```@Valid``` 둘다 사용 가능하다.
  + ```javax.validation.@Valid```를 사용하려면 build.gradle에 의존성 추가가 필요하다.
  + ```@Validated```는 스프링 전용 검증 애노테이션이고, ```@Valid```는 자바 표준 검증 애노테이션이다. 둘중 아무거나 사용해도 동일하게 작동하지만, ```@Validated```는 내부에 groups 라는 기능을 포함하고 있다. 
- 검증 순서
  + ```@ModelAttribute``` 각각의 필드에 타입 변환 시도
    - 성공하면 다음으로 
    - 실패하면 ```typeMismatch```로 ```FieldError``` 추가
  + validator 적용
    - 바인딩에 성공한 필드만 Bean Validator 적용

### 에러 코드
- Bean Validator를 적용하고 bindingResult에 등록된 검증 오류 코드를 확인하면 오류 코드가 애노테이션 이름으로 등록된다.
- 예를 들어 ```@NotBlank``` 는
  + NotBlank.item.itemName
  + NotBlank.itemName
  + NotBlank.java.lang.String
  + NotBlank
- 메시지 찾는 순서
  + 생성된 메시지 코드 순서대로 ```messageSource```에서 메시지 찾기
  + 애노테이션의 ```message```속성 사용 -> ```@NotBlank(message = "공백! {0}")```
  + 라이브러리가 제공하는 기본 값 사용 -> 공백일 수 없습니다.
- ```ObjectError```의 경우
  + ```@ScriptAssert()```를 사용하면 된다.
  + 하지만 실제 사용하기에는 제약이 많아 복잡하기 때문에 자바 코드로 작성하는 것을 권장

### Bean Validation- groups
- 동일한 모델 객체를 등록할 때와 수정할 때 다르게 검증하는 방법
  + Bean Validation의 groups 기능을 사용한다.
  + Item을 직접 사용하지 않고 ItemSaveForm, ItemUpdateForm과 같은 별도의 모델 객체를 만들어서 사용한다.
- groups 으로 사용될 인터페이스를 만들어야 함
- groups 기능을 사용하려면 ```@Validated```를 사용해야 함
- 복잡하기 때문에 잘 사용하지는 않음
- 또한 실무에서는 폼 객체를 분리해서 주로 사용함

### HTTP 메시지 컨버터
- ```@Valid```, ```@Validated```는 HttpMessageConverter(```@RequestBody```)에도 사용할 수 있다.
- 참고
  + ```@ModelAttribute```는 HTTP 요청 파라미터(URL 쿼리 스트링, POST Form)를 다룰 때 사용한다.
  + ```@RequestBody```는 HTTP Body의 데이터를 객체로 변환할 때 사용한다. 주로 API JSON 요청을 다룰 때 사용한다.
- API의 경우 3가지 경우를 나누어 생각해야 한다.
  + 성공 요청: 성공
  + 실패 요청: JSON을 객체로 생성하는 것 자체가 실패함
  + 검증 오류 요청: JSON을 객체로 생성하는 것은 성공했고 검증에서 실패
- 차이점 확인
  + ```@ModelAttribute```는 필드 단위로 정교하게 바인딩이 적용된다. 특정 필드가 바인딩 되지 않아도 나머지는 정상 바인딩이 되고 Validator를 사용한 검증도 적용할 수 있다.
  + ```@RequestBody```는 HttpMessageConverter 단계에서 JSON 데이터를 객체로 변경하지 못하면 이후 단계 자체가 진행되지 않고 예외가 발생한다. 컨트롤러도 호출되지 않고 Validator도 적용할 수 없다.
</p>
</details>

<details><summary>로그인 처리 1 - 쿠키, 세션</summary>
<p>

### 쿠키 (Cookie)
- 서버에서 로그인 성공 시 HTTP 응답에 쿠키를 담아서 전달하면 로그인 상태를 유지할 수 있다.
- 종류
  + 영속 쿠키: 만료 날짜를 입력하면 해당 날짜까지 유지
  + 세션 쿠키: 만료 날짜를 생략하면 브라우저 종료시까지만 유지
- 보안 문제
  + 쿠키 값은 임의로 변경할 수 있다.
    - 클라이언트가 쿠키를 강제로 변경하면 다른 사용자가 된다.
    - 실제 브라우저 개발자 모드 -> Application -> Cookie 변경으로 확인
  + 쿠키에 보관된 정보는 훔쳐갈 수 있다.
    - 만약 쿠키에 개인정보나 신용카드 정보가 있다면 이 정보가 웹브라우저에도 보관되고 네트워크 요청마다 클라이언트에서 서버로 전달된다.
    - 쿠키의 정보가 나의 로컬 PC에서 털릴 수도 있고 네트워크 구간에서 털릴 수도 있다.
  + 해커가 쿠키를 한번 훔쳐가면 평생 사용할 수 있다.
    - 해커가 쿠키를 훔쳐가서 그 쿠키로 악의적인 요청을 계속 시도할 수 있다.
- 대안
  + 쿠기에 중요한 값을 노출하지 않고 사용자 별로 예측 불가능한 임의의 토큰(랜덤 값)을 노출하고 서버에서 토큰과 사용자 id를 매핑해서 인식한다. 그리고 서버에서 토큰을 관리한다.
  + 토큰은 해커가 임의의 값을 넣어도 찾을 수 없도록 예측 불가능 해야 한다.
  + 해커가 토큰을 털어가도 시간이 지나면 사용할 수 없도록 서버에서 해당 토큰의 만료 시간을 짧게 유지한다. 또는 해킹이 의심되는 경우 서버에서 해당 토큰을 강제로 제거하면 된다.

### 세션 (Session)
- 서버에 중요한 정보를 저장하고 연결을 유지하는 방법
- 세션을 사용 시
  + 쿠키 값을 변조 가능 -> 예상 불가능한 복잡한 세션 id를 사용한다.
  + 쿠키에 보관하는 정보는 클라이언트 해킹 시 털릴 가능성이 있다. -> 세션 id가 털려도 여기에는 중요한 정보가 없다.
  + 쿠키 탈취 후 사용 -> 세션의 만료 시간을 짧게 유지하고 해킹이 의심되는 경우 강제로 제거하면 된다.
- HTTP Session
  + 서블릿을 통해 ```HttpSession```을 생성하면 다음과 같은 쿠키를 생성한다. 이름은 ```JSESSIONID```이고 값은 추정 불가능한 랜덤 값이다.
  + ```@SessionAttribute```를 사용할 수 있다.
    - 이미 로그인 된 사용자를 찾을 때 사용할 수 있고 이 애노테이션은 세션을 새로 생성하지 않는다.
  + TrackingModes
    - 로그인을 처음 시도하면 URL이 다음과 같이 JSESSIONID를 포함하고 있다.
    - ```http://localhost:8080/home;jsessionid=ED55A92FB5AD639D406E3F41BD82C574```
    - 이것은 웹 브라우저가 쿠키를 지원하지 않을 때 쿠키 대신 URL을 통해서 세션을 유지하는 방법이다. 이 방법을 사용하려면 URL에 이 값을 계속 포함해서 전달해야 한다.
    - 타임리프 같은 템플릿은 엔진을 통해서 링크를 걸면 ```jsessionid```를 자동으로 URL에 포함해준다.
    - 서버 입장에서 윕 브라우저가 쿠키를 지원하는지 안하는지 최초에는 판단하지 못하므로 쿠키 값도 전달하고 URL에 ```jsessionid```도 전달한다.
    - URL 전달 방식을 끄고 항상 쿠키를 통해서만 세션을 유지하고 싶으면 다음 옵션을 넣어주면 된다.
      + ```server.servlet.session.tracking-modes=cookie```
  + 세션 타임아웃 발생
    - 세션의 타임아웃 시간은 해당 세션과 관련된 ```jsessionid```를 전달하는 HTTP 요청이 있으면 현재 시간으로 다시 초기화 된다. 이렇게 초기화 되면 세션 타임아웃으로 설정한 시간동안 세션을 추가로 사용할 수 있다.
    - ```LastAccessedTime``` 이후로 timeout 시간이 지나면 WAS가 내부에서 해당 세션을 제거한다.
- 실무에서 주의할 점은 세션에는 최소한의 데이터만 보관해야 한다는 점이다. 보관한 데이터 용량 * 사용자 수로 세션의 메모리 사용량이 급격하게 늘어나서 장애로 이어질 수 있다.
- 추가로 세션 시간의 길이를 너무 길게 가져가면 메모리 사용이 계속 누적 될 수 있으므로 적당한 시간을 가져가는 것이 중요한다. 기본적으로 30분
</p>
</details>

<details><summary>로그인 처리 2 - 필터, 인터셉터</summary>
<p>

### 서블릿 필터
- 웹과 관련된 공통 관심 사항을 처리할 수 있다.
- 필터 흐름: HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 컨트롤러
- 필터를 적용하면 필터가 호출된 다음에 서블릿이 호출된다. 그래서 모든 고객의 요청 로그를 남기는 요구사항이 있다면 필터를 사용하면 된다.
- 참고로 필터는 특정 URL 패턴에 적용할 수 있다. ```/*```라고 하면 모든 요청에 필터가 적용된다.
- 적절하지 않은 요청이라고 판단되면 다음으로 넘기지 않고 끝낼 수 있다.
- 필터 체인
  + 필터는 체인으로 구성되는데 중간에 필터를 자유롭게 추가할 수 있다.
  + HTTP 요청 -> WAS -> 필터 1 -> 필터 2 -> 필터 3 -> 서블릿 -> 컨트롤러
- 필터 인터페이스
  + 필터 인터페이스를 구현하고 등록하면 서블릿 컨테이너가 필터를 싱글톤 객체로 생성하고 관리한다.
  + ```init()```: 필터 초기화 메서드, 서블릿 컨터이너가 생성될 때 호출된다.
  + ```doFilter()```: 고객의 요청이 올 때마다 해당 메서드가 호출된다. 필터에 로직을 구현하면 된다.
  + ```destroy()```: 필터 종료 메서드, 서블릿 컨테이너가 종료될 때 호출된다.
- 참고
  + 실무에서 HTTP 요청 시 같은 요청의 로그에 모두 같은 식별자를 자동으로 남기는 방법은 logback mdc로 검색해보자.
  + 필터는 ```chain.doFilter(request, response)```를 호출하여 다음 필터 또는 서블릿을 호출할 때 ```ServletRequest```, ```ServletResponse```를 구현한 완전히 다른 객체로 만들어서 전달할 수 있다.

### 스프링 인터셉터
- 웹과 관련된 공통 관심 사항을 처리할 수 있다.
- 스프링 인터셉터 흐름: HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 스프링 인터셉터 -> 컨트롤러
- 스프링 인터셉터는 디스패처 서블릿과 컨트롤러 사이에서 컨트롤러 호출 직전에 호출된다.
- 스프링 인터셉터는 스프링 MVC가 제공하는 기능이기 때문에 결국 디스패처 서블릿 이후에 등장하게 된다. 스프링 MVC의 시작점이 디스패처 서블릿이라고 생각해보면 이해가 될 것이다.
- 스프링 인터셉터에도 URL 패턴을 적용할 수 있는데 서블릿 URL 패턴과는 다르고, 매우 정밀하게 설정할 수 있다.
- 적절하지 않은 요청이라고 판단되면 다음으로 넘기지 않고 끝낼 수 있다.
- 인터셉터 체인
  + 인터셉터는 체인으로 구성되는데 중간에 필터를 자유롭게 추가할 수 있다.
  + HTTP 요청 -> WAS -> 필터 1 -> ... -> 필터 N -> 서블릿 -> 인터셉터 1 -> ... -> 인터셉터 N -> 컨트롤러
- 스프링 인터셉터 인터페이스
  + 인터셉터는 컨트롤러 호출 전(```preHandle```), 컨트롤러 호출 후(```postHandle```), 요청 완료 이후(```afterCompletion```)와 같이 단계적으로 세분화되어 있다.
    + ```preHandle```: 컨트롤러 호출 전에 호출 (더 정확히는 핸들러 어댑터 호출 전), 반환 값이 true 이면 다음을 진행하고 false 인 경우 다음 인터셉터, 핸들러 어댑터 모두 호출되지 않고 끝난다.
    + ```postHandle```: 컨트롤러 호출 후에 호출 (더 정확히는 핸들러 어댑처 호출 후), 컨트롤러에서 예외가 발생하면 호출되지 않는다.
    + ```afterCompletion```: 뷰가 랜더링된 이후에 호출, 항상 호출
  + 인터셉터는 어떤 컨트롤러(```handler```)가 호출되는지 호출 정보도 받을 수 있다.
  + 그리고 어떤 ```modelAndView```가 반환되는지 응답 정보도 받을 수 있다.
- 스프링 인터셉터 PathPattern 참고: https://docs.spring.io/spring-framework/docs/5.0.0.M4_to_5.0.0.M5/Spring%20Framework%205.0.0.M5/org/springframework/web/util/patterns/PathPattern.html
- 인터셉터의 장점
  + pre, post 로 관심사가 분리되어 원하는 부분만 구현하면 된다.
  + 인터셉터 등록 시 pathPattern을 보다 정교하게 설정할 수 있다.

### ArgumentResolver
- ```supportsParameter```: 원하는 파라미터가 맞는지 확인한다. ```@Login``` 애노테이션이 있으면서 ```Member```타입이면 ```ArgumentResolver```가 사용된다.
- ```resolveArgument```: 컨트롤러 호출 직전에 호출되어 필요한 파라미터 정보를 생성해준다.
</p>
</details>

<details><summary>예외 처리</summary>
<p>

### 서블릿 예외 처리
- 서블릿은 다음 2가지 방식으로 예외를 처리한다.
  + Exception(예외)
  + response.sendError(HTTP 상태 코드, 에러 메시지)
- Exception 이 발생한 경우 잡지 않고 WAS까지 전달이 되면
  + 톰캣은 없는 URL의 경우 404, 그외 서버에서 발생한 에러는 500으로 처리한다.
- sendError 를 사용하면 당장 에러를 발생시키는 것이 아니고 서블릿 컨테이너에게 오류가 발생했다는 것을 알릴 수 있다.
  + 서블릿 컨테이너는 고객에게 응답 전에 response의 sendError()가 호출되었는지 확인하고 설정한 오류 코드에 맞추어 기본 오류 페이지를 보여준다.

### 서블릿 예외 처리 - 필터
- DispatcherType
  + 서블릿 예외 처리를 위해 에러 페이지를 호출하게 되면 필터, 서블릿, 인터셉터 모두 다시 호출되기 때문에
  + 클라이언트의 요청인지 오류 페이지 출력을 위한 내부 요청인지 구분하기 위해 이 값을 제공한다.
  + ```REQUEST```: 클라이언트 요청
  + ```ERROR```: 오류 요청
  + ```FORWARD```: MVC에서 배웠던 서블릿에서 다른 서블릿이나 JSP를 호출할 때 ```RequestDispatcher.forward(request, response);```
  + ```INCLUDE```: 서블릿에서 다른 서블릿이나 JSP의 결과를 포함할 때 ```RequestDispatcher.include(request, response);```
  + ```ASYNC```: 서블릿 비동기 호출
- 필터는 서블릿 기술이기 때문에 기본적으로 REQUEST 일때 적용이 되고 잘 처리가 된다. (추가로 다른 타입을 설정할 수 있음)

### 서블릿 예외 처리 - 인터셉터
- excludePathPatterns 에 에러 페이지 경로를 추가하여 내부 호출에서는 인터셉터가 실행되지 않도록 처리할 수 있다.

### 전체 에러 흐름 정리
- 필터와 인터셉터 등록 시 설정을 통해 내부 호출 시에는 필터와 인터셉터를 실행하지 않도록 할 수 있다. (기본적으로 흐름에 맞게 실행됨)
```
1. WAS(/error-ex, dispatcherType=REQUEST) -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러
2. WAS(여기까지 전파) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외 발생)
3. WAS 오류 페이지 확인
4. WAS(/error-page/500, dispatcherType=ERROR) -> 필터 (x) -> 서블릿 -> 인터셉터 (x) -> 컨트롤러(error-page/500) -> view
```

### 오류 페이지
- 지금까지 예외 처리 페이지를 만들기 위해 다음과 같은 과정을 거쳤다.
  + WebServerCustomizer 만들고
  + 예외 종류에 따라서 ErrorPage 추가하고
  + 예외 처리용 컨트롤러 ErrorPageController 를 만들었다.
- 스프링 부트는 모든 과정을 자동화 해준다.
  + ErrorPage 를 자동으로 등록한다. 이때 /error 라는 경로로 기본 오류 페이지를 설정한다.
  + BasicErrorController 라는 스프링 컨트롤러를 자동으로 등록한다.
- 순서
  + 뷰 템플릿
    - ```resources/templates/error/500.html```
    - ```resources/templates/error/5xx.html```
  + 정적 리소스 (```static```, ```public```)
    - ```resources/static/error/404.html```
    - ```resources/static/error/400.html```
    - ```resources/static/error/4xx.html```
  + 적용 대상이 없을 때 뷰 이름 (```error```)
    - ```resources/templates/error.html```
</p>
</details>

<details><summary>API 예외 처리</summary>
<p>

### 스프링 기본 에러 처리
- api도 ```BasicErrorController```를 사용할 수 있다.
  + ```errorHtml()```: ```produces = MediaType.TEXT_HTML_VALUE``` 클라이언트 요청의 Accept 헤더 값이 text/html 인 경우에 호출하여 view 를 반환
  + ```error()```: 그 외의 경우에 호출되고 ResponseEntity로 HTTP Body에 JSON 데이터를 반환한다.

### HandlerExceptionResolver
- 스프링 MVC는 컨트롤러(핸들러) 밖으로 예외가 던져진 경우 예외를 해결하고 동작을 새로 정의할 수 있는 방법을 제공한다.
- 컨트롤러 밖으로 던져진 예외를 해결하고 동작 방식을 변경하고 싶으면 HandlerExceptionResolver를 사용하면 된다.
- 반환 값에 따라 ```DispatcherServlet```의 동작 방식은 다음과 같다.
  + 빈 ModelAndView: 뷰를 랜더링하지 않고 정상 흐름으로 서블릿이 리턴된다.
  + ModelAndView 지정: ```ModelAndView```에 ```View```, ```Model``` 등의 정보를 지정해서 반환하면 뷰를 랜더링한다.
  + null: ```null```을 반환하면 다음 ```ExceptionResolver```를 찾아서 실행한다. 만약 처리할 수 있는 ```ExceptionResolver```가 없으면 예외 처리가 안되고 기존에 발생한 예외를 서블릿 밖으로 던진다.
- 등록 시
  + ```configureHandlerExceptionResolvers(...)```를 사용하면 스프링이 기본으로 등록하는 ```ExceptionResolver```가 제거되므로 주의
  + ```extendHandlerExceptionResolvers```를 사용하자
- 서블릿 컨테이너까지 예외를 던지지 않고 미리 처리하여 정상 응답처럼 전달하는 방식이다.
- ModelAndView를 반환하기 때문에 직접 만들어 사용하는데 어려움이 많다.

### 스프링이 제공하는 ExceptionResolver
- ExceptionHandlerExceptionResolver
  + ```@ExceptionHandler```를 처리한다.
- ResponseStatusExceptionResolver
  + HTTP 상태 코드를 지정해준다.
  + 다음 두가지 예외를 처리한다.
    - ```@ResponseStatus```가 달려있는 예외
    - ```ResponseStatusException``` 예외
- DefaultHandlerExceptionResolver
  + 스프링 내부 기본 예외를 처리한다.
  + 우선 순위가 가장 낮다.

### @ExceptionHandler
- HTML 화면 오류 vs API 오류
  + 웹 브라우저에 HTML 화면을 제공할 때는 오류가 발생하면 ```BasicErrorController```를 사용하는게 편하다.
  + API 는 예외에 따라 응답 데이터가 다양해질 수 있어 처리가 어렵다.
    - ```HandlerExceptionResolver```를 떠올려보면 ```ModelAndView```를 반환해야 했다. 이것은 API 응답에 필요하지 않다.
    - API 응답을 위해 HttpServletResponse 에 직접 응답 데이터를 넣어주었다. 이것은 매우 불편하다.
    - 특정 컨트롤러에서만 발생하는 예외를 별도로 처리하기 어렵다.
    - 따라서 스프링은 ```ExceptionHandlerExceptionResolver```를 통해 처리되는 ```@ExceptionHandler```를 제공한다.
- 해당 컨트롤러에서 발생한 에러를 처리한다.
- 지정한 예외와 그 자식 예외까지 같이 처리한다. 나눠져 있다면 자식 예외(더 자세한 예외)가 더 우선적으로 처리된다.
- 실행 흐름 예시
  + 컨트롤러를 호출한 결과 ```IllegalArgumentException``` 예외가 컨트롤러 밖으로 던져진다.
  + 예외가 발생했으므로 ```ExceptionResolver```가 작동한다. 가장 우선순위가 높은 ```ExceprionHandlerExceptionResolver```가 실행된다.
  + ```ExceprionHandlerExceptionResolver```는 해당 컨트롤러에 ```IllegalArgumentException```을 처리할 수 있는 ```@ExceptionHandler```가 있는지 확인한다.
  + ```illegalExceptionHandler()```를 실행한다. ```@RestController```이므로 ```illegalExceptionHandler()```에도 ```@ResponseBody```가 적용되어 HTTP 컨버터가 사용되고 JSON으로 반환된다.
  + ```@ResponseStatus(HttpStatus.BAD_REQUEST)```를 지정했으므로 HTTP 상태 코드 400으로 응답한다.
- 참고: https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/exceptionhandlers.html

### @ControllerAdvice
- 대상으로 지정한 여러 컨트롤러에 ```@ExceptionHandler```, ```@InitBinder``` 기능을 부여해주는 역할을 한다.
- ```@ControllerAdvice```에 대상을 지정하지 않으면 모든 컨트롤러에 적용된다. (글로벌 적용)
- ```@RestControllerAdvice```는 ```@ControllerAdvice```와 같고 ```@ResponseBody```가 추가되어 있다.
- 참고: https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-advice.html
</p>
</details>


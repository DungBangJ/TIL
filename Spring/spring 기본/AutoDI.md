# 다양한 의존관계 주입 방법

- 생성자 주입
- 수정자 주입(setter 주입)
- 필드 주입
- 일반 메서드 주입

## 생성자 주입

- 생성자 호출시점에 단 1번만 호출되는 것이 보장된다.
- 불변, 필수 의존관계에 사용

```java

@Component
public class OrderServiceImpl implements OrderService {
     private final MemberRepository memberRepository;
     private final DiscountPolicy discountPolicy;

     @Autowired //생성자가 딱 1개만 있으면 @Autowired를 생략해도 자동 주입 된다.
     public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy
             discountPolicy) {
          this.memberRepository = memberRepository;
          this.discountPolicy = discountPolicy;
     }
}
```

## 수정자 주입(setter 주입)

- 선택, 변경 가능성이 있는 의존관계에 사용

```java

@Component
public class OrderServiceImpl implements OrderService {
     private MemberRepository memberRepository;
     private DiscountPolicy discountPolicy;

     @Autowired
     public void setMemberRepository(MemberRepository memberRepository) {
          this.memberRepository = memberRepository;
     }

     @Autowired
     public void setDiscountPolicy(DiscountPolicy discountPolicy) {
          this.discountPolicy = discountPolicy;
     }
}
```

- @Autowired의 기본 동작은 주입할 대상이 없으면 오류가 발생한다.
    - 주입할 대상이 없어도 동작하게 하려면 @Autowired(required = false)로 지정한다.

## 필드 주입

- 간결하지만 외부에서 변경이 불가능해 테스트 하기 힘들다.
- 사용하지 말자.

```java

@Component
public class OrderServiceImpl implements OrderService {
     @Autowired
     private MemberRepository memberRepository;
     @Autowired
     private DiscountPolicy discountPolicy;
}
```

## 일반 메서드 주입

- 한 번에 여러 필드 주입 가능

```java

@Component
public class OrderServiceImpl implements OrderService {
     private MemberRepository memberRepository;
     private DiscountPolicy discountPolicy;

     @Autowired
     public void init(MemberRepository memberRepository, DiscountPolicy
             discountPolicy) {
          this.memberRepository = memberRepository;
          this.discountPolicy = discountPolicy;
     }
}
```

# 옵션 처리

주입할 스프링 빈이 없어도 동작해야 할 때 옵션으로 처리하는 방법

- @Autowired(required=false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
- org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null이 입력된다.
- Optional<> : 자동 주입할 대상이 없으면 Optional.empty 가 입력된다.

```java
//호출 자체가 안됨
@Autowired(required = false)
public void setNoBean1(Member member){
        System.out.println("setNoBean1 = "+member);
        }
//null 호출
@Autowired
public void setNoBean2(@Nullable Member member){
        System.out.println("setNoBean2 = "+member);
        }
//Optional.empty 호출
@Autowired(required = false)
public void setNoBean3(Optional<Member> member){
        System.out.println("setNoBean3 = "+member);
        }
```

- @Nullable, Optional은 스프링 전반에 걸쳐서 지원된다. 예를 들어서 생성자 자동 주입에서 특정 필드에만 사용해도 된다

# 생성자 주입 강추

- 불변
    - 대부분의 의존관계 주입은 한번 일어나면 애플리케이션 종료시점까지 의존관계를 변경할 일이 없다.
    - 생성자 주입은 객체를 생성할 때 딱 1번만 호출되므로 이후에 호출되는 일이 없다.
        - 불변 설계 가능
- 누락
    - 생성자 주입을 사용하면 주입 데이터를 누락 했을 때 컴파일 오류가 발생한다.
        - IDE에서 바로 어떤 값을 필수로 주입해야 하는지 알 수 있다.

## final 키워드

생성자 주입을 사용하면 필드에 final 키워드를 사용할 수 있다.

- 생성자에서 혹시라도 값이 설정되지 않는 오류를 컴파일 시점에 막아준다.

기본적으로 생성자 주입을 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여해서 사용하자.

# 롬복

@RequiredArgsConstructor

- final이 붙은 필드를 모아서 생성자를 자동으로 만들어준다.

```java

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
     private final MemberRepository memberRepository;
     private final DiscountPolicy discountPolicy;
}
```

- 롬복이 자바의 애노테이션 프로세서라는 기능을 이용해서 컴파일 시점에 생성자 코드를 자동으로 생성해준다.
- class를 열엽보면 다음의 코드가 추가되어 있다.

```java
public OrderServiceImpl(MemberRepository memberRepository,DiscountPolicy discountPolicy){
        this.memberRepository=memberRepository;
        this.discountPolicy=discountPolicy;
        }
```

## 롬복 적용 방법

build.gradle에 라이브러리 및 환경 추가

```
plugins {
	id 'org.springframework.boot' version '2.6.4'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
}

group = 'hello'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

//lombok 설정 추가 시작
configurations {
	compileOnly{
		extendsFrom annotationProcessor
	}
}
//lombok 설정 추가 끝

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter'
	
	//lombok 라이브러리 추가 시작
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testCompileOnly 'org.projectlombok:lombok'
	testAnnotationProcessor 'org.projectlombok:lombok'
	//lombok 라이브러리 추가 끝

	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
	useJUnitPlatform()
}
```

# 조회 빈이 2개 이상일 때 - 문제

@Autowired는 타입(Type)으로 조회한다. getBean()과 유사하게 동작한다고 보면 된다.

```java
@Autowired
private DiscountPolicy discountPolicy
```

타입으로 조회를 하는데 선택된 빈이 2개 이상이면 문제가 발생한다.

```java

@Component
public class FixDiscountPolicy implements DiscountPolicy {
}

...

@Component
public class RateDiscountPolicy implements DiscountPolicy {
}
```

- NoUniqueBeanDefinitionException 예외가 발생한다.
    - 이름이 다르지만 타입이 같기 때문에 예외 발생
    - 의존 관계 자동 주입에서 해결하는 방법 여러 가지
        - @Autowired 필드명 매칭
        - @Qualifier끼리 매칭
        - @Primary 사용

# @Autowired 필드명, @Qualifier, @Primary

## @Autowired 필드명 매칭

필드명을 빈 이름으로 변경하면 먼저 타입 매칭을 시도하고 2개 이상의 동일 타입이 있다면 그때 추가로 필드명을 이용해 매칭한다.

```java
@Autowired
private DiscountPolicy rateDiscountPolicy
```

## @Qualifier 사용

같은 이름의 @Qualifier끼리 매칭한다.

- 같은 이름의 @Qualifier가 없다면 그 이름의 스프링 빈을 추가로 찾는다.

빈 등록시 @Qualifier를 붙여준다.

```java

@Component
@Qualifier("mainDiscountPolicy")
public class RateDiscountPolicy implements DiscountPolicy {
}

@Component
@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy {
}
```

생성자 자동 주입 예시

```java
@Autowired
public OrderServiceImpl(MemberRepository memberRepository,
@Qualifier("mainDiscountPolicy") DiscountPolicy
        discountPolicy){
        this.memberRepository=memberRepository;
        this.discountPolicy=discountPolicy;
        }
```

수정자 자동 주입 예시

```java
@Autowired
public DiscountPolicy setDiscountPolicy(@Qualifier("mainDiscountPolicy")
DiscountPolicy discountPolicy){
        return discountPolicy;
        }
```

## @Primary 사용

@Autowired 시에 여러 빈이 매칭되면 @Primary가 우선권을 가진다.

```java

@Component
@Primary
public class RateDiscountPolicy implements DiscountPolicy {
}

@Component
public class FixDiscountPolicy implements DiscountPolicy {
}
```

- RateDiscountPolicy가 스프링 빈 등록 시에 우선권을 갖는다.

# 애노테이션 직접 만들기

@Qualifier의 이름은 문자열이기 때문에 컴파일 시에 오류가 있는지 체크할 수가 없다.

- 직접 만들어야 한다.

```java
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}
```

- 이 애노테이션은 @Qualifier처럼 사용한다.

```java

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {
}
```

```java
//생성자 자동 주입
@Autowired
public OrderServiceImpl(MemberRepository memberRepository,
@MainDiscountPolicy DiscountPolicy discountPolicy){
        this.memberRepository=memberRepository;
        this.discountPolicy=discountPolicy;
        }
//수정자 자동 주입
@Autowired
public DiscountPolicy setDiscountPolicy(@MainDiscountPolicy DiscountPolicy
        discountPolicy){
        return discountPolicy;
        }
```

# 스프링 컨테이너를 생성하면서 스프링 빈 등록하기

```java
new AnnotationConfigApplicationContext(AutoAppConfig.class,DiscountService.class);
```

- 스프링 컨테이너는 생성자에 클래스 정보를 받는다.
    - 여기에 클래스 정보를 넘기면 해당 클래스가 스프링 빈으로 자동 등록된다.
        - new AnnotationConfigApplicationContext() 를 통해 스프링 컨테이너를 생성한다.
        - AutoAppConfig.class , DiscountService.class 를 파라미터로 넘기면서 해당 클래스를 자동으로 스프링 빈으로 등록한다


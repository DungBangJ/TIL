# 컴포넌트 스캔과 의존관계 자동 주입

스프링은 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 컴포넌트 스캔이라는 기능을 제공한다.

- 의존관계도 자동으로 주입하는 @Autowired라는 기능 제공

AutoAppConfig.java

```java
package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.*;

@Configuration
@ComponentScan(
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes =
                Configuration.class))
public class AutoAppConfig {

}
```

- @ComponentScan을 붙인다
    - 기존의 AppConfig와는 다르게 @Bean으로 등록한 클래스가 없다.
- 컴포넌트 스캔을 사용하면 @Configuration이 붙은 설정 정보도 자동으로 등록된다.
    - 앞의 코드에서 쓰인 @Configuration을 제외하기 위해 excludeFilters를 사용했다.
- 컴포넌트 스캔은 @Component 애너테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.
    - @Configuration도 @Component가 붙어 있다.

각 클래스가 컴포넌트 스캔의 대상이 되도록 @Component 애너테이션을 붙여주자.

MemoryMemberRepository @Component 추가

```java

@Component
public class MemoryMemberRepository implements MemberRepository {
}
```

RateDiscountPolicy @Component 추가

```java

@Component
public class RateDiscountPolicy implements DiscountPolicy {
}
```

MemberServiceImpl @Component, @Autowired 추가

```java

@Component
public class MemberServiceImpl implements MemberService {
     private final MemberRepository memberRepository;

     @Autowired
     public MemberServiceImpl(MemberRepository memberRepository) {
          this.memberRepository = memberRepository;
     }
}
```

- @Autowired를 사용하면 생성자에서 여러 의존관계도 한번에 주입받을 수 있다.

## 컴포넌트 스캔과 자동 의존관계 주입 동작 과정

1. @ComponentScan
    - @ComponentScan은 @Component가 붙은 모든 클래스를 스프링 빈으로 등록한다.
    - 빈 이름: 맨 앞글자만 소문자
    - 빈 이름 지정: `@Component("memberService2")`

2. @Autowired 의존관계 자동 주입
    - 생성자에 @Autowired를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.
        - 타입이 같은 빈을 찾아서 주입하는 것
            - getBean(MemberRepository.class)와 동일하다고 보면 된다.

## 탐색 위치와 기본 스캔 대상

탐색할 패키지의 시작 위치 지정

- 필요한 위치부터 탐색 권장

```java
@ComponentScan(
        basePackages = "hello.core",
        basePackageClasses = AutoAppConfig.class,
        }
```

- basePackages: 이 패키지를 포함하는 하위 패키지를 모두 탐색
- basePackageClasses: 지정한 클래스의 패키지를 탐색 시작 위치로 지정

## 컴포넌트 스캔 기본 대상

컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함한다.

- @Component : 컴포넌트 스캔에서 사용
- @Controlller : 스프링 MVC 컨트롤러에서 사용
- @Service : 스프링 비즈니스 로직에서 사용
- @Repository : 스프링 데이터 접근 계층에서 사용
- @Configuration : 스프링 설정 정보에서 사용

컴포넌트 스캔의 용도 뿐만 아니라 다음 애노테이션이 있으면 스프링은 부가 기능을 수행한다.

- @Controller : 스프링 MVC 컨트롤러로 인식
- @Repository : 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환해준다.
- @Configuration : 앞서 보았듯이 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리를 한다.
- @Service : 사실 @Service 는 특별한 처리를 하지 않는다. 대신 개발자들이 핵심 비즈니스 로직이 여기에 있겠구나 라고 비즈니스 계층을 인식하는데 도움이 된다

## 필터

컴포넌트 스캔 대상에 추가할 애노테이션

```java
package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
```

컴포넌트 스캔 대상에서 제외할 애노테이션

```java
package hello.core.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
```

컴포넌트 스캔 대상에 추가할 클래스

```java
package hello.core.scan.filter;

@MyIncludeComponent
public class BeanA {
}
```

- @MyIncludeComponent 적용

컴포넌트 스캔 대상에서 제외할 클래스

```java
package hello.core.scan.filter;

@MyExcludeComponent
public class BeanB {
}
```

- @MyExcludeComponent 적용

설정 정보와 전체 테스트 코드

```java
package hello.core.scan.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.context.annotation.ComponentScan.Filter;

public class ComponentFilterAppConfigTest {
     @Test
     void filterScan() {
          ApplicationContext ac = new
                  AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);
          BeanA beanA = ac.getBean("beanA", BeanA.class);
          assertThat(beanA).isNotNull();
          Assertions.assertThrows(
                  NoSuchBeanDefinitionException.class,
                  () -> ac.getBean("beanB", BeanB.class));
     }

     @Configuration
     @ComponentScan(
             includeFilters = @Filter(type = FilterType.ANNOTATION, classes =
                     MyIncludeComponent.class),
             excludeFilters = @Filter(type = FilterType.ANNOTATION, classes =
                     MyExcludeComponent.class)
     )
     static class ComponentFilterAppConfig {
     }
}
```
- includeFilters 에 MyIncludeComponent 애노테이션을 추가해서 BeanA가 스프링 빈에 등록된다.
- excludeFilters 에 MyExcludeComponent 애노테이션을 추가해서 BeanB는 스프링 빈에 등록되지
않는다.

## 중복 등록과 충돌

1. 자동 빈 등록 vs 자동 빈 등록
   - ConflictingBeanDefinitionException 예외 발생

2. 수동 빈 등록 vs 자동 빈 등록
   - 수동 빈 등록이 우선권을 갖는다.
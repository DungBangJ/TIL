# 컴포넌트 스캔

스프링 컨테이너를 생성할 떄 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 기능

```java
package yback.board;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.*;

@Configuration
@ComponentScan(
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
}
```

- 컴포넌트 스캔을 사용하려면, 설정 정보에 `@ComponentScan` annotation을 붙여주면 된다.
  - @ComponentScan 내부에 있는 필터는 AppConfig, TestConfig등의 @Component anotation이 붙은 설정 정보들인데, 이 설정 정보들을 제외하고 스캔하기 위해서 추가해준 것이다.
    - 지금은 AppConfig와 같이 직접 의존 관계 주입을 하는 설정 정보가 아닌 자동으로 빈을 찾아 의존 관계를 주입해주는 AutoAppConfig를 만들고 있기 때문에 무시해주자.
- 클래스 안에는 아무것도 필요없다.

컴포넌트 스캔은 @Component annotation이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다.

- AppConfig, TestConfig등의 @Configuration이 붙은 설정 정보들도 컴포넌트 스캔 대상에 포함이 되는 이유는 @Configuration 내부에 @Component가 포함되어 있기 때문이다.
- 각 클래스가 컴포넌트 스캔의 대상이 되도록 @Component 애노테이션을 붙여주자.
  - `MemberRepositoryImpl`, `MemberServiceImpl`, `RateDiscountPolicy`에 @Component를 추가
- AppConfig에서는 @Bean으로 직적 설정 정보를 작성했고, 의존관계도 직접 명시했다.
  - 이제는 이렇게 직접 적용시키는 부분이 없기 때문에 의존관계 주입은 @Component를 붙인 해당 클래스 내부에서 해결해야 한다.
    - @Autowired로 의존관계를 자동으로 주입해준다.

```java
package yback.board.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

     private final MemberRepository memberRepository;

     @Autowired
     public MemberServiceImpl(MemberRepository memberRepository) {
          this.memberRepository = memberRepository;
     }

     @Override
     public void join(Member member) {
          memberRepository.save(member);
     }

     @Override
     public Member findMember(Long memerId) {
          return memberRepository.findById(memerId);
     }
}
```

- OrderServiceImpl에도 @Component, @Autowired를 추가해주자.

```java
package yback.board.discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yback.board.member.Member;
import yback.board.member.MemberRepository;

@Component
public class OrderServiceImpl implements OrderService{
     private final MemberRepository memberRepository;
     private final DiscountPolicy discountPolicy;

     @Autowired
     public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
          this.memberRepository = memberRepository;
          this.discountPolicy = discountPolicy;
     }


     @Override
     public Order createOrder(Long memberId, String itemName, int itemPrice) {
          Member member = memberRepository.findById(memberId);
          int discountPrice = discountPolicy.discount(member, itemPrice);

          return new Order(memberId, itemName, itemPrice, discountPrice);
     }
}
```

이렇게 변경해주고 AutoAppConfig를 설정 정보 파라미터로 하는 스프링 컨테이너를 테스트한다.

```java
package yback.board.scan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import yback.board.AutoAppConfig;
import yback.board.member.MemberService;

class AutoAppConfigTest {

     @Test
     void basicScan() {
          ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
          MemberService memberService = ac.getBean(MemberService.class);
          Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
     }
}
```

- 문제 없이 잘 돌아가는 것을 확인할 수 있다.

## 컴포넌트 스캔 과정

1. @ComponentScan은 @Component가 붙은 모든 클래스를 스프링 빈으로 등록한다.
   - 빈을 등록할 때 이름은 첫 글자만 소문자로 바꿔서 등록한다.
   - 빈 이름을 지정할 수도 있다. (`@Component("memberService2")`)

2. @Autowired 의존관계 자동 주입
   - 생성자에 @Autowired를 지정하면, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입한다.

## 컴포넌트 스캔 탐색 위치와 기본 스캔 대상

### 탐색할 패키지의 시작 위치 지정

모든 자바 클래스를 다 컴포넌트 스캔하면 시간이 너무 오래 걸린다.

- 꼭 필요한 위치부터 탐색하도록 지정할 수 있다.

```java
@ComponentScan(
    basePackages = "yback.board"
)
```

- basePackages에 해당하는 디렉토리부터 모든 하위 패키지를 탐색한다.
- basePackages = {"yback.member", "yback.service"}
  - 이렇게 한 번에 여러 디렉토리를 시작위치로 설정할 수도 있다.
- 그냥 @ComponentScan만 있다면 이것이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.

권장하는 방법

- 설정 정보 클래스의 위치를 프로젝트 최상단에 놓고, 아무런 위치 설정을 하지 않는 것이 스프링 부트의 기본 기능이므로 이를 따르자.
  - @SpringBootApplication 안에 @ComponentScan이 들어있고, 이 실행 파일은 항상 루트 디렉토리에 위치해 있다.

### 컴포넌트 스캔 기본 대상

컴포넌트 스캔은 @Component 뿐만 아니라 다음의 annotation이 붙은 클래스도 스캔 대상에 포함한다.(모두 @Component를 포함하고 있기 때문에 스캔 가능한 것)

- @Controller
  - 스프링 MVC 컨트롤러에서 사용
- @Service
  - 스프링 비즈니스 로직에서 사용
- @Repository
  - 스프링 데이터 접근 계층에서 사용
- @Configuration
  - 스프링 설정 정보에서 사용

### 필터

includeFilters

- 컴포넌트 스캔 대상을 추가로 지정

excludeFilters

- 컴포넌트 스캔에서 제외할 대상을 지정

컴포넌트 스캔 대상에 추가할 애노테이션

```java
package yback.board.scan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {
}
```

컴포넌트 스캔 대상에서 제외할 애노테이션

```java
package yback.board.scan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent {
}
```

위의 두 사용자 정의 annotation을 사용해보자.

컴포넌트 스캔 대상에서 추가할 클래스

```java
package yback.board.scan.filter;

@MyIncludeComponent
public class BeanA {
}
```

컴포넌트 스캔 대상에서 제외할 클래스

```java
```java
package yback.board.scan.filter;

@MyExcludeComponent
public class BeanB {
}
```

```java
package yback.board.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.context.annotation.ComponentScan.*;

public class ComponentFilterAppConfigTest {

     @Test
     void filterScan() {
          ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppConfig.class);

          BeanA beanA = ac.getBean("beanA", BeanA.class);
          assertThat(beanA).isNotNull();
          assertThrows(NoSuchBeanDefinitionException.class, () ->
                  ac.getBean("beanB", BeanB.class));
     }

     @Configuration
     @ComponentScan(
             includeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyIncludeComponent.class),
             excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = MyExcludeComponent.class)
     )
     static class ComponentFilterAppConfig {
     }

}
```

- @ComponentScan에서 includeFilters는 포함할 annotation, excludeFilters는 제외할 annotation을 나타낸다.

## 중복 등록과 충돌

컴포넌트 스캔 중에 같은 빈 이름이 등록되었을 때

1. 자동 빈 등록 vs 자동 빈 등록
2. 수동 빈 등록 vs 자동 빈 등록

### 자동 빈 등록 vs 자동 빈 등록

컴포넌트 스캔에 의해 자동으로 스프링 빈이 등록되었을 때 이름이 같은 경우 오류 발생

- ConflictingBeanDefinitionException 예외 발생

### 수동 빈 등록 vs 자동 빈 등록

자동 빈 등록하는 가운데 수동 빈 등록이 있다면 수동 빈 등록이 우선권을 갖는다.

```java
@Configuration
@ComponentScan
public class AutoAppConfig {

    @Bean(name = "memberRepositoryImpl")
    public MemberRepository memberRepository() {
        return new MemberRepositoryImpl();
    }
}
```

- 이렇게 @ComponentScan annotation이 붙은 설정 정보 클래스 내부에 수동으로 빈 등록을 나타낸다면, 해당 빈의 자동 빈 등록은 무시되고, 수동 빈 등록이 된다.

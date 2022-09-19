# 스프링 적용하기

## AppConfig

기존의 AppConfig에 annotation을 추가한다.

```java
package yback.board;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yback.board.discount.DiscountPolicy;
import yback.board.discount.FixDiscountPolicy;
import yback.board.discount.OrderService;
import yback.board.discount.OrderServiceImpl;
import yback.board.member.*;

@Configuration
public class AppConfig {

     @Bean
     public MemberService memberService() {
          return new MemberServieImpl(memberRepository());
     }

     @Bean
     public OrderService orderService() {
          return new OrderServiceImpl(
                  memberRepository(),
                  discountPolicy()
          );
     }

     @Bean
     public MemberRepository memberRepository() {
          return new MemberRepositoryImpl();
     }

     @Bean
     public DiscountPolicy discountPolicy() {
          return new FixDiscountPolicy();
     }
}
```

class에 `@Configuration` annotation을 붙여주고, 각 메서드에는 `@Bean` annotation을 붙여준다.

- 이렇게 추가해주면 스프링 컨테이너에 스프링 빈으로 등록된다.

## MemberApp, OrderApp

AppConfig에서 빈으로 등록했으니, main 클래스들을 바꿔서 실행해본다.

MemberApp

```java
package yback.board;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import yback.board.member.Grade;
import yback.board.member.Member;
import yback.board.member.MemberService;

public class MemberApp {
     public static void main(String[] args) {
          ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
          MemberService memberService = ac.getBean("memberService", MemberService.class);
          Member member = new Member(1L, "memberA", Grade.VIP);
          memberService.join(member);

          Member findMember = memberService.findMember(1L);
          System.out.println("new member = " + member.getName());
          System.out.println("find member = " + findMember.getName());
     }
}
```

OrderApp

```java
package yback.board;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import yback.board.discount.Order;
import yback.board.discount.OrderService;
import yback.board.member.Grade;
import yback.board.member.Member;
import yback.board.member.MemberService;

public class OrderApp {
     public static void main(String[] args) {
          ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
          MemberService memberService = ac.getBean("memberService", MemberService.class);
          OrderService orderService = ac.getBean("orderService", OrderService.class);

          long memberId = 1L;
          Member member = new Member(memberId, "memberA", Grade.VIP);
          memberService.join(member);

          Order order = orderService.createOrder(memberId, "itemA", 10000);
          System.out.println("order = " + order);
     }
}
```

스프링 컨테이너

- ApplicationContext를 스프링 컨테이너라고 한다.
- AppConfig를 사용해서 직접 객체를 생성하고 DI를 했지만, 스프링 컨테이너를 사용하면 모든게 자동으로 된다.
- 스프링 컨테이너는 @Configuration이 붙은 AppConfig를 설정 정보로 사용한다.
  - @Bean이 붙은 메소드를 모두 호출해서 반환된 객체를 스프리 컨테이너에 등록하고 이 때의 메소드를 스프링 빈이라고 부르고, 메소드의 이름은 스프링 빈의 이름이 된다.

# 스프링 컨테이너

## 스프링 컨테이너 생성

```java
ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
```

ApplicationContext

- 인터페이스
- 스프링 컨테이너

AnnotationConfigApplicationContext

- 인터페이스 구현체

~.class

- 구성 정보를 지정해 주는 것
- `@Configuration` annotation이 붙은 클래스
- `AppConfig.class`

>스프링 빈의 이름은 변경 가능하다.
>`@Bean(name="myMemberService")"`

new 연산자로 객체를 생성할 때 파라미터로 들어온 구성 정보를 바탕으로 스프링 빈을 등록하고 의존관계도 모두 설정하여 최종적으로 스프링 컨테이너의 구성을 마친다.

## 스프링 컨테이너에 등록된 모든 빈 조회

```java
package yback.board.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.validation.ObjectError;
import yback.board.AppConfig;

public class ApplicationContextInfoTest {
     AnnotationConfigApplicationContext acac = new AnnotationConfigApplicationContext(AppConfig.class);

     @Test
     @DisplayName("모든 빈 출력하기")
     void findAllBean() {
          String[] beanDefinitionNames = acac.getBeanDefinitionNames();
          for (String beanDefinitionName : beanDefinitionNames) {
               Object bean = acac.getBean(beanDefinitionName);
               System.out.println("name =  = " + beanDefinitionName + ", object = " + bean);
          }
     }

     @Test
     @DisplayName("애플리케이션 빈 출력하기")
     void findApplicationBean() {
          String[] beanDefinitionNames = acac.getBeanDefinitionNames();
          for (String beanDefinitionName : beanDefinitionNames) {
               BeanDefinition beanDefinition = acac.getBeanDefinition(beanDefinitionName);
               if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                    Object bean = acac.getBean(beanDefinitionName);
                    System.out.println("name = " + beanDefinitionName + ", object = " + bean);
               }
          }
     }
}
```

모든 빈 출력

- 스프링에 등록된 모든 빈 정보를 출력
- ac.getBeanDefinitionNames()
  - 스프링의 등록된 모든 빈 이름 조회
- ac.getBean()
  - 빈 이름으로 빈 객체를 조회

애플리케이션 빈 출력

- 스프링이 내부에서 사용하는 빈은 제외하고, 내가 등록한 빈만 출력
- getRole()로 구분 가능하다.
  - ROLE_APPLICATION
    - 일반적으로 사용자가 정의한 빈
  - ROLE_INFRASTRUCTURE
    - 스프링이 내부에서 사용하는 빈

# AppConfig 스프링 기반으로 변경

```java
package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
     @Bean
     public MemberService memberService() {
          return new MemberServiceImpl(memberRepository());
     }

     @Bean
     public OrderService orderService() {
          return new OrderServiceImpl(
                  memberRepository(),
                  discountPolicy());
     }

     @Bean
     public MemberRepository memberRepository() {
          return new MemoryMemberRepository();
     }

     @Bean
     public DiscountPolicy discountPolicy() {
          return new RateDiscountPolicy();
     }
}
```

- `@Configuration`과 `@Bean`을 붙여준다.
    - `@Bean`을 붙여주면 스프링 컨테이너에 스프링 빈으로 등록하는 것

# MemberApp, OrderApp에 스프링 컨테이너 적용

MemberApp

```java
package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import
        org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
     public static void main(String[] args) {
// AppConfig appConfig = new AppConfig();
// MemberService memberService = appConfig.memberService();
          ApplicationContext applicationContext = new
                  AnnotationConfigApplicationContext(AppConfig.class); //@Configuration을 찾아온다.
          MemberService memberService =
                  applicationContext.getBean("memberService", MemberService.class); //빈을 가져온다. 이름(보통 메서드 이름으로), 타입
          Member member = new Member(1L, "memberA", Grade.VIP);
          memberService.join(member);
          Member findMember = memberService.findMember(1L);
          System.out.println("new member = " + member.getName());
          System.out.println("find Member = " + findMember.getName());
     }
}
```

OrderApp

```java
package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import
        org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {
     public static void main(String[] args) {
// AppConfig appConfig = new AppConfig();
// MemberService memberService = appConfig.memberService();
// OrderService orderService = appConfig.orderService();
          ApplicationContext applicationContext = new
                  AnnotationConfigApplicationContext(AppConfig.class);
          MemberService memberService =
                  applicationContext.getBean("memberService", MemberService.class);
          OrderService orderService = applicationContext.getBean("orderService",
                  OrderService.class);
          long memberId = 1L;
          Member member = new Member(memberId, "memberA", Grade.VIP);
          memberService.join(member);
          Order order = orderService.createOrder(memberId, "itemA", 10000);
          System.out.println("order = " + order);
     }
}
```
- 스프링 컨테이너
  - ApplicationContext
    - 인터페이스
  - 스프링 컨테이너를 통해서 DI
  - `@Configuration`이 붙은 AppConfig를 설정(구성)정보로 사용
    - `@Bean`이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록
      - 등록된 것을 스프링 빈이라고 한다.
      - 스프링 빈은 `@Bean`이 붙은 메서드의 이름을 스프링 빈의 이름으로 사용한다.
  - 스프링 컨테이너를 통해서 필요한 스프링 빈(객체)를 찾아야 한다.
    - getBean() 메서드로 찾는다.

# 스프링 컨테이너 생성 과정

1. 스프링 컨테이너 생성
   - new AnnotationConfigApplicationContext(AppConfig.class)
   - 스프링 컨테이너를 생성할 때는 구성 정보를 지정해줘야 한다.
   - AppConfig.class: 구성 정보

2. 스프링 빈 등록
   - 빈 이름은 메서드 이름
   - 빈 이름 직접 부여 가능
     - `@Bean(name="memberService2")`
   - 빈 이름은 항상 달라야 한다.

3. 스프링 빈 의존관계 설정 - 준비
   - 스프링 컨테이너는 설정 정보를 참고해서 의존관계를 주입(DI)한다.

# BeanFactory와 ApplicationContext
- 둘 다 스프링 컨테이너

![3](./assets/DIPCode(Yes Spring)-1647238145489.png)

- BeanFactory
  - 스프링 컨테이너의 최상위 인터페이스
  - 관리, 조회 기능
  - getBean() 제공

- ApplicationContext
  - 부가기능

![4](./assets/DIPCode(Yes Spring)-1647238436332.png)

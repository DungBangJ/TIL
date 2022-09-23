# 다양한 의존관계 주입 방법

- 생성자 주입
- 수정자 주입(setter 주입)
- 필드 주입
- 일반 메서드 주입

## 생성자 주입

이름 그대로 생성자를 통해서 의존 관계를 주입 받는 방법

- 생성자 호출 시점에 딱 1번만 호출되는 것이 보장된다.
- 불변, 필수 의존관계에 사용

```java
@Component
public class OrderServiceImpl implements OrderService{
     private final MemberRepository memberRepository;
     private final DiscountPolicy discountPolicy;

     //@Autowired
     public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
          this.memberRepository = memberRepository;
          this.discountPolicy = discountPolicy;
     }
}
```

- 생성자가 하나라면 @Autowired를 생략해도 자동 주입이 된다.

## 수정자 주입(setter 주입)

setter라 불리는 필드의 값을 변경하는 수정자 메서드를 통해서 의존관계를 주입하는 방법

- 선택, 변경 가능성이 있는 의존관계에 사용

```java
@Component
public class OrderServiceImpl implements OrderService{
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

## 필드 주입

필드에 바로 주입하는 방법

- 간결하지만, 외부에서 변경이 불가능하기에 테스트하기 힘들다는 단점이 있다.
- 사용하지 않는 것이 좋다.

```java
@Component
public class OrderServiceImpl implements OrderService{
     @Autowired
     private MemberRepository memberRepository;
     
     @Autowired
     private DiscountPolicy discountPolicy;
}
```

- IDE에서도 추천하지 않는다고 뜬다.

## 일반 메서드 주입

일반 메서드를 통해 주입 받을 수 있다.

- 한 번에 여러 필드를 주입 받을 수 있다.

```java
@Component
public class OrderServiceImpl implements OrderService{

     private MemberRepository memberRepository;
     private DiscountPolicy discountPolicy;

     @Autowired
     public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
          this.memberRepository = memberRepository;
          this.discountPolicy = discountPolicy;
     }
}
```

## 옵션 처리

주입할 스프링 빈이 없어도 동작해야 할 때가 있다.

자동 주입 대상을 옵션으로 처리하는 3가지 방법

- `@Autowired(required = false)`
  - 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출되지 않는다.
- org.springframework.lang.@Nullable
  - 자동 주입할 대상이 없으면 null이 입력된다.
- Optional<>
  - 자동 주입할 대상이 없으면 Optional.empty가 입력된다.

```java
//호출 안됨
@Autowired(required = false)
public void setNoBean1(Member member) { //Member는 스프링 빈이 아니므로 이 메서드는 호출 자체가 안된다.
 System.out.println("setNoBean1 = " + member);
}

//null 호출
@Autowired
public void setNoBean2(@Nullable Member member) {
 System.out.println("setNoBean2 = " + member); //setNoBean2 = null
}

//Optional.empty 호출
@Autowired(required = false)
public void setNoBean3(Optional<Member> member) {
 System.out.println("setNoBean3 = " + member); //setNoBean3 = Optional.empty
}
```

# 생성자 주입을 사용하자

## 불변

- 대부분의 의존관계 주입은 한번 일어나면 애플리케이션 종료 시점까지 의존관계를 변경할 일이 없다.
  - 대부분은 종료까지 변경되면 안된다.
- 수정자 주입은 setXXX 메서드를 public으로 열어두어야 한다.
  - 누군가 실수로 변경할 수도 있기 때문에 메서드를 열어두는 것은 좋지 않다.

## 수정자 주입과 차이

수정자 주입은 의존관계 주입에서 누락이 생길경우, 실행 결과로 NPE가 뜨는데, 생성자 주입은 컴파일 오류를 발생시키므로 IDE에서 바로 어떤 값을 필수로 주입해야 하는지 알 수 있다.

## final 키워드

생성자 주입은 필드에 final 키워드를 사용할 수 있다.

- 혹시라도 생성자에서 값이 설정되지 않았다면 컴파일 오류를 발생시킨다.

>생성자 주입을 제외한 나머지 주입 방식은 모두 생성자 이후에 호출되므로, 필드에 final 키워드를 사용할 수 없다.

# 롬복과 최신 트렌드

롬복을 사용한다면 롬복 라이브러리가 제공하는 @RequiredArgsConstructor 기능을 사용할 수 있다.

- final이 붙은 필드를 모아서 생성자를 자동으로 만들어준다.

```java
@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

     private final MemberRepository memberRepository;
     private final DiscountPolicy discountPolicy;
}
```

- 이렇게 생성자를 딱 1개만 사용해서 롬복을 사용할 수 있게끔 만드는 것이 트렌드이다.

# 조회 빈이 2개 이상 (문제)

```java
@Autowired //@Autowired는 타입(type)으로 조회한다.
private DiscountPolicy discountPolicy
.
.
.

@Component
public class FixDiscountPolicy implements DiscountPolicy {}
.
.
.
@Component
public class RateDiscountPolicy implements DiscountPolicy {}
```

자손 클래스가 두개 이상일 때 부모 클래스를 조회한다면 NoSuchBeanDefinitionException 오류가 발생한다.

- 이때 하위 타입으로 지정하여 조회할 수도 있지만, 하위 타입으로 지정하는 것은 DIP를 위배하고 유연성이 떨어진다.

## @Autowired 필드명

@Autowired는 타입 매칭을 시도하고, 해당 타입의 빈이 여러 개가 있다면, 파라미터 이름으로 빈 이름을 추가 매칭한다.

원래 코드

```java
@Autowired
private DiscountPolicy discountPolicy
```

필드 명을 빈 이름으로 변경한 코드

```java
@Autowired
private DiscountPolicy rateDiscountPolicy
```

>필드 명 매칭은 먼저 타입 매칭을 시도하고, 그 결과에서 같은 타입의 빈이 여러 개가 있다면 추가로 필드 명을 매칭한다.

## @Qualifier

빈 등록시에 `@Qualifier("빈 이름")`을 붙여줘서 구분해준다.

```java
@Component
@Qualifier("rateDiscountPolicy")
public class RateDiscountPolicy implements DiscountPolicy {}
```

```java
@Component
@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy {}
```

### @Qualifier 자동 주입 예시

생성자 자동 주입 예시

```java
@Autowired
public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("rateDiscountPolicy") DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
}
```

수정자 자동 주입 예시

```java
@Autowired
public DiscountPolicy setDiscountPolicy(@Qualifier("rateDiscountPolicy")DiscountPolicy discountPolicy) {
    this.discountPolicy = discountPolicy;
}
```

이렇게 @Qualifier로 주입할 때 `@Qualifier("rateDiscountPolicy")`를 못 찾는다면 rateDiscountPolicy의 이름을 가진 빈을 찾는다.

- 하지만 @Qualifier는 @Qualifier를 찾는 용도로만 사용하는 것이 명확하고 좋다.

## @Primary

우선순위를 정하는 방법이다.

- @Autowired 시에 여러 빈이 매칭된다면 @Primary가 우선권을 가진다.

```java
@Component
@Primary
public class RateDiscountPolicy implements DiscountPolicy {}
```

```java
@Component
public class FixDiscountPolicy implements DiscountPolicy {}
```

이렇게 되면 @Primary가 붙은 RateDiscountPolicy가 DiscountPolicy 타입의 빈을 조회할 시에 우선권을 갖게 된다.

```java
//생성자
@Autowired
public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
}
```

```java
//수정자
@Autowired
public DiscountPolicy setDiscountPolicy(DiscountPolicy discountPolicy) {
    this.discountPolicy = discountPolicy;
}
```

## annotation 직접 만들기

`@Qualifier("mainDiscountPolicy")`, 이렇게 문자를 적으면 컴파일시 문자열만 찾고, 타입 체크는 하지 않는다.

- 직접 annotation을 만들어서 해결한다.

```java
package yback.board.annotation;

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

이렇게 직접 annotation을 만들고, 타입이 같아서 의존 관계 주입시에 충돌이 일어나면 우선순위를 부여해줄 클래스에 직접 만든 annotation을 붙여주고, 의존관계를 주입하는 생성자나 수정자의 파라미터의 타입 앞에도 annotation을 붙여준다.

```java
import yback.board.annotation.MainDiscountPolicy;
import yback.board.member.Grade;
import yback.board.member.Member;

@MainDiscountPolicy
@Component
public class RateDiscountPolicy implements DiscountPolicy{

     private int dicountPercent1 = 10;
     private int dicountPercent2 = 20;

     @Override
     public int discount(Member member, int price) {
          if (member.getGrade() == Grade.MANAGER) {
               return price * dicountPercent2 / 100;
          } else if (member.getGrade() == Grade.VIP) {
               return price * dicountPercent1 / 100;
          } else {
               return 0;
          }
     }
}
```

- 우선권을 부여할 스트링 빈에 annotation 추가

```java
@Component
public class OrderServiceImpl implements OrderService{

     private final MemberRepository memberRepository;
     private final DiscountPolicy discountPolicy;

     public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
          this.memberRepository = memberRepository;
          this.discountPolicy = discountPolicy;
     }
}
```

- 생성자 주입시에 파라미터의 타입 앞에 annotation을 붙여준다.
- 수정자도 마찬가지로 파라미터 타입 앞에 annotation을 붙여주면 된다.

# 조회한 빈이 모두 필요할 때 (List or Map)

의도적으로 해당 타입의 스프링 빈이 다 필요한 경우가 있을 수도 있다.

```java
package yback.board.annotation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import yback.board.AutoAppConfig;
import yback.board.discount.DiscountPolicy;
import yback.board.member.Grade;
import yback.board.member.Member;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

     @Test
     void findAllBean() {
          ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
          DiscountService discountService = ac.getBean(DiscountService.class);
          Member member = new Member(1L, "userA", Grade.VIP);
          int discountPrice = discountService.discount(member, 10000, "rateDiscountPolicy");

          Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);
          Assertions.assertThat(discountPrice).isEqualTo(1000);
     }

     static class DiscountService {
          private final Map<String, DiscountPolicy> policyMap;
          private final List<DiscountPolicy> policies;

          public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
               this.policyMap = policyMap;
               this.policies = policies;
               System.out.println("policyMap = " + policyMap);
               System.out.println("policies = " + policies);
          }

          public int discount(Member member, int price, String discountCode) {

               DiscountPolicy discountPolicy = policyMap.get(discountCode);
               System.out.println("discountCode = " + discountCode);
               System.out.println("discountPolicy = " + discountPolicy);

               return discountPolicy.discount(member, price);
          }
     }

}
```

- 로직 분석
  - `DiscountService`는 Map으로 모든 `DiscountPolicy`를 주입받는다.
    - `fixDiscountPolicy`와 `rateDiscountPolicy`가 주입된다.
  - `discount()` 메서드는 `discountCode`로 Map의 key인 String(`rateDiscountPolicy` or `fixDiscountPolicy`)을 파라미터 Map의 value(DiscountPolicy 인터페이스 or DiscountPolicy 인터페이스 구현체)로 받아서 변수 `discountPolicy`에 저장해준다.

- 주입 분석
  - `Map<String, DiscountPolicy>`
    - map의 key에 스프링 빈의 이름을 넣어주고, 그 값으로 DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.
      - DiscountPolicy 인터페이스 or DicountPolicy 인터페이스의 구현체
  - `List<DiscountPolicy>`
    - DiscountPolicy 타입으로 조회한 모든 스프링 빈을 담아준다.

>스프링 컨테이너를 생성하면서, 넘겨지는 파라미터는 스프링 빈으로 자동 등록된다.

**애플리케이션에 광범위하게 영향을 미치는 기술 지원 객체는 수동 빈으로 등록해서 설정 정보에 바로 나타나게 하는 것이 유지보수 하기 좋다.**

```java
@Configuration
public class DiscountPolicyConfig {

    @Bean
    public DiscountPolicy rateDiscountPolicy() {
        return new RateDiscountPolicy();
    }

    @Bean
    public DiscountPolicy fixDiscountPolicy() {
        return new FixDiscountPolicy();
    }
}
```

- 이렇게 설정 정보를 수동으로 만들면, 어떤 빈들이 주입될지 빠르게 파악할 수 있다.

**스프링 부트가 아니라 내가 직접 기술 지원 객체를 스프링 빈으로 등록한다면 수동으로 등록해서 명확하게 드러내는 것이 좋다.**

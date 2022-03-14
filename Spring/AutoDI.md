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
-  @Nullable, Optional은 스프링 전반에 걸쳐서 지원된다. 예를 들어서 생성자 자동 주입에서 특정 필드에만 사용해도 된다

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
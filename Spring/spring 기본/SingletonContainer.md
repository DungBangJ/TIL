# 스프링 없는 순수한 DI 컨테이너 테스트

```java
package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {
     @Test
     @DisplayName("스프링 없는 순수한 DI 컨테이너")
     void pureContainer() {
          AppConfig appConfig = new AppConfig();
          //1. 조회: 호출할 때 마다 객체를 생성
          MemberService memberService1 = appConfig.memberService();
          //2. 조회: 호출할 때 마다 객체를 생성
          MemberService memberService2 = appConfig.memberService();
          //참조값이 다른 것을 확인
          System.out.println("memberService1 = " + memberService1);
          System.out.println("memberService2 = " + memberService2);
          //memberService1 != memberService2
          assertThat(memberService1).isNotSameAs(memberService2);
     }
}
```

- 스프링 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때마다 객체를 새로 생성한다.
    - 메모리 낭비가 심하다.
    - 객체가 하나만 생성되고, 공유하도록 설계해야 한다.
        - 싱글톤 패턴

# 싱글톤 패턴

- 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴
    - 2개 이상 생성하지 못하도록 막는다.
        - private 생성자를 이용해서 new 키워드를 사용하지 못하게 막는다.

```java
package hello.core.singleton;

public class SingletonService {
     //1. static 영역에 객체를 딱 1개만 생성해둔다.
     private static final SingletonService instance = new SingletonService();
     //2. public으로 열어서 객체 인스터스가 필요하면 이 static 메서드를 통해서만 조회하도록 허용한다.

     public static SingletonService getInstance() {
          return instance;
     }

     //3. 생성자를 private으로 선언해서 외부에서 new 키워드를 사용한 객체 생성을 못하게 막는다.
     private SingletonService() {
     }

     public void logic() {
          System.out.println("싱글톤 객체 로직 호출");
     }
}
```

- 생성자를 private으로 막아서 new 방지

싱글톤 패턴을 사용하는 테스트 코드

```java
@Test
@DisplayName("싱글톤 패턴을 적용한 객체 사용")
public void singletonServiceTest(){
        //private으로 생성자를 막아두었다. 컴파일 오류가 발생한다.
        //new SingletonService();
        //1. 조회: 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService1=SingletonService.getInstance();
        //2. 조회: 호출할 때 마다 같은 객체를 반환
        SingletonService singletonService2=SingletonService.getInstance();
        //참조값이 같은 것을 확인
        System.out.println("singletonService1 = "+singletonService1);
        System.out.println("singletonService2 = "+singletonService2);
        // singletonService1 == singletonService2
        assertThat(singletonService1).isSameAs(singletonService2);
        singletonService1.logic();
        }
```

## 싱글톤 패턴의 문제점

싱글톤 패턴 문제점

- 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
- 의존관계상 클라이언트가 구체 클래스에 의존한다. DIP를 위반한다.
- 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
- 테스트하기 어렵다.
- 내부 속성을 변경하거나 초기화 하기 어렵다.
- private 생성자로 자식 클래스를 만들기 어렵다.
- 결론적으로 유연성이 떨어진다.
- 안티패턴으로 불리기도 한다

# 싱글톤 컨테이너

스프링 컨테이너는 싱글톤 패턴을 적요하지 않아도, 객체 인스턴스를 싱글톤으로 관리한다.

- 컨테이너는 객체를 하나만 생성해서 관리한다.
- 싱글톤 패턴을 위한 지저분한 코드가 들어가지 않아도 된다.

스프링 컨테이너를 사용하는 테스트 코드

```java
@Test
@DisplayName("스프링 컨테이너와 싱글톤")
void springContainer(){
        ApplicationContext ac=new
        AnnotationConfigApplicationContext(AppConfig.class);
        //1. 조회: 호출할 때 마다 같은 객체를 반환
        MemberService memberService1=ac.getBean("memberService",
        MemberService.class);
        //2. 조회: 호출할 때 마다 같은 객체를 반환
        MemberService memberService2=ac.getBean("memberService",
        MemberService.class);
        //참조값이 같은 것을 확인
        System.out.println("memberService1 = "+memberService1);
        System.out.println("memberService2 = "+memberService2);
        //memberService1 == memberService2
        assertThat(memberService1).isSameAs(memberService2);
        }
```

- 요청이 올 때마다 객체를 생성하지 않고, 이미 만들어진 객체를 공유해서 효율적으로 재사용 가능
- 스프링의 기본 빈 등록 방식은 싱글톤이지만, 싱글톤 방식만 지원하는 것은 아니다.
    - 요청할 때마다 새로운 객체를 생성해서 반환하는 기능도 제공한다.

## 싱글톤 방식의 주의점

싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤 방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(
stateful)하게 설계하면 안된다.

- 무상태(stateless)로 설계해야 한다.
    - 특정 클라이언트에 의존적인 필드가 있으면 안된다.
    - 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다!
    - 가급적 읽기만 가능해야 한다.
    - 필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.

## @Configuration과 바이트코드 조작의 마법

```java
@Test
void configurationDeep(){
        ApplicationContext ac=new
        AnnotationConfigApplicationContext(AppConfig.class);
        //AppConfig도 스프링 빈으로 등록된다.
        AppConfig bean=ac.getBean(AppConfig.class);

        System.out.println("bean = "+bean.getClass());
        //출력: bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$bd479d70
        }
```

AnnotationConfigApplicationContext의 파라미터로 넘긴 값은 스프링 빈으로 등록된다.

- AppConfig도 스프링 빈이 된다.
- AppConfig 스프링 빈을 조회

```java
bean=

class hello.core.AppConfig$$EnhancerBySpringCGLIB$$bd479d70
```

- 스프링이 CGLIB라는 바이트코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다.
    - 그 임의의 다른 클래스가 싱글톤이 보장되도록 해준다.

![5](./assets/SingletonContainer-1647240776304.png)

AppConfig@CGLIB 예상 코드

```java
@Bean
public MemberRepository memberRepository(){

        if(memoryMemberRepository가 이미 스프링 컨테이너에 등록되어 있으면?){
        return 스프링 컨테이너에서 찾아서 반환;
        }else{ //스프링 컨테이너에 없으면
        기존 로직을 호출해서 MemoryMemberRepository를 생성하고 스프링 컨테이너에 등록
        return 반환
        }
}
```

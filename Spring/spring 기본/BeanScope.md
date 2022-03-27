# 빈 스코프

빈이 존재할 수 있는 범위

스프링의 다양한 스코프 지원

- 싱글톤
    - 기본 스코프
    - 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프
    - 싱글톤 스코프의 빈을 조회하면 스프링 컨테이너는 항상 같은 인스턴스의 스프링 빈을 반환한다.
- 프로토타입
    - 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는다.
        - 프로토타입 빈은 스프링 컨테이너가 종료될 때 종료 메서드가 전혀 실행되지 않는다.
    - 매우 짧은 범위의 스코프
    - 프로토타입 스코프를 스프링 컨테이너에 조회하면 스프링 컨테이너는 항상 새로운 인스턴스를 생성해서 반환한다.
- 웹 관련 스코프
    - request
        - 웹 요청이 들어오고 나갈때까지 유지되는 스코프
    - session
        - 웹 세션이 생성되고 종료될 때까지 유지되는 스코프
    - application
        - 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프

빈 스코프 지정 방법

- 컴포넌트 스캔 자동 등록

```java

@Scope("prototype")
@Component
public class HelloBean {
}
```

- 수동 등록

```java
@Scope("prototype")
@Bean
PrototypeBean HelloBean(){
        return new HelloBean();
        }
```

## 싱글톤 스코프 빈 테스트

```java
import org.junit.jupiter.api.Test;
import
        org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {
     @Test
     public void singletonBeanFind() {
          AnnotationConfigApplicationContext ac = new
                  AnnotationConfigApplicationContext(SingletonBean.class);
          SingletonBean singletonBean1 = ac.getBean(SingletonBean.class);
          SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);
          System.out.println("singletonBean1 = " + singletonBean1);
          System.out.println("singletonBean2 = " + singletonBean2);
          assertThat(singletonBean1).isSameAs(singletonBean2);
          ac.close(); //종료
     }

     @Scope("singleton")
     static class SingletonBean {
          @PostConstruct
          public void init() {
               System.out.println("SingletonBean.init");
          }

          @PreDestroy
          public void destroy() {
               System.out.println("SingletonBean.destroy");
          }
     }
}

     <실행결과>
             SingletonBean.init
             singletonBean1 = hello.core.scope.PrototypeTest$SingletonBean@54504ecd
        singletonBean2=hello.core.scope.PrototypeTest$SingletonBean@54504ecd
        org.springframework.context.annotation.AnnotationConfigApplicationContext-
        Closing SingletonBean.destroy
```

- 같은 인스턴스의 빈 조회

## 프로토타입 스코프 빈 테스트

```java
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class PrototypeTest {
     @Test
     public void prototypeBeanFind() {
          AnnotationConfigApplicationContext ac = new
                  AnnotationConfigApplicationContext(PrototypeBean.class);
          System.out.println("find prototypeBean1");
          PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
          System.out.println("find prototypeBean2");
          PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
          System.out.println("prototypeBean1 = " + prototypeBean1);
          System.out.println("prototypeBean2 = " + prototypeBean2);
          assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
          ac.close(); //종료
     }

     @Scope("prototype")
     static class PrototypeBean {
          @PostConstruct
          public void init() {
               System.out.println("PrototypeBean.init");
          }

          @PreDestroy
          public void destroy() {
               System.out.println("PrototypeBean.destroy");
          }
     }
}

     <실행 결과>
             find prototypeBean1
PrototypeBean.init
        find prototypeBean2
        PrototypeBean.init
        prototypeBean1=hello.core.scope.PrototypeTest$PrototypeBean@13d4992d
        prototypeBean2=hello.core.scope.PrototypeTest$PrototypeBean@302f7971
        org.springframework.context.annotation.AnnotationConfigApplicationContext-
        Closing
```

- 프로토타입 스코프 빈
    - 스프링 컨테이너에서 **빈을 조회**할 때 생성되고, 초기화 메서드도 실행된다.
- 싱글톤 빈
    - 스프링 컨테이너 **생성** 시점에 초기화 메서드 실행

# 싱글톤에서 프로토타입 빈 사용

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import
        org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {
     @Test
     void singletonClientUsePrototype() {
          AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
          ClientBean clientBean1 = ac.getBean(ClientBean.class); //ClientBean을 스프링 컨테이너에 요청해서 받는다.
          //ClientBean은 싱글톤
          int count1 = clientBean1.logic();
          assertThat(count1).isEqualTo(1);

          ClientBean clientBean2 = ac.getBean(ClientBean.class);
          int count2 = clientBean2.logic();
          assertThat(count2).isEqualTo(2);
     }

     static class ClientBean {
          private final PrototypeBean prototypeBean; //1. 의존관계 자동 주입 시점에 스프링 컨테이너에 프로토타입 빈을 요청
          //3. ClientBean은 프로토타입 빈을 내부 필드에 보관(참조값을 보관)

          @Autowired
          public ClientBean(PrototypeBean prototypeBean) {
               this.prototypeBean = prototypeBean;
          }

          public int logic() {
               prototypeBean.addCount();
               int count = prototypeBean.getCount();
               return count;
          }
     }

     @Scope("prototype")
     static class PrototypeBean { //2. 스프링 컨테이너는 프로토타입 빈을 생성해서 ClientBean에 반환
          private int count = 0;

          public void addCount() {
               count++;
          }

          public int getCount() {
               return count;
          }

          @PostConstruct
          public void init() {
               System.out.println("PrototypeBean.init " + this);
          }

          @PreDestroy
          public void destroy() {
               System.out.println("PrototypeBean.destroy");
          }
     }
}
```

- clientBean이 내부에 가지고 있는 프로토타입 빈은 이미 과거에 주입이 끝난 빈
    - 주입 시점에 스프링 컨테이너에 요청해서 프로토타입 빈이 새로 생성된 것이므로, 더 이상 생성되지 않는다.
- 스프링은 일반적으로 싱글톤 빈을 사용하므로, 싱글톤 빈이 프로토타입 빈을 사용하게 된다.

# 프로토타입 스코프 - 싱글톤 빈과 함께 사용시 Provider로 문제 해결

```java
public class PrototypeProviderTest {
     @Test
     void providerTest() {
          AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
          ClientBean clientBean1 = ac.getBean(ClientBean.class);
          int count1 = clientBean1.logic();
          assertThat(count1).isEqualTo(1);

          ClientBean clientBean2 = ac.getBean(ClientBean.class);
          int count2 = clientBean2.logic();
          assertThat(count2).isEqualTo(1);
     }

     static class ClientBean {
          @Autowired
          private ApplicationContext ac;

          public int logic() {
               PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);//항상 새로운 프로토타입 빈이 생성된다.
               prototypeBean.addCount();
               int count = prototypeBean.getCount();
               return count;
          }
     }

     @Scope("prototype")
     static class PrototypeBean {
          private int count = 0;

          public void addCount() {
               count++;
          }

          public int getCount() {
               return count;
          }

          @PostConstruct
          public void init() {
               System.out.println("PrototypeBean.init " + this);
          }

          @PreDestroy
          public void destroy() {
               System.out.println("PrototypeBean.destroy");
          }
     }
}
```

- 의존관계를 외부에서 주입(DI) 받는게 아니라 이렇게 직접 필요한 의존관계를 찾는 것을 Dependenct Lookup(DL) 의존 관계 조회(탐색)라고 한다.

## ObjectFactory, ObjectProvider

지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스를 제공

```java
@Autowired
private ObjectProvider<PrototypeBean> prototypeBeanProvider;
public int logic(){
        PrototypeBean prototypeBean=prototypeBeanProvider.getObject();
        prototypeBean.addCount();
        int count=prototypeBean.getCount();
        return count;
        }
```

- prototypeBeanProvide.getObject()을 통해서 항상 새로운 프로토타입 빈이 생성된다.
    - ObjectProvider의 getObject()를 호출하면 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아서 반환한다.(DL)

# 웹 스코프

- 웹 환경에서만 동작한다.
- 스프링이 해당 스코프의 종료시점까지 관리한다.

## request 스코프 예제 만들기

웹 환경을 추가해야한다.

builde.gradle에 추가

```
//web 라이브러리 추가
implementation 'org.springframework.boot:spring-boot-starter-web'
```

- spring-boot-starter-web 라이브러리를 추가하면 스프링 부트는 내장 톰켓 서버를 활용해서 웹 서버와 스프링을 함께 실행시킨다

만약 기본 포트인 8080 포트를 다른곳에서 사용중이어서 오류가 발생하면 포트를 변경해야 한다. 9090 포트로 변경하려면 다음 설정을 추가하자.

main/resources/application.properties

```
server.port=9090
```

MyLogger

```java
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request")
public class MyLogger { //request 스코프로 지정
     private String uuid;
     private String requestURL;

     public void setRequestURL(String requestURL) {
          this.requestURL = requestURL;
     }

     public void log(String message) {
          System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
     }

     @PostConstruct
     public void init() {
          uuid = UUID.randomUUID().toString();
          System.out.println("[" + uuid + "] request scope bean create:" + this);
     }

     @PreDestroy
     public void close() {
          System.out.println("[" + uuid + "] request scope bean close:" + this);
     }
}
```

- request 스코프로 지정하면, 빈이 HTTP 요청 당 하나씩 생성되고, HTTP 요청이 끝나는 시점에 소멸된다.
    - 요청 당 하나씩 빈이 생성되므로, uuid를 저장해두면 다른 HTTP 요청과 구분할 수 있다.

LogDemoController

```java
import hello.core.common.MyLogger;
import hello.core.logdemo.LogDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
     private final LogDemoService logDemoService;
     private final MyLogger myLogger;

     @RequestMapping("log-demo")
     @ResponseBody
     public String logDemo(HttpServletRequest request) {
          String requestURL = request.getRequestURL().toString();
          myLogger.setRequestURL(requestURL);
          myLogger.log("controller test");
          logDemoService.logic("testId");
          return "OK";
     }
}
```

- 로거가 잘 작동하는지 확인하는 테스트용 컨트롤러
- 여기서 받은 requestURL 값을 myLogger에 저장해둔다.
    - myLogger는 HTTP 요청 당 각각 구분되므로 섞이지 않는다.

LogDemoService

```java
import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {
     private final MyLogger myLogger;

     public void logic(String id) {
          myLogger.log("service id = " + id);
     }
}
```

- request scope를 사용하지 않고 파라미터로 모든 정보를 서비스 계층에 넘긴다면, 파라미터가 지저분해진다.
- 웹과 관련된 부분은 컨트롤러까지만 사용해야 한다.
- 서비스 계층은 웹 기술에 종속되지 않고, 가급적 순수하게 유지하는 것이 유지보수 관점에서 좋다.

이렇게 코드를 짜면 오류가 발생하는데 스프링 애플리케이션을 실행하는 시점에 싱글톤 빈은 생성해서 주입이 가능하지만, request 스코프 빈은 아직 생성되지 않는다.

- 이 빈은 실제 요청이 와야 생성된다.

## 스코프와 Provider

LogDemoController

```java
import hello.core.common.MyLogger;
import hello.core.logdemo.LogDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
     private final LogDemoService logDemoService;
     private final ObjectProvider<MyLogger> myLoggerProvider;

     @RequestMapping("log-demo")
     @ResponseBody
     public String logDemo(HttpServletRequest request) {
          String requestURL = request.getRequestURL().toString();
          MyLogger myLogger = myLoggerProvider.getObject();
          myLogger.setRequestURL(requestURL);
          myLogger.log("controller test");
          logDemoService.logic("testId");
          return "OK";
     }
}
```

LogDemoService

```java
import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {
     private final ObjectProvider<MyLogger> myLoggerProvider;

     public void logic(String id) {
          MyLogger myLogger = myLoggerProvider.getObject();
          myLogger.log("service id = " + id);
     }
}
```

- ObjectProvider 덕분에 ObjectProvider.getObject()를 호출하는 시점까지 request scope 빈의 생성을 지연할 수 있다.
    - getObject()를 호출하는 시점에 HTTP 요청이 진행중이라서 request scope 빈의 생성이 정상 처리된다.
    - 여러 번의 getObject() 호출이어도 같은 HTTP 요청이면 같은 스프링 빈이 반환된다.

## 스코프와 프록시

```java

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {
}
```

- `proxyMode = ScopedProxyMode.TARGET_CLASS`
    - 적용대상이 클래스면 TARGET_CLASS
    - 적용대상이 인터페이스면 INTERFACES
- 이 방식은 MyLogger의 가짜 프록시 클래스를 만들어두고 HTTP request와 상관없이 가짜 프록시 클래스를 다른 빈에 미리 주입해 둘 수 있다.

### 웹 스코프와 프록시 동작 원리

먼저 주입된 MyLogger를 확인해보자

```java
System.out.println("myLogger = "+myLogger.getClass());

<출력결과>
myLogger=class hello.core.common.MyLogger$$EnhancerBySpringCGLIB$$b68b726d
```

- CGLIB라는 라이브러리로 내 클래스를 상속 받은 가짜 프록시 객체를 만들어서 주입한다.
    - @Scope의 proxyMode = ScopedProxyMode.TARGET_CLASS를 설정하면 스프링 컨테이너는 CGLIB라는 바이트코드를 조작하는 라이브러리를 이용해서, MyLogger를 상속받은
      가짜 프록시 객체를 생성한다. 이름: "myLogger"
    - ac.getBean("myLogger", MyLogger.class)로 조회해도 프록시 객체가 조회된다.
    - 의존관계 주입도 이 가짜 프록시 객체가 주입된다.
- 가짜 프록시 객체는 요청이 오면 그때 내부에서 진짜 빈을 요청하는 위임 로직을 호출한다.

다른 코드들은 Provider 전으로 작성해야 한다.

LogDemoController

```java
import hello.core.common.MyLogger;
import hello.core.logdemo.LogDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {
     private final LogDemoService logDemoService;
     private final MyLogger myLogger;

     @RequestMapping("log-demo")
     @ResponseBody
     public String logDemo(HttpServletRequest request) {
          String requestURL = request.getRequestURL().toString();
          myLogger.setRequestURL(requestURL);
          myLogger.log("controller test");
          logDemoService.logic("testId");
          return "OK";
     }
}
```

LogDemoService

```java
import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {
     private final MyLogger myLogger;

     public void logic(String id) {
          myLogger.log("service id = " + id);
     }
}
```

# 빈 생명주기 콜백

예제 코드

```java
package yback.board.lifecycle;

public class NetworkClient {
     private String url;

     public NetworkClient() {
          System.out.println("생성자 호출, url = " + url);
          connect();
          call("초기화 연결 메시지");
     }

     public void setUrl(String url) {
          this.url = url;
     }

     public void connect() {
          System.out.println("connect: " + url);
     }

     public void call(String message) {
          System.out.println("call: " + url + ", message = " + message);
     }

     public void disconnect() {
          System.out.println("close: " + url);
     }
}
```

스프링 환경설정과 실행

```java
package yback.board.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifecycleTest {

     @Test
     public void lifeCycleTest() {
          ConfigurableApplicationContext cac = new AnnotationConfigReactiveWebApplicationContext(LifeCycleConfig.class);
          NetworkClient client = cac.getBean(NetworkClient.class);
          cac.close();
     }

     @Configuration
     static class LifeCycleConfig {

          @Bean
          public NetworkClient networkClient() {
               NetworkClient networkClient = new NetworkClient();
               networkClient.setUrl("http://hello-spring.dev");
               return networkClient;
          }
     }
}
```

Test output

```
생성자 호출, url = null
connect: null
call null, message = 초기화 연결 메시지
```

분명 테스트 코드에 빈 생성시 `networkClient.setUrl("http://hello-spring.dev")`로 url이 저장되기를 기대했지만, 기대와는 달리 url이 저장되지 않았다.

- 객체를 생성할 단계에는 url이 없고, 객체를 생성한 다음에 외부에서 수정자 주입을 통해서 `setUrl()`이 호출되어야 url이 존재하는 것이다.

스프링 빈의 간단한 라이프 사이클

- 객체 생성 -> 의존관계 주입
  - 스프링 빈은 객체를 생성하고 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가 완료된다.
  - 스프링은 의존관계 주입이 완료되면 스프링 빈에게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공한다.

스프링 빈의 이벤트 라이프사이클

- 스프링 컨테이너 생성
- 스프링 빈 생성
- 의존관계 주입
- 초기화 콜백
  - 빈이 생성되고, 빈의 의존과계 주입이 완료된 후 호출
- 사용
- 소멸전 콜백
  - 빈이 소멸되기 직전에 호출
- 스프링 종료

>객체의 생성과 초기화를 분리하는게 좋다.
>생성자는 설정 정보를 파라미터로 받고, 메모리를 할당해서 객체를 생성하는 책임을 가진다. 반면 초기화는 이렇게 생성된 값들을 활용해서 외부 커넥션을 연결하는등 무거운 동작을 수행한다. 그래서 나누는 것이 좋다.

## 생명주기 콜백 3가지 방법

### 인터페이스 (InitializingBean, DisposableBean)

```java
package yback.board.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient implements InitializingBean, DisposableBean {
     private String url;

     public NetworkClient() {
          System.out.println("생성자 호출, url = " + url);
     }

     public void setUrl(String url) {
          this.url = url;
     }

     public void connect() {
          System.out.println("connect: " + url);
     }

     public void call(String message) {
          System.out.println("call: " + url + ", message = " + message);
     }

     public void disconnect() {
          System.out.println("close: " + url);
     }

     @Override
     public void afterPropertiesSet() throws Exception {
          connect();
          call("초기화 연결 메시지");
     }

     @Override
     public void destroy() throws Exception {
          disconnect();
     }

}
```

`InitializingBean`, `DisposableBean` 인터페이스를 구현하게 `NetworkClient` 코드를 수정한다.

- `afterPropertiesSet()`은 `InitializingBean` 인터페이스의 미완성 메서드
  - 초기화 지원
- `destroy()`은 `DisposableBean` 인터페이스의 미완성 메서드
  - 소멸 지원

다시 Test 코드를 돌려보자.
Test output

```
생성자 호출, url = null
connect: http://hello-spring.dev
call: http://hello-spring.dev, message = 초기화 연결 메시지
19:14:59.342 [main] DEBUG org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext - Closing org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext@26e356f0, started on Mon Sep 26 19:14:59 KST 2022
close: http://hello-spring.dev
```

- 출력 결과를 보면 초기화 메서드가 주입 완료 후에 제대로 호출된 것을 확인할 수 있다.
- 스프링 컨테이너의 종료가 호출되면 소멸 메서드가 제대로 호출되는 것을 확인할 수 있다.

초기화, 소멸 인터페이스 단점

- 이 인터페이스는 스프링 전용
- 이름 변경 불가능

초기화, 소멸 인터페이스는 초창기에 나온 방법들이므로, 요즘은 거의 사용하지 않는다.

### 빈 등록 초기화, 소멸 메서드 지정

설정 정보에 `@Bean(initMethod = "init", destroyMethod = "close")`처럼 초기화 , 소멸 메서드를 지정할 수 있다.

```java
package yback.board.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {
     private String url;

     public NetworkClient() {
          System.out.println("생성자 호출, url = " + url);
     }

     public void setUrl(String url) {
          this.url = url;
     }

     public void connect() {
          System.out.println("connect: " + url);
     }

     public void call(String message) {
          System.out.println("call: " + url + ", message = " + message);
     }

     public void disconnect() {
          System.out.println("close: " + url);
     }

     public void init() {
          System.out.println("NetworkClient.init");
          connect();
          call("초기화 연결 메시지");
     }
     
     public void clos() {
          System.out.println("NetworkClient.close");
          disconnect();
     }
}
```

```java
package yback.board.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifecycleTest {

     @Test
     public void lifeCycleTest() {
          ConfigurableApplicationContext cac = new AnnotationConfigReactiveWebApplicationContext(LifeCycleConfig.class);
          NetworkClient client = cac.getBean(NetworkClient.class);
          cac.close();
     }

     @Configuration
     static class LifeCycleConfig {

          @Bean(initMethod = "init", destroyMethod = "close")
          public NetworkClient networkClient() {
               NetworkClient networkClient = new NetworkClient();
               networkClient.setUrl("http://hello-spring.dev");
               return networkClient;
          }
     }
}
```

설정 정보 사용 특징

- 메서드 이름을 자유롭게 지을 수 있다.
- 스프링 빈이 스프링 코드에 의존하지 않는다.
- 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.

### 애노테이션 @PostConstruct, @PreDestroy

```java
package yback.board.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {
     private String url;

     public NetworkClient() {
          System.out.println("생성자 호출, url = " + url);
     }

     public void setUrl(String url) {
          this.url = url;
     }

     public void connect() {
          System.out.println("connect: " + url);
     }

     public void call(String message) {
          System.out.println("call: " + url + ", message = " + message);
     }

     public void disconnect() {
          System.out.println("close: " + url);
     }

     @PostConstruct
     public void init() {
          System.out.println("NetworkClient.init");
          connect();
          call("초기화 연결 메시지");
     }

     @PreDestroy
     public void close() {
          System.out.println("NetworkClient.close");
          disconnect();
     }
}
```

```java
package yback.board.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifecycleTest {

     @Test
     public void lifeCycleTest() {
          ConfigurableApplicationContext cac = new AnnotationConfigReactiveWebApplicationContext(LifeCycleConfig.class);
          NetworkClient client = cac.getBean(NetworkClient.class);
          cac.close();
     }

     @Configuration
     static class LifeCycleConfig {

          @Bean
          public NetworkClient networkClient() {
               NetworkClient networkClient = new NetworkClient();
               networkClient.setUrl("http://hello-spring.dev");
               return networkClient;
          }
     }
}
```

- 그냥 초기화, 종료 메서드에 @PostConstruct, @Predestroy 애노테이션을 붙이면 가장 편리하게 초기화와 종료를 실행할 수 있다.
  - 최신 스프링에서 가장 권장하는 방법
  - 자바 표준이므로 스프링이 아닌 다른 컨테이너에서도 동작한다.
  - 컴포넌트 스캔과 잘 어울린다.

코드를 고칠 수 없는 외부 라이브러리를 초기화, 종료해야 하면 @Bean의 initMethod, destroyMethod를 사용한다.
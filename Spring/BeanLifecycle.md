# 빈 생명주기 콜백 시작

데이터베이스 커넥션 풀이나, 네트워크 소켓처럼 애플리케이션 시작 시점에 필요한 연결을 미리 해두고, 애플리케이션 종료

스프링 빈의 라이프 사이클

- 객체 생성 -> 의존관계 주입
- 초기화 작업은 의존관계 주입이 모두 완료되고 호출해야 한다.
- 스프링은 의존관계 주입이 완료되면 스프링 비넹게 콜백 메서드를 통해서 초기화 시점을 알려주는 다양한 기능을 제공한다.
- 스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다.
- 스프링 빈의 전체 이벤트 라이프 사이클
    - 스프링 컨테이너 생성
    - 스프링 빈 생성
    - 의존관계 주입
    - 초기화 콜백
    - 사용
    - 소멸 전 콜백
    - 스프링 종료

객체의 생성과 초기화를 분리하는 것이 좋다.

# 빈 생명주기 콜백

- 인터페이스(InitializationBean, DisposableBean)
- 설정 정보에 초기화 메서드, 종료 메서드 지정
- @PostConstruct, @PreDestroy 애노테이션 지원

## 인터페이스 InitializationBean, DisposableBean

- InitializationBean
    - afterPropertiesSet() 메서드로 초기화를 진행한다.
- DisposableBean
    - detroy() 메서드로 소멸을 지원한다.

```java
package hello.core.lifecycle;

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

     //서비스 시작시 호출
     public void connect() {
          System.out.println("connect: " + url);
     }

     public void call(String message) {
          System.out.println("call: " + url + " message = " + message);
     }

     //서비스 종료시 호출
     public void disConnect() {
          System.out.println("close + " + url);
     }

     @Override
     public void afterPropertiesSet() throws Exception {
          connect();
          call("초기화 연결 메시지");
     }

     @Override
     public void destroy() throws Exception {
          disConnect();
     }
}
```

초기화, 소멸 인터페이스 단점

- 스프링 전용 인터페이스
- 메서드의 이름을 변경할 수 없다.
- 외부 라이브러리에 적용할 수 없다.

## 빈 등록 초기화, 소멸 메서드 지정

설정 정보에 `@Bean(initMethod = "init", detroyMethod = "close")` 처럼 초기화, 소멸 메서드를 지정할 수 있다.

설정 정보를 사용하도록 변경

```java
public class NetworkClient {
     private String url;

     public NetworkClient() {
          System.out.println("생성자 호출, url = " + url);
     }

     public void setUrl(String url) {
          this.url = url;
     }

     //서비스 시작시 호출
     public void connect() {
          System.out.println("connect: " + url);
     }

     public void call(String message) {
          System.out.println("call: " + url + " message = " + message);
     }

     //서비스 종료시 호출
     public void disConnect() {
          System.out.println("close + " + url);
     }

     public void init() {
          System.out.println("NetworkClient.init");
          connect();
          call("초기화 연결 메시지");
     }

     public void close() {
          System.out.println("NetworkClient.close");
          disConnect();
     }
}
```

설정 정보에 초기화 소멸 메서드 지정

```java

@Configuration
static class LifeCycleConfig {
     @Bean(initMethod = "init", destroyMethod = "close")
     public NetworkClient networkClient() {
          NetworkClient networkClient = new NetworkClient();
          networkClient.setUrl("http://hello-spring.dev");
          return networkClient;
     }
}
```

설정 정보 사용 특징

- 메서드 이름 자유
- 스프링 빈이 스프링 코드에 의존하지 않는다.
- 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있다.

## 애노테이션 @PostContruct, @PreDestroy

```java
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

     //서비스 시작시 호출
     public void connect() {
          System.out.println("connect: " + url);
     }

     public void call(String message) {
          System.out.println("call: " + url + " message = " + message);
     }

     //서비스 종료시 호출
     public void disConnect() {
          System.out.println("close + " + url);
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
          disConnect();
     }
}
```

```java

@Configuration
static class LifeCycleConfig {
     @Bean
     public NetworkClient networkClient() {
          NetworkClient networkClient = new NetworkClient();
          networkClient.setUrl("http://hello-spring.dev");
          return networkClient;
     }
}
```

@PreConstruct, @PreDestroy 애노테이션 특징
- 애노테이션 하나만 붙이면 된다.
- 최신 스프링에서 가장 권장하는 방법
- 외부 라이브러리에 적용하지 못한다.
  - 외부 라이브러리를 초기화, 종료해야 하면 @Bean의 initMethod, destroyMethod를 사용하자.
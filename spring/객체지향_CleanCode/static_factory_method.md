# 정적 팩토리 메서드

클래스의 인스턴스를 만들 수 있는 방법
- public 생성자의 쓰임을 줄이자.

## 장점

### 이름을 가질 수 있다

이름을 가지게 되면 생성자 생성의 목적을 표현할 수 있어 가독성이 증가한다.

```java
MyClass<Integer> list1 = new MyClass<>();
MyClass<Integer> list2 = MyClass.createInstance();

```

### 객체 생성 제한이 가능하다

객체 생성에 제한을 걸거나, 불변 객체를 매번 반환하는 등의 요령을 쓸 수 있다.

### SubClass(자식 클래스)를 반환할 수 있다

특정 버전의 class 정적 팩토리 메서드가 클래스 A를 반환하다가 A의 하위 클래스인 B를 반환하더라도 Client 코드에서는 그걸 신경쓰지 않고 작업이 가능하다.

- 기본적인 생성자를 사용한다면 구현체가 바뀌는 것이기 때문에 Client 코드를 변경해야 한다.

### 입력 매개변수에 따라 다른 타입으로 반환 가능하다.

반환 타입이 하위 타입이기만 하면 어떤 클래스의 객체를 반환하든 상관없다.

### 정적 팩토리 메서드를 작성하는 시점에는 반환할 객체 클래스가 존재하지 않아도 된다.

```java
class Car {
    public static Car newTruck() {
        Class<?> truckClz = Class.forName("me.javarouka.vehicle.Truck");
        return truckClz.newInstance();
    }

    public static Car newBus() {
        Class<?> busClz = Class.forName("me.javarouka.vehicle.Bus");
        return busClz.newInstance();
    }
} 
```

위 코드처럼 처음 클래스가 로더에 존재하지 않아도 동적으로 현재 코드가 실행되는 클래스 로더에 해당 클래스들을 로딩하고 그 클래스들의 인스턴스를 생성해서 반환한다.

## 단점

1. 상속을 하려면 public이나 protected 생성자가 필요하다.
   - 정적 팩토리 메서드만 제공하면 상속이 불가능한다.

2. 정적 팩토리 메소드는 프로그래머가 찾기 어렵다.
   - 정적 팩토리 메소드에서 흔히 사용하는 명명방식

```
from : 매개변수를 하나 받아서 타입의 인스턴스를 반환하는 형변환 메소드

of : 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메소드

valueOf : from과 of의 자세한 버전

getInstance : 매개변수로 명시한 인스턴스를 반환하지만 같은 인스턴스임을 보장하지 않는다.

newInstance : 매번 새로운 객체를 반환
```
- [LinkedList](#linkedlist)
  - [Linkedlist 선언](#linkedlist-선언)
  - [LinkedList 값 삭제](#linkedlist-값-삭제)
  - [LinkedList 크기 구하기](#linkedlist-크기-구하기)
  - [LinkedList 값 출력](#linkedlist-값-출력)
  - [LinkedList 값 검색](#linkedlist-값-검색)

# LinkedList

- ArrayList와 다르게 LinkedList는 각각의 노드를 연결하는 방식을 사용한다.

![](https://images.velog.io/images/disambur23/post/011d37e7-2922-41b9-baf7-c01116807384/image.png)

- LinkedList는 각 노드가 데이터와 포인터를 가지고 한 줄로 연결되어 있는 방식의 자료구조이다.
- 데이터를 담고 있는 노드들이 연결되어 있고, 노드의 포인터가 이전 노드와 다음 노드와의 연결을 담당한다.
- Node는 LinkedList에 객체를 추가하거나 삭제하면 앞뒤 링크만 변경되고 나머지 링크는 변경되지 않는다.
- 중간에 데이터를 추가나 삭제하더라도 전체의 인덱스가 한 칸씩 뒤로 밀리거나 당겨지는 일이 없기에 ArrayList에 비해서 데이터의 추가나 삭제가 용이하다.
  - 하지만 인덱스가 없기에 특정 요소에 접근하기 위해서는 순차 탐색이 필요로 하여 탐색 속도가 떨어진다는 단점이 있어 이럴 때에는 ArrayList를 활용한다.

![](https://images.velog.io/images/disambur23/post/0ba2a686-fe91-442d-b43c-8c10c057def0/image.png)

## Linkedlist 선언

```java
LinkedList list = new LinkedList();//타입 미설정 Object로 선언된다.
LinkedList<Student> members = new LinkedList<Student>();//타입설정 Student객체만 사용가능
LinkedList<Integer> num = new LinkedList<Integer>();//타입설정 int타입만 사용가능
LinkedList<Integer> num2 = new LinkedList<>();//new에서 타입 파라미터 생략가능
LinkedList<Integer> list2 = new LinkedList<Integer>(Arrays.asList(1,2));//생성시 값추가
```

 LinkedList의 생성은 ArrayList와 크게 다르지 않다. 일반적으로 new LinkedList<>()와 같이 타입을 생략한 형태로 초기화를 진행한다. ArrayList와 다른 점은 가용량이 의미가 없기 때문에 가용량을 받는 생성자가 존재하지 않는다는 점이다. 즉, LinkedList 선언은 ArrayList선언 방식과 같지만 LinkedList에서는 초기의 크기를 미리 생성할수는 없다는 것이다.
 <br/>
 LinkedList를 생성할 때 LinkedList에 사용 타입을 명시해주는 것이 좋다. JDK 5.0 이후부터 자료형의 안정성을 위해 제너릭스(Generics)라는 개념이 도입되었는데, LinkedList<Integer> list = new LinkedList<Integer>();라면 int만 사용할 수 있고 다른 타입은 사용이 불가능한 것을 뜻한다.

> ※제네릭스는 선언할 수 있는 타입이 객체 타입입니다. int는 기본자료형이기 때문에 들어갈  수 없으므로 int를 객체화시킨 wrapper클래스를 사용해야 한다.

## LinkedList 값 삭제

```java
LinkedList<Integer> list = new LinkedList<Integer>(Arrays.asList(1,2,3,4,5));
list.removeFirst(); //가장 앞의 데이터 제거
list.removeLast(); //가장 뒤의 데이터 제거
list.remove(); //생략시 0번째 index제거
list.remove(1); //index 1 제거
list.clear(); //모든 값 제거
```

LinkedList에 값을 제거하는 방법도 값을 추가하는것과 비슷하다. removeFirst() 메소드를 사용하면 가장 첫 데이터가 removeLast()를 사용하면 가장 뒤에 있는 데이터가 삭제되고, remove(index, value)를 사용하여 특정 index의 값을 제거할 수도 있다. 값을 전부 제거하려면 clear()메소드를 사용한다.
  
## LinkedList 크기 구하기

  ```java
LinkedList<Integer> list = new LinkedList<Integer>(Arrays.asList(1,2,3));
System.out.println(list.size()); //list 크기 : 3
```

  LinkedList의 크기를 구하려면 LinkedList의 size() 메소드를 사용하면 된다.
  
## LinkedList 값 출력

  ```java
LinkedList<Integer> list = new LinkedList<Integer>(Arrays.asList(1,2,3));

System.out.println(list.get(0));//0번째 index 출력
    
for(Integer i : list) { //for문을 통한 전체출력
    System.out.println(i);
}
  
System.out.println(list) //그냥 list 전체를 출력

Iterator<Integer> iter = list.iterator(); //Iterator 선언 
while(iter.hasNext()){//다음값이 있는지 체크
    System.out.println(iter.next()); //값 출력
}
```
  
LinkedList의 get(index) 메소드를 사용하면 LinkedList의 원하는 index의 값이 리턴된다. 전체 출력은 대부분 for문을 통해서 출력을 하고 Iterator를 사용해서 출력을 할 수도 있다. LinkedList의 경우 인덱스를 사용하여 연산을 수행할 수 있도록 get(index) 메소드를 제공하지만, 메소드 내부의 동작은 순차 탐색으로 이루어져 있어 ArrayList의 get(index)메서드보다 속도가 느리다.
  
## LinkedList 값 검색

```java
ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3));
System.out.println(list.contains(1)); //list에 1이 있는지 검색 : true
System.out.println(list.indexOf(1)); //1이 있는 index반환 없으면 -1
```

LinkedList에서 찾고자 하는 **값을 검색**하려면 LinkedList의 contains(value) 메소드를 사용하면 됩니다. 만약 값이 있다면 true가 리턴되고 값이 없다면 false가 리턴됩니다. 값이 있는 **index를 찾으려면** indexOf(value) 메소드를 사용하면 되고 만약 값이 없다면 -1을 리턴합니다.  
  
  참조: <https://coding-factory.tistory.com/552>, <https://psychoria.tistory.com/767>

ArrayList는 자바의 List 인터페이스를 상속받은 여러 클래스 중 하나이다. 일반 배열과 동일하게 연속된 메모리 공간을 사용하며 인덱스는 0부터 시작한다.![](https://images.velog.io/images/disambur23/post/6ff8624d-aee0-42a8-b80c-b271044584d6/image.png)

배열과의 차이점은 배열은 크기가 고정인 반면, ArrayList는 크기가 가변적으로 변한다. 내부적으로 저장이 가능한 메모리 용량이 있으며, 현재 사용 중인 공간의 크기가 있다. 만약 현재 가용량(Capacity) 이상을 저장하려고 할 때 더 큰 공간의 메모리를 새롭게 할당한다.

## ArrayList 생성
일단 ArrayList를 사용하려면 import가 필요하다.
```java
import java.util.ArrayList;
```
```java
ArrayList list = new ArrayList();//타입 미설정 Object로 선언된다.
ArrayList<Student> members = new ArrayList<Student>();//타입설정 Student객체만 사용가능
ArrayList<Integer> num = new ArrayList<Integer>();//타입설정 int타입만 사용가능
ArrayList<Integer> num2 = new ArrayList<>();//new에서 타입 파라미터 생략가능
ArrayList<Integer> num3 = new ArrayList<Integer>(10);//초기 용량(capacity)지정
ArrayList<Integer> list2 = new ArrayList<Integer>(Arrays.asList(1,2,3));//생성시 기본 값추가
```
만약 ArrayLIst<String> list = new ArrayList<String>(); 이라고 되어있다면 String객체들만 add되어질수있고 다른 타입의 객체는 사용이 불가능하다.
  
> ※제네릭스는 선언할 수 있는 타입이 객체 타입이다. int는 기본자료형이기 때문에 들어갈수 없으므로 int를 객체화시킨 wrapper클래스를 사용해야 한다. 위와 같이 Integer 사용!


  
## ArrayList 값 추가
  
```java
ArrayList<Integer> list = new ArrayList<Integer>();
list.add(3); //값 추가
list.add(null); //null값도 add가능
list.add(1,10); //index 1에 10 삽입
```
```java
ArrayList<Student> members = new ArrayList<Student>();
Student student = new Student(name,age);
members.add(student);
members.add(new Member("홍길동",15));
```
ArrayList에 값을 추가하려면 ArrayList의 add(index, value) 메소드를 사용하면 된다. index를 생략하면 ArrayList 맨 뒤에 데이터가 추가되며 index중간에 값을 추가하면 해당 인덱스부터 마지막 인덱스까지 모두 1씩 뒤로 밀려난다.![](https://images.velog.io/images/disambur23/post/a7934711-c815-4bf5-89e3-ea37242db954/image.png)

이 경우 데이터가 늘어나면 늘어날 수록 성능에 악영향이 미치기에 중간에 데이터를 insert를 해야할 경우가 많다면 ArrayList보다는 LinkedList를 활용하시는 것도 좋은 방법이다. 

## ArrayList 값 삭제
```java
ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3));
list.remove(1);  //index 1 제거
list.clear();  //모든 값 제거
```
ArrayList에 값을 제거하려면 ArrayList의 remove(index) 메소드를 사용하면 된다. remove()함수를 사용하여 특정 인덱스의 객체를 제거하면 바로 뒤 인덱스부터 마지막 인덱스까지 모두 앞으로 1씩 당겨진다. 모든 값을 제거하려면 clear() 메소드를 사용하면 된다.
  
## ArrayList 크기 구하기
```java
ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3));
System.out.println(list.size()); //list 크기 : 3
```
ArrayList의 크기를 구하려면 ArrayList의 size() 메소드를 사용하면 된다.
  
  
## ArrayList 값 출력
```java
ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3));

System.out.println(list.get(0));//0번째 index 출력
		
for(Integer i : list) { //for문을 통한 전체출력
    System.out.println(i);
}

System.out.println(list); //그냥 list 출력

Iterator iter = list.iterator(); //Iterator 선언 
while(iter.hasNext()){//다음값이 있는지 체크
    System.out.println(iter.next()); //값 출력
}
```
ArrayList의 get(index) 메소드를 사용하면 ArrayList의 원하는 index의 값이 리턴된다. 전체출력은 대부분 for문을 통해서 출력을하고 Iterator를 사용해서 출력을 할수도 있다. 
  
## ArrayList 값 검색
```java
ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(1,2,3));
System.out.println(list.contains(1)); //list에 1이 있는지 검색 : true
System.out.println(list.indexOf(1)); //1이 있는 index반환 없으면 -1
```  
  
ArrayList에서 찾고자 하는 값을 검색하려면 ArrayList의 contains(value) 메소드를 사용하면 된다. 만약 값이 있다면 true가 리턴되고 값이 없다면 false가 리턴된다. 값이 있는 index를 찾으려면 indexOf(value) 메소드를 사용하면 되고 만약 값이 없다면 -1을 리턴한다.
  
 참조: https://coding-factory.tistory.com/551
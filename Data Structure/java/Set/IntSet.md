# 배열로 집합 만들기

같은 자료형만 있는 집합은 배열로 표현할 수 있다.

```java
public class IntSet {
     private int max; // 집합의 최대 개수
     private int num; // 집합의 요소 개수
     private int[] set; // 집합 본체
}
```

변수로는 집합 요소의 최대 개수, 현재 집합 요소의 개수, 집합의 본체를 만들어야 한다.

- int형 집합을 만들것이므로 int형 배열을 선언해준다.

## 생성자 및 변수 호출 함수

```java
     // 생성자
     public IntSet(int capacity) {
          num = 0;
          max = capacity;
          try {
               set = new int[max]; // 집합 배열 생성
          } catch (OutOfMemoryError e) { // 배열 생성 실패
               max = 0;
          }
     }

    // 집합의 최대 개수
     public int capacity() {
          return max; // 최대 개수 반환
     }

     // 집합의 요소 개수
     public int size() {
          return num; // 요소 개수 반환
     }
```

생성자에서는 변수를 모두 초기화해준다.

- 처음 생성할 때는 요소 개수가 0이고, 파라미터로 넘겨준 최대 요소 개수를 max에 대입해준다.
- 배열 생성할 시에 실패를 대비해서 예외처리를 해주고, 예외가 터졌을 때는 max를 0으로 초기화해준다.

## 집합의 요소 검색, 추가, 삭제

### 검색

```java
     // 집합에서 n을 검색(index 반환)
     public int indexOf(int n) {
          for (int i = 0; i < n; i++)
               if (set[i] == n)
                    return i; // 검색 성공, index 반환
          return -1; // 검색 실패
     }

     // 집합에 n이 있는지 없는지 확인
     public boolean contains(int n) {
          return indexOf(n) != -1;
     }
```

배열의 첫 주소부터 if문으로 확인을 해서 요소가 있는지 검색하고, index를 반환한다.
단순히 값이 집합에 존재하는지 확인하는 함수

- 앞에서 만든 indexOf() 함수를 사용해 확인한다.

### 추가, 삭제

```java
     // 집합에 n을 추가
     public boolean add(int n) {
          if (num >= max || contains(n)) { // 가득 찼거나 이미 n이 존재
               return false;
          } else {
               set[num++] = n; // 가장 마지막 자리에 n을 추가
               return true;
          }
     }

     // 집합에서 n을 삭제
     public boolean remove(int n) {
          int idx;

          if (num <= 0 || ((idx = indexOf(n)) == -1)) { // 비어 있거나 n이 존재하지 않는다.
               return false;
          } else {
               set[idx] = set[--num]; // 마지막 요소를 삭제한 곳으로 옮긴다.(덮어 없애는 것)
               return true;
          }
     }
```

추가, 삭제 함수에서는 일단 집합이 꽉찼거나 아예 없을 경우를 대비해줬고, 집합은 값의 중복이 불가하므로 그에 대한 예외도 처리해줬다.
추가 함수에서는 그냥 비어있는 배열 자리에 값을 넣었지만, 삭제 함수에서는 가장 최근에 추가한 값을 삭제할 값의 자리로 이동시켜 덮어 씌우는 방식으로 삭제를 구현했다.

### 집합 복사

```java
    // 집합 s에 복사
     public void copyTo(IntSet s) {
          int n = (s.max < num) ? s.max : num; // 복사할 요소 개수
          for (int i = 0; i < n; i++)
               s.set[i] = set[i];
          s.num = n;
     }

     // 집합 s를 복사
     public void copyFrom(IntSet s) {
          int n = (max < s.num) ? max : s.num; // 복사할 요소 개수
          for (int i = 0; i < n; i++)
               set[i] = s.set[i];
          num = n;
     }
    // 집합 s1과 s2의 합집합을 복사
     public void unionOf(IntSet s1, IntSet s2) {
          copyFrom(s1);
          for (int i = 0; i < s2.num; i++)
               add(s2.set[i]);
     }
```

copyTo는 파라미터로 받은 set 객체에 클래스의 set객체(this)를 복사하는 함수이다.

- 복사하는 요소의 개수는 배열의 크기를 넘어설 수 없으므로, 둘 중에 작은 것에 맞춘다.

copyFrom은 파라미터로 받은 set 객체를 클래스의 set객체(this)로 복사하는 함수이다.

- 이 함수도 둘 중에 작은 배열의 크기만큼만 복사해준다.

unionOf는 일단 s1을 set(this)에 복사해주고, 그 뒤에 add() 함수를 사용해 s2까지 추가해주면서 합집합을 기능을 가진 함수이다.

- 당연히 add()함수에서 중복된 요소는 배제시킨다.

### 두 집합이 일치하는지 확인하는 함수와 toString함수 구현

```java
     // 집합 s와 같은지 확인
     public boolean equalTo(IntSet s) {
          if (num != s.num)
               return false; // 요소 개수부터 같지 않으면 확인해볼 필요 없음

          for (int i = 0; i < num; i++) {
               int j = 0;
               for (; j < s.num; j++)
                    if (set[i] == s.set[j])
                         break;
               if (j == s.num)
                    return false;
          }
          return true;
     }

    // "{a, b, c}" 형식의 문자열로 표현을 바꾼다.
     public String toString() {
          StringBuffer temp = new StringBuffer("{");
          for (int i = 0; i < num; i++)
               temp.append(set[i]).append(", ");
          temp.append("}");
          return temp.toString();
     }
```

equalTo() 함수로 set(this)와 파라미터로 받은 집합 s가 완전히 같은지 확인한다.

- 개수가 다르면 확인할 필요 없이 다르다.

toString() 함수는 Object 클래스의 toString()을 오버라이딩한다.

- 자바의 메서드를 오버라이드할 때는 클래스의 접근 제한을 바꿀 수 없다.


## 전체 코드

```java
public class IntSet {
     private int max; // 집합의 최대 개수
     private int num; // 집합의 요소 개수
     private int[] set; // 집합 본체

     // 생성자
     public IntSet(int capacity) {
          num = 0;
          max = capacity;
          try {
               set = new int[max]; // 집합 배열 생성
          } catch (OutOfMemoryError e) { // 배열 생성 실패
               max = 0;
          }
     }

     // 집합의 최대 개수
     public int capacity() {
          return max; // 최대 개수 반환
     }

     // 집합의 요소 개수
     public int size() {
          return num; // 요소 개수 반환
     }

     // 집합에서 n을 검색(index 반환)
     public int indexOf(int n) {
          for (int i = 0; i < n; i++)
               if (set[i] == n)
                    return i; // 검색 성공, index 반환
          return -1; // 검색 실패
     }

     // 집합에 n이 있는지 없는지 확인
     public boolean contains(int n) {
          return indexOf(n) != -1;
     }

     // 집합에 n을 추가
     public boolean add(int n) {
          if (num >= max || contains(n)) { // 가득 찼거나 이미 n이 존재
               return false;
          } else {
               set[num++] = n; // 가장 마지막 자리에 n을 추가
               return true;
          }
     }

     // 집합에서 n을 삭제
     public boolean remove(int n) {
          int idx;

          if (num <= 0 || ((idx = indexOf(n)) == -1)) { // 비어 있거나 n이 존재하지 않는다.
               return false;
          } else {
               set[idx] = set[--num]; // 마지막 요소를 삭제한 곳으로 옮긴다.(덮어 없애는 것)
               return true;
          }
     }

     // 집합 s에 복사
     public void copyTo(IntSet s) {
          int n = (s.max < num) ? s.max : num; // 복사할 요소 개수
          for (int i = 0; i < n; i++)
               s.set[i] = set[i];
          s.num = n;
     }

     // 집합 s를 복사
     public void copyFrom(IntSet s) {
          int n = (max < s.num) ? max : s.num; // 복사할 요소 개수
          for (int i = 0; i < n; i++)
               set[i] = s.set[i];
          num = n;
     }

     // 집합 s와 같은지 확인
     public boolean equalTo(IntSet s) {
          if (num != s.num)
               return false; // 요소 개수부터 같지 않으면 확인해볼 필요 없음

          for (int i = 0; i < num; i++) {
               int j = 0;
               for (; j < s.num; j++)
                    if (set[i] == s.set[j])
                         break;
               if (j == s.num)
                    return false;
          }
          return true;
     }

     // 집합 s1과 s2의 합집합을 복사
     public void unionOf(IntSet s1, IntSet s2) {
          copyFrom(s1);
          for (int i = 0; i < s2.num; i++)
               add(s2.set[i]);
     }

     // "{a, b, c}" 형식의 문자열로 표현을 바꾼다.
     public String toString() {
          StringBuffer temp = new StringBuffer("{");
          for (int i = 0; i < num; i++)
               temp.append(set[i]).append(", ");
          temp.append("}");
          return temp.toString();
     }
}

```
- [ArrayList](#arraylist)
- [ArrayList 클래스 및 생성자 구성하기](#arraylist-클래스-및-생성자-구성하기)
- [동적할당을 위한 resize 메소드 구현](#동적할당을-위한-resize-메소드-구현)
  - [조건문 1: `if (Arrays.equals(array, EMPTY_ARRAY))`](#조건문-1-if-arraysequalsarray-empty_array)
  - [조건문 2: `if (size == array_capacity)`](#조건문-2-if-size--array_capacity)
  - [조건문 3: `if (size < (array_capacity >>> 1))`](#조건문-3-if-size--array_capacity--1)
  - [hugeRangeCheck 메서드](#hugerangecheck-메서드)
    - [조건문 1: `if (MAX_ARRAY_SIZE - size <= 0)`](#조건문-1-if-max_array_size---size--0)
    - [조건문 2](#조건문-2)
- [add 메소드 구현](#add-메소드-구현)
  - [add(E value) & addLast(E value)](#adde-value--addlaste-value)
  - [add(int index, E value)](#addint-index-e-value)
  - [addFirst(E value)](#addfirste-value)
- [get, set, indexOf, contains 메소드 구현](#get-set-indexof-contains-메소드-구현)
  - [`get(int index)`](#getint-index)
  - [`set(int index, E value)`](#setint-index-e-value)
  - [indexOf(Object value)](#indexofobject-value)
  - [`LastIndexOF(Object value)`](#lastindexofobject-value)
  - [`contains(Object value)`](#containsobject-value)
- [remove 메소드 구현](#remove-메소드-구현)
  - [`remove(int index)`](#removeint-index)
  - [`remove(Object value)`](#removeobject-value)
- [size, isEmpty, clear 메소드 구현](#size-isempty-clear-메소드-구현)
  - [size()](#size)
  - [isEmpty()](#isempty)
  - [clear()](#clear)
- [clone, set, toArray 메소드 구현](#clone-set-toarray-메소드-구현)
  - [clone()](#clone)
  - [sort()](#sort)
  - [toArray()](#toarray)
- [iterator](#iterator)

# ArrayList

- Object[] 배열(객체 배열)을 두고 사용한다.
- '동적 할당'을 전제로 한다.
  - 데이터의 개수를 알 수 없는데 배열을 쓰고 싶을 때 사용
- 데이터 사이에 빈 공간을 허락하지 않는다.
  - 데이터는 항상 연속되어야 한다.

# ArrayList 클래스 및 생성자 구성하기

직접 만들었던 List 인터페이스를 implements 해준다.

```java
import my_interface.List;

public class ArrayList<E> implements List<E>, Cloneable, Iterable<E> {

     private static final int DEFAULT_CAPACITY = 10;
     private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
     private static final Object[] EMPTY_ARRAY = {};
     private int size;

     Object[] array;

     public ArrayList() {
          this.array = EMPTY_ARRAY;
          this.size = 0;
     }

     public ArrayList(int capacity) {
          if (capacity < 0)
               throw new IllegalArgumentException();

          if (capacity == 0) {
               array = EMPTY_ARRAY;
          } else {
               array = new Object[capacity];
          }
          this.size = 0;
     }
}
```

- DEFAULT_CAPACITY
  - 배열이 생성될 때의 최초 할당 크기
  - 최소 할당 용적 변수로 기본값은 10으로 설정
- MAX_ARRAY_SIZE
  - 확장 가능한 용적의 한계값
  - 이론적으로는 Integer.MAX_VALUE( = 2^31 - 1)의 인덱스를 가질 수 있지만, VM에 따라 배열의 크기 제한이 상이하고, 제한 값을 초과할 경우 에러가 발생하므로 8을 뺀 값을 저장해준다.
- EMPTY_ARRAY
  - 아무것도 없는 빈 배열
  - 용적 0
- size
  - 배열에 담긴 요소의 개수
  - 용적 크기가 아니다(capacity가 아니다.).
  - 생성자를 생성할 당시에는 항상 0
- array
  - 요소를 담을 배열

>DEFAULT_CAPACITY와 EMPTY_ARRAY 변수는 상수로 쓸 것이기 때문에 static final 키워드를 붙여준다.

- 생성자가 2개
  - `ArrayList()`
    - 용적을 초기화하지 않고, 빈 객체만 생성
  - `ArrayList(int capacity)`
    - 사용자가 데이터의 개수를 예측할 수 있어서 미리 공간 할당을 해놓고 싶은 경우 사용
    - capacity는 0보다 작을 수 없으므로 음수가 입력될 경우 IllegalArgumentException 발생

# 동적할당을 위한 resize 메소드 구현

들어오는 데이터의 개수에 따라 최적화된 용적을 가질 수 있도록 해야 한다.

- size(요소의 개수)가 capacity(용적)에 얼마나 차있는지를 확인하고, 적절한 크기에 맞게 배열의 용적을 변경해줘야 한다.
- capacity(용적)는 외부에서 마음대로 접근하지 못하도록 private으로 접근을 제한한다.

```java
     private void resize() {
          int array_capacity = array.length;

          // if (array_capacity == 0)
          if (Arrays.equals(array, EMPTY_ARRAY)) {
               array = new Object[DEFAULT_CAPACITY];
               return;
          }

          // if array is full
          if (size == array_capacity) {

               // default growing 1.5x
               int new_capacity = hugeRangeCheck(array_capacity, array_capacity + (array_capacity >> 1));

               // copy
               array = Arrays.copyOf(array, new_capacity);
               return;
          }

          // if array is less than half full
          if (size < (array_capacity >>> 1)) {
               int new_capacity = array_capacity >>> 1;

               // copy
               array = Arrays.copyOf(array, Math.max(new_capacity, DEFAULT_CAPACITY));
               return;
          }
     }
```

## 조건문 1: `if (Arrays.equals(array, EMPTY_ARRAY))`

- 생성자에서 사용자가 용적을 별도로 설정하지 않은 경우 EMPTY_ARRAY로 초기화 되어있어 용적이 0이다.
- 이 상황이라면 array의 용적을 최소 용적으로 설정해두었던 DEFAULT_CAPACITY의 크기만큼 배열을 생성해주고 메소드를 종료한다.
- `Arrays.equals(A, B)`
  - A 배열의 내용물과 B 배열의 내용물이 같은지 확인

## 조건문 2: `if (size == array_capacity)`

- 데이터의 개수와 용적과 같을 때, 즉 꽉 찼을 경우에는 용적을 늘려줘야 한다.
  - 용적을 늘려줄 때는 새로운 용적이 MAX_ARRAY_SIZE를 넘어버릴 수 있기 때문에 hugeRangeCheck 메서드로 체크를 해준다.(1.5배를 늘릴 것)
- 새로 만든 `new_capacity`를 `array`에 적용시켜야하므로 `Arrays.copyOf(원본 배열, 복사할 길이)`  메서드를 사용한다.
  - 원본 배열의 길이를 복사하고 복사할 길이가 남는다면 빈 공간은 null로 채운다.

## 조건문 3: `if (size < (array_capacity >>> 1))`

- 데이터가 용적의 절반 미만으로 채워지지 않았을 경우이다.
- 불필요한 공간이 있기 때문에 이를 제거하기 위한 분기문이다.

## hugeRangeCheck 메서드

resizing을 할 때 혹시나 발생할 overflow를 방지하기 위한 체크 함수이다.

- 용적은 초기에 설정한 `MAX_ARRAY_SIZE`를 초과할 수 없기 때문에 이 값을 넘는지 체크하면 된다.

```java
 private int hugeRangeCheck(int oldCapacity, int newCapacity) {

        // fully elements in array
        if (MAX_ARRAY_SIZE - size <= 0) { 
            throw new OutOfMemoryError("Required array length too large");
        }

  if(newCapacity >= 0) {
   if(newCapacity - MAX_ARRAY_SIZE <= 0) {
    return newCapacity;
   }
   return MAX_ARRAY_SIZE;
  }
  else {
         int fiveFourthsSize = oldCapacity + (oldCapacity >>> 2);

         if(fiveFourthsSize <= 0 || fiveFourthsSize >= MAX_ARRAY_SIZE) {
          return MAX_ARRAY_SIZE;
         }
         return fiveFourthsSize;
  }
 }
```

### 조건문 1: `if (MAX_ARRAY_SIZE - size <= 0)`

size(요소의 개수)가 `MAX_ARRAY_SIZE`(최대 용적)보다 클 경우는 당연히 에러를 발생시킨다.

- `OutOfMemoryError`

### 조건문 2

`if (newCapacity >= 0)`

- `newCapacity`가 0을 포함한 양수일 경우
- 새로 할당할 용적의 크기(`newCapacity`)가 `MAX_ARRAY_SIZE`보다 작을 경우에는 당연히 아무 무리 없이 `newCapacity`를 반환하면 되고, 그게 아니라면 그냥 MAX_ARRAY_SIZE를 반환시켜버린다.

`else`

- `newCapacity`가 음수일 경우
- 음수일 경우는 있을 수 없으므로 `fiveFourtheSize`라는 새로운 용적 변수를 선언한다.
  - A + (A >>> 2) = 5/4 * A이므로 `oldCapacity`약간 크게 만들어 `fiveFourtheSize` 변수를 초기화한다.
  - 이렇게 비스마스킹을 썼는데, `fiveFourtheSize`가 음수일경우나 `MAX_ARRAY_SIZE`를 넘었을 경우는 그냥 `MAX_ARRAY_SIZE`를 반환하고, 그게 아니라 정상적인 수가 나왔다면 `fiveFourtheSize`를 반환한다.

# add 메소드 구현

직접 구현한 List Interface에 있는 add 함수를 무조건 구현해줘야 한다.

add 메소드는 크게 3가지로 분류한다.

- addLast(E value)
  - 가장 마지막 부분에 추가
- addFirst(E value)
  - 가장 앞 부분에 추가
- add(int index, E value)
  - 특정 위치에 추가

>Java에 내장되어 있는 ArrayList는 add(), add(int index, E element) 뿐이다. 여기서 add()는 addFirst()의 역할을 한다.

## add(E value) & addLast(E value)

```java
     @Override
     public boolean add(E value) {
          addLast(value);
          return true;
     }

     public void addLast(E value) {
          if (size == array.length) {
               resize();
          }
          array[size] = value;
          size++;
     }
```

- add(E value) 메소드 내부에서 addLast()를 호출하므로 addLast()를 보면된다.
  - addLast()에서 일단 배열이 꽉 차있어서 요소를 추가하기 어렵기 때문에 resize()를 호출해서 배열 크기를 늘려준다.
  - 꽉 차있지 않다면 배열의 마지막에 value를 넣어주고, 요소의 개수를 가리키는 size 변수를 1 증가시킨다.

## add(int index, E value)

```java
     @Override
     public void add(int index, E value) {
          if (index > size || index < 0) {
               throw new IndexOutOfBoundsException();
          }

          if (index == size) {
               addLast(value);
               return;
          }

          if (size == array.length) {
               resize();
          }

          for (int i = size; i > index; i--) {
               array[i] = array[i - 1];
          }
          array[index] = value;
          size++;
     }
```

- 중간에 데이터를 추가하려면 일단 index가 원래 배열의 범위를 벗어나지 않는지 체크를 해야 한다.
  - 벗어난다면, `IndexOutOfBoundsException()` 에러를 터뜨리면 된다.
- 배열의 마지막 부분에 추가하는 것은 `addLast()` 메소드를 추가시키면 되고, 만약 배열이 꽉 찼다면 `resize()` 메서드를 호출한다.
- index가 중간에 위치하면 원래 그 index에 있던 요소부터 마지막 요소까지 한 칸씩 밀어주고 그 위치에 넣어주면 된다.

## addFirst(E value)

```java
     public void addFirst(E value) {
          add(0, value);
     }
```

- 배열의 처음에 데이터를 추가하는 것은 단순하게 처음부터 모든 요소를 한 칸씩 뒤로 밀어주고, 그 위치에 데이터를 넣어주면 되므로, `add(0, value)`를 호출한다.

# get, set, indexOf, contains 메소드 구현

## `get(int index)`

배열에서 index 위치에 해당하는 요소를 반환하는 메소드이다.

- 배열의 크기를 벗어난 index가 파라미터로 들어왔을 경우에는 `IndexOutOfBoundsException()` 예외를 발생시킨다.
- 반환할 때, 원본 데이터 타입으로 반환하기 위해 E 타입으로 캐스팅을 해준다.

```java
     @SuppressWarnings("unchecked")
     @Override
     public E get(int index) {
          if (index >= size || index < 0) {
               throw new IndexOutOfBoundsException();
          }
          return (E) array[index];
     }
```

- `@SuppressWarnings("unchecked")`
  - 이 annotation을 붙이지 않으면 type safe(타입 안정성)에 대한 경고를 보낸다.
  - 메소드에서 반환되는 것을 보면 E 타입으로 캐스팅하고 있고, 그 대상은 Object[] 배열의 Object 데이터이다.
    - 즉, Object -> E 타입으로 변환하는 것인데, E 타입으로 변환할 수 없는 타입일 가능성이 있다는 경고 표시가 뜬다. 하지만 우리가 add하여 받아들이는 데이터 타입은 E 타입만 존재한다.
      - 형 안정성 보장된다.
      - `ClassCastException()` 예외가 발생할 일이 없으니 이 경고들을 무시하겠다는 annotation인 것이다.

## `set(int index, E value)`

set 메소드는 배열의 index에 위치한 원래의 데이터를 value로 교체하는 메소드이다.

- 그냥 덮어씌우는 것
- 당연히 index 값이 배열의 범위를 넘어서면 `IndexOutOfBoundsException()`을 발생시킨다.

```java
     @Override
     public void set(int index, E value) {
          if (index >= size || index < 0) {
               throw new IndexOutOfBoundsException();
          } else {
               array[index] = value;
          }
     }
```

## indexOf(Object value)

파라미터로 들어오는 value의 위치를 배열에서 찾아서 그 위치를 반환하는 메소드이다.

- 만약 배열에 여러 개의 value가 있다면 가장 먼저 마주치는 요소의 인덱스를 반환한다.
- value가 배열에 없다면 -1을 반환한다.

```java
 @Override
     public int indexOf(Object value) {
          if (value == null) {
               for (int i = 0; i < size; i++) {
                    if (array[i] == null) {
                         return i;
                    }
               }
          } else {
               for (int i = 0; i < size; i++) {
                    if (value.equals(array[i])) {
                         return i;
                    }
               }
          }
          return -1;
     }
```

## `LastIndexOF(Object value)`

indexOf() 메소드는 배열의 처음부터 검색을 하지만, LastIndexOf()는 배열의 뒤에서부터 검색을 한다.

- value가 뒤 쪽에 있는 것이 예상될 때 더 빠르게 값을 찾아 인덱스를 반환할 수 있다.

```java
     public int lastIndexOf(Object value) {
          if (value == null) {
               for (int i = size - 1; i >= 0; i--) {
                    if (array[i] == null) {
                         return i;
                    }
               }
          } else {
               for (int i = size - 1; i >= 0; i--) {
                    if (value.equals(array[i])) {
                         return i;
                    }
               }
          }
          return -1;
     }
```

## `contains(Object value)`

파라미터로 들어오는 value가 배열에 있는지 확인만 하고 있으면 true, 없으면 false를 반환하는 메서드이다.

- `indexOf()` 메소드를 사용하면 된다.

```java
     @Override
     public boolean contains(Object value) {
          return indexOf(value) >= 0;
     }
```

# remove 메소드 구현

## `remove(int index)`

배열의 index 위치에 있는 요소를 제거하고, 제거한 값을 반환하는 메소드이다.

- 배열의 index 위치에 있는 값을 제거하면, 그 자리는 빈 공간이 되므로 뒤에 있는 요소들을 모두 한 칸씩 당겨줘야 한다.
- 계속 `remove(int index)` 메소드를 호출하다 보면 배열의 capacity(용적)가 요소의 개수에 비해 너무 많을 수가 있으니 remove 메서드를 호출할 때마다 `resize()` 메서드를 호출해서 capacity를 조절해준다.

```java
     @SuppressWarnings("unchecked")
     @Override
     public E remove(int index) {
          if (index >= size || index < 0) {
               throw new IndexOutOfBoundsException();
          }

          E element = (E) array[index];
          array[index] = null;

          for (int i = index; i < size - 1; i++) {
               array[i] = array[i + 1];
               array[i + 1] = null;
          }
          size--;
          resize();
          return element;
     }
```

- index 값이 배열의 범위에 해당하지 않는다면, `IndexOutOfBoundsException()` 예외를 발생시킨다.
- 삭제하는 원소를 반환해야 하므로, Object -> E 타입으로 캐스팅을 하면서 경고창이 뜬다.
  - 이 경고창을 없애기 위해 `@SuppressWarnings("unchecked")` annotation을 붙여준다.
    - 어차피 삭제되는 원소 또한 E type 외에는 다른 형이 없기 때문에 형 안정성이 보장된다.
- 명시적으로 요소를 null로 처리해줘야 가비지컬렉터에 의해 더이상 쓰지 않는 데이터의 메모리가 수거되기 때문에 최대한 null 처리를 해주는 것이 좋다.

## `remove(Object value)`

파라미터로 들어오는 value를 배열에서 찾아 삭제하는 메소드이다.

- value를 찾아 삭제에 성공하면 true 반환, value가 없거나 삭제에 실패할 경우 false를 반환한다.
- 배열에 value가 여러 개 있을 경우에는 가장 먼저 마주치는 요소를 삭제한다.

```java
     @Override
     public boolean remove(Object value) {
          int index = indexOf(value);
          if (index == -1) {
               return false;
          }
          remove(value);
          return true;
     }
```

# size, isEmpty, clear 메소드 구현

## size()

배열에 들어있는 요소의 개수를 반환하는 메소드

```java
     @Override
     public int size() {
          return size;
     }
```

## isEmpty()

배열이 비어 있는지 확인하는 메소드

```java
     @Override
     public boolean isEmpty() {
          return size == 0;
     }
```

## clear()

배열에 있는 모든 요소를 제거하여 비워버리는 메소드

```java
     @Override
     public void clear() {
          for (int i = 0; i < size; i++) {
               array[i] = null;
          }
          size = 0;
          resize();
     }
```

- 배열을 비울 때는 명시적으로 null 처리를 해주는 것이 좋다.
  - GC issue
- 배열을 비웠으니 `resize()` 메소드를 호출하여 capacity를 줄여준다.

# clone, set, toArray 메소드 구현

## clone()

기존에 있던 객체를 파괴하지 않고 요소들이 모두 동일한 객체를 새로 하나 만드는 메소드

- Object에 있는 메소드이지만 접근 제어자가 protected라서 직접 재정의(구현)하려면 Cloneable 인터페이스를 implement 해야한다.

```java
     @Override
     public Object clone() {

          try {
               ArrayList<?> cloneList = (ArrayList<?>) super.clone(); //new ArrayList()와 같다.
               cloneList.array = new Object[size]; // array 새로 생성

               System.arraycopy(array, 0, cloneList.array, 0, size);

               return cloneList;
          } catch (CloneNotSupportedException e) {
               throw new Error(e);
          }
     }
```

- `super.clone()`
  - 생성자 역할
  - shallow copy를 통해 `new ArrayList()`를 호출하는 것과 같다.
    - deep copy를 하려면(완벽하게 복제하려면) clone한 리스트의 array 또한 새로 생성해서 해당 배열에 copy해줘야 한다.
  - 이렇게 cloneList를 반환하면, cloneList의 array또한 새로 생성된

## sort()

```java
     public void sort() {
          sort(null);
     }

     @SuppressWarnings("unchecked")
     public void sort(Comparator<? super E> c) {
          Arrays.sort((E[]) array, 0, size, c);
     }
```

## toArray()

toArray()는 크게 두 가지가 있다.

1. 아무런 인자 없이 현재 있는 ArrayList의 리스트를 객체배열(Object[])로 반환

- 장점
  - ArrayList에 있는 요소의 수만큼 정확하게 배열의 크기가 할당되어 반환된다.

2. ArrayList를 파라미터로 들어오는 다른 배열에 복사해서 그 배열을 반환
   - 장점
     - 객체 클래스로 상속관계에 있는 타입이라서 다형성을 이용하거나 Wrapper(예)Integer -> int) 같이 데이터 타입을 유연하게 캐스팅할 수 있다.
     - 파라미터로 들어오는 a 배열이 ArrayList보다 크기가 크고 더 많은 요소가 있다면, ArrayList의 요소를 복사하고, 원래 남아 있던 나머지 a의 요소들은 보존할 수 있다.

```java
     public Object[] toArray() {
          return Arrays.copyOf(array, size);
     }

     @SuppressWarnings("unchecked")
     public <T> T[] toArray(T[] a) {
          if (a.length < size) {
               return (T[]) Arrays.copyOf(array, size, a.getClass());
          }

          System.arraycopy(array, 0, a, 0, size);

          if (a.length > size) {
               a[size] = null;
          }
          return a;
     }
```

- `public <T> T[] toArray(T[] a)`
  - 상위 타입으로 들어오는 객체에 대해서도 데이터를 담을 수 있도록 별도의 제네릭 메소드를 구성한 것
    - 우리가 만들었던 ArrayList의 E타입이 Integer이고, T 타입이 Object라고 한다면, OBject는 Integer의 상위 타입이므로 Object 안에 Integer 타입의 데이터를 담을 수 있다.
    - 사용자가 만든 부모, 자식 클래스 같이 상속관계에 있는 클래스들에서 하위 타입이 상위 타입으로 데이터를 받고 싶을 때도 사용할 수 있다.(다형성)
- `(T[]) Arrays.copyOf(array, size, a.getClass());`
  - 파라미터로 들어오는 배열(a)가 현재 array의 요소 개수(size)보다 작으면 size에 맞게 a의 공간을 재할당하면서 array에 있던 모든 요소를 복사한다.
  - 상위 타입에 대해서도 담을 수 있도록 만들기 위해 copyOf() 메소드에서 Class라는 파라미터를 마지막에 넣어준다.(`a.getClass()`)
    - 이렇게 하면 Object[] 배열로 리턴된 것을 T[] 타입으로 캐스팅하여 반환한다.

# iterator

배열의 요소들에 반복을 통해 접근을 도와주는 inner class

- now 변수
  - Iter class에서 다루는 배열의 인덱스
- hasNext() 메소드
  - 반복을 통해 요소들에 접근하다가 다음 인덱스에 값이 있는지 확인하는 메소드
  - 현재 인덱스가 배열의 개수보다 작으면 true
- next() 메소드
  - 현재 인덱스의 다음 요소값을 반환하는 메소드
  - 현재 인덱스가 마지막 인덱스이면 다음 인덱스는 없으므로 `NoSuchElementException()` 예외를 발생시킨다.

```java
     @Override
     public Iterator<E> iterator() {
          return new Iter();
     }

     private class Iter implements Iterator<E> {

          private int now = 0;

          @Override
          public boolean hasNext() {
               return now < size;
          }

          @SuppressWarnings("unchecked")
          @Override
          public E next() {
               int cs = now;
               if (cs >= size) {
                    throw new NoSuchElementException();
               }
               Object[] data = ArrayList.this.array;
               now = cs + 1;
               return (E) data[cs];
          }
          
          public void remove() {
               throw new UnsupportedOperationException();
          }
     }
```

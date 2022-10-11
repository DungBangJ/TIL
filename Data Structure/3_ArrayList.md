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

`newCapacity`가 0을 포함한 양수일 경우

- 새로 할당할 용적의 크기(`newCapacity`)가 `MAX_ARRAY_SIZE`보다 작을 경우에는 당연히 아무 무리 없이 `newCapacity`를 반환하면 되고, 그게 아니라면 그냥 MAX_ARRAY_SIZE를 반환시켜버린다.

`else`

`newCapacity`가 음수일 경우

- 음수일 경우는 있을 수 없으므로 `fiveFourtheSize`라는 새로운 용적 변수를 선언한다.
  - A + (A >>> 2) = 5/4 * A이므로 `oldCapacity`약간 크게 만들어 `fiveFourtheSize` 변수를 초기화한다.
  - 이렇게 비스마스킹을 썼는데, `fiveFourtheSize`가 음수일경우나 `MAX_ARRAY_SIZE`를 넘었을 경우는 그냥 `MAX_ARRAY_SIZE`를 반환하고, 그게 아니라 정상적인 수가 나왔다면 `fiveFourtheSize`를 반환한다.

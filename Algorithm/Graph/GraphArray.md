# **인접 행렬로 구하는 법**

인접행렬

-   그래프의 연결 관계를 이차원 배열로 나타내는 방식

## 1. 인접 행렬 선언

```java
public class graphArray{
   private int[][] array;
}
```

위 코드의 array

-   그래프 정점(vertex)들을 이어주는 간선(edge)들을 0과 1로 표시한다.
    -   0: 연결 x
    -   1:연결 o

## 2. 행렬의 크기 설정

```
public class GraphArray {
    private int[][] array; //인접 행렬 선언

    public GraphArray(int size) {
        array = new int[size][size]; //인접 행렬의 크기
    }
}
```

생성자로 정사각 행렬을 크기와 함께 초기화한다.

## 3. 필요한 메서드 구현

-   array를 가져오는 함수
-   array가 비어있는지 확인하는 함수
-   방향 그래프 경우
    -   정점 x와 y를 **단방향**으로 이어주는 함수
    -   단방향 연결을 끊어주는 함수
-   완전 그래프 경우
    -   정점 x와 y를 **양방향**으로 이어주는 함수
    -   양방향 연결을 끊어주는 함수
-   행렬을 출력하는 함수

### array를 가져오는 함수

```java
public int[][] getArray(){
   return array;
}
```

### array가 비어있는지 확인하는 함수

```java
public boolean isEmpty(){
   return array == null;
}
```
- array가 null이면(비어 있으면) true를 반환

### 정점 x와 정점 y를 단방향으로 연결해주는 함수

```java
public void addDirectedEdge(int x, int y){
   array[x][y] = 1;
}
```
- x와 y를([x][y]) 연결시켜준다(= 1).

### 정점 x와 정점 y를 양방향으로 연결해주는 함수

```java
public void addCompleteEdge(int x, int y){
   array[x][y] = 1;
   array[y][x] = 1;
}
```

### 정점 x와 정점 y의 단방향 연결을 끊어주는 함수

```java
public void deleteDirectedEdge(int x, int y){
   array[x][y] = 0;
}
```
- x와 y를([x][y]) 연결을 끊어준다(= 0).

### 정점 x와 정점 y의 양방향 연결을 끊어주는 함수

```java
public void deleteCompleteEdge(int x, int y){
   array[x][y] = 0;
   array[y][x] = 0;
}
```

### 출력 함수

```java
public void printArray(){
   for(int i=0; i<array.length; i++){
      for(int j=0; j<array.length; j++){
         System.out.print(array[i][j] + " ");
      }
      System.out.println();
   }
}
```

## 전체 코드

```java
public class graphArray{
   private int[][] array;

   public graphArray(int size){
      array = new int[size+1][size+1]
   }

   public int[][] getArray(){
      return array;
   }

   public boolean isEmpty() {
		return array == null;
	}
   public void addDirectedEdge(int x, int y) {
		array[x][y] = 1;
	}

	public void addCompleteEdge(int x, int y) {
		array[x][y] = 1;
		array[y][x] = 1;
	}
	public void deleteDirectedEdge(int x, int y) {
		array[x][y] = 0;
	}

	public void deleteCompleteEdge(int x, int y) {
		array[x][y] = 0;
		array[y][x] = 0;
	}
	
}
```
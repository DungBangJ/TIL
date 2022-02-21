#정렬
정렬 알고리즘의 핵심 요소
- 교환
- 선택
- 삽입
## 내부정렬과 외부정렬
내부정렬
- 정렬할 모든 데이터를 하나의 배열에 저장할 수 있는 경우에 사용

외부 정렬
- 정렬할 데이터가 너무 많아서 하나의 배열에 저장할 수 없는 경우에 사용하는 알고리즘

# 버블 정렬

오름차순이라면 배열의 맨 오른쪽에서부터 두 요소씩 크기 비교를 하여 위치를 바꾸거나 유지시킨다.
- n개의 요소는 n-1번의 비교를 하게 된다.
- 모든 요소를 한 번의 사이클로 비교를 마치는 것을 1 pass라고 한다.
  - 첫 pass 때 배열의 요소 중 가장 작은 요소가 0번 index로 이동한다.
  - 다음 pass는 그 다음으로 작은 요소가 1번 index로 이동하는 형식이다.
- 총 n-1번의 pass를 진행한다.

## 버블 정렬 프로그램
이웃한 두 요소의 대소 관계를 비교하여 교환을 반복

```java
package Sort;

import java.util.Scanner;

class BubbleSort {
     //a[idx1]과 a[idx2]의 자리를 바꾼다.
     static void swap(int[] a, int idx1, int idx2) {
          int t = a[idx1];
          a[idx1] = a[idx2];
          a[idx2] = t;
     }

     //버블 정렬
     static void bubbleSort(int[] a, int n) {
          for (int i = 0; i < n - 1; i++) {
               for (int j = n - 1; j > i; j--) {
                    if (a[j - 1] > a[j]) {
                         swap(a, j - 1, j);
                    }
               }
          }
     }

     public static void main(String[] args) {
          Scanner sc = new Scanner(System.in);

          System.out.println("버블 정렬(버전 1");
          System.out.print("요솟수: ");
          int nx = sc.nextInt();
          int[] x = new int[nx];

          for (int i = 0; i < nx; i++) {
               System.out.print("x[" + i + "]: ");
               x[i] = sc.nextInt();
          }

          bubbleSort(x, nx);

          System.out.println("오름차순으로 정렬했습니다.");
          for (int i = 0; i < nx; i++) {
               System.out.println("x[" + "] = " + x[i]);
          }
     }
}
```

- 버블 정렬은 중간에 정렬이 끝났음에도 n-1번의 pass를 진행하므로 비효율적이다.
  - 다음의 코드로 개선

```java
     //버블 정렬의 비효율성을 제거
static void bubbleSort2(int[] a, int n) {
     for (int i = 0; i < n - 1; i++) {
          int exchg = 0; //교환 횟수
        for (int j = n - 1; j > i; j--) {
             if (a[j - 1] > a[j]) {
                  swap(a, j - 1, j);
                  exchg++;
             }
        }
        //교환이 이루어지지 않았다면 종료
        if(exchg == 0) break;
     }
}
```

# 단순 선택 정렬
가장 작은 요소부터 선택해 알맞은 위치로 옮겨서 순서대로 정렬하는 알고리즘

교환과정
- 아직 정렬하지 않은 부분에서 가장 작은 키의 값(a[min])을 선택한다.
- a[min]과 아직 정렬하지 않은 부분의 첫 번째 요소를 교환한다.

```java
static void selectionSort(int[] a, int n) {
          for (int i = 0; i < n; i++) {
               int min = i; //아직 정렬되지 않은 부분에서 가장 작은 요소의 인덱스를 기록
               for (int j = i + 1; j < n; j++) {
                    if (a[j] < a[min]) {
                         min = j;
                    }

                    swap(a, i, min); //아직 정렬되지 않은 부분의 첫 요소와 가장 작은 요소를 교환
               }
          }
     }
```
- 교환 횟수: (n^2-n)/2

# 단순 삽입 정렬
선택한 요소를 그보다 더 앞쪽의 알맞은 위치에 '삽입하는' 작업을 반복하여 정렬하는 알고리즘
- 아직 정렬되지 않은 부분의 첫 번째 요소를 정렬된 부분의 알맞은 위치에 삽입

```java
package Sort;

import java.util.Scanner;

public class InsertionSort {
     static void insertionSort(int[] a, int n) {
          for (int i = 1; i < n; i++) {
               int j;
               int tmp = a[i];

               for (j = i; j > 0 && a[j - 1] > tmp; j--) {
                    a[j] = a[j - 1];
               }

               a[j] = tmp;
          }
     }

     public static void main(String[] args) {
          Scanner sc = new Scanner(System.in);
          System.out.println("단순 삽입 정렬");
          System.out.print("요솟수: ");
          int nx = sc.nextInt();
          int[] x = new int[nx];

          for (int i = 0; i < nx; i++) {
               System.out.print("x[" + i + "]: ");
               x[i] = sc.nextInt();
          }

          insertionSort(x, nx);

          System.out.println("오름차순으로 정렬했습니다.");
          for (int i = 0; i < nx; i++) {
               System.out.println("x[" + i + "] = " + x[i]);
          }
     }
}
```

- 교환 횟수: n^2/2

# 셸 정렬
단순 삽입 정렬의 장점은 살리고 단점은 보완한 알고리즘
- 장점
  - 정렬을 마쳤거나 정렬을 마친 상태에 가까우면 정렬 속도가 빨라진다.
- 단점
  - 삽입할 위치가 멀리 떨어져 있으면 이동(대입)해야 하는 횟수가 많아진다.

과정(요소가 8개일 때)
1. 2개 요소에 대해 '4-정렬'을 한다.(4개의 그룹)
2. 4개 요소에 대해 '2-정렬'을 한다.(2개의 그룹)
3. 8개 요소에 대해 '1-정렬'을 한다.(1개의 그룹)

```java
package Sort;

import java.util.Scanner;

public class ShellSort {
     static void shellSort(int[] a, int n) {
          for (int h = n / 2; h > 0; h /= 2) {
               for (int i = h; i < n; i++) {
                    int j;
                    int tmp = a[i];
                    for (j = i - h; j >= 0 && a[j] > tmp; j -= h) {
                         a[j + h] = a[j];
                    }

                    a[j + h] = tmp;
               }
          }
     }

     public static void main(String[] args) {
          Scanner sc = new Scanner(System.in);

          System.out.println("셸 정렬(버전1)");
          System.out.print("요솟 수:");

          int nx = sc.nextInt();
          int[] x = new int[nx];

          for (int i = 0; i < nx; i++) {
               System.out.print("x[" + i + "]: ");
               x[i] = sc.nextInt();
          }

          shellSort(x, nx);

          System.out.println("오름차순으로 정렬");
          for (int i = 0; i < nx; i++) {
               System.out.println("x[" + i + "] = " + x[i]);
          }
     }
}

```
- 처음에 그룹을 두개로 나눈 후에 정렬을 하고 다시 합치면 정렬이 안된 처음 상태와 다를게 없으므로 이를 보완해야 한다.

```java
package Sort;

import java.util.Scanner;

public class ShellSort {
     static void shellSort(int[] a, int n) {
          for (int h = n / 2; h > 0; h /= 2) {
               for (int i = h; i < n; i++) {
                    int j;
                    int tmp = a[i];
                    for (j = i - h; j >= 0 && a[j] > tmp; j -= h) {
                         a[j + h] = a[j];
                    }

                    a[j + h] = tmp;
               }
          }
     }

     static void shellSort2(int[] a, int n) {
          int h;
          //h의 초깃값을 구한다. 1부터 시작하여 값을 3배하고 1을 더하면서 n/9를 넘지 않는 가장 큰 값을 h에 대입한다.
          for (h = 1; h < n / 9; h = h * 3 + 1);

          for (; h > 0; h /= 3) {
               for (int i = h; i < n; i++) {
                    int j;
                    int tmp = a[i];
                    for (j = i - h; j >= 0 && a[j] > tmp; j -= h) {
                         a[j + h] = a[j];
                    }
                    
                    a[j + h] = tmp;
               }
          }
     }

     public static void main(String[] args) {
          Scanner sc = new Scanner(System.in);

          System.out.println("셸 정렬");
          System.out.print("요솟 수:");

          int nx = sc.nextInt();
          int[] x = new int[nx];

          for (int i = 0; i < nx; i++) {
               System.out.print("x[" + i + "]: ");
               x[i] = sc.nextInt();
          }

          shellSort(x, nx);
          shellSort2(x, nx);
          
          System.out.println("오름차순으로 정렬");
          for (int i = 0; i < nx; i++) {
               System.out.println("x[" + i + "] = " + x[i]);
          }
     }
}
```

- 멀리 떨어져 있는 요소를 교환해야하므로 안정적이지는 않다.

# 퀵 정렬

배열에서 중간에 피벗 요소[x]를 구한다.
1. x <= a[pl]가 성립하는 요소를 찾을 때까지 pl을 오른쪽으로 스캔
2. a[pr] <= x가 성립하는 요소를 찾을 때까지 pr을 왼쪽으로 스캔
3. pl은 오른쪽으로, pr은 왼쪽으로 이동하면서 피벗보다 작은 것은 피벗 왼쪽에 피벗보다 큰 것은 피벗 오른쪽에 위치하도록 서로 교환을 진행한다.
4. pl과 pr이 교차하거나 피벗에서 만나면 그룹을 나누는 작업을 마친다.(피벗 이하의 그룹, 피벗 이상의 그룹)

```java
package Sort;

import java.util.Scanner;

public class QuickSort {
     static void swap(int[] a, int idx1, int idx2) {
          int t = a[idx1];
          a[idx1] = a[idx2];
          a[idx2] = t;
     }

     static void quickSort(int[] a, int left, int right) {
          int pl = left;
          int pr = right;
          int x = a[(pl + pr) / 2];
          do {
               while (a[pl] < x) pl++;
               while (a[pr] > x) pr--;

               if (pl <= pr) {
                    swap(a, pl++, pr--);
               }

          } while (pl <= pr);

          if(left < pr) quickSort(a, left, pr);
          if(pl < right) quickSort(a, pl, right);
     }

     public static void main(String[] args) {
          Scanner sc = new Scanner(System.in);

          System.out.println("퀵 정렬");
          System.out.print("요솟수: ");
          int nx = sc.nextInt();
          int[] x = new int[nx];

          for (int i = 0; i < nx; i++) {
               System.out.print("x[" + i + "]: ");
               x[i] = sc.nextInt();
          }

          quickSort(x, 0, nx - 1);

          System.out.println("오름차순으로 정렬했습니다.");
          for (int i = 0; i < nx; i++) {
               System.out.println("x[" + i + "} = " + x[i]);
          }
     }
}

```
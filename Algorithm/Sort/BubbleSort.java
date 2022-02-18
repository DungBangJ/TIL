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

     //단순 선택 정렬
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
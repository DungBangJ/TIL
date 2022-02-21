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

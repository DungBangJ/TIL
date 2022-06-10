void    BubbleSort(int arr[], int size)
{
    int i, j, temp;
    for (i = 0; i < (size - 1); i++)
        for (j = 0; j < size - 1 - i; j++)
            if (arr[j] > arr[j + 1])
            {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
}

#include <stdio.h>

int main(void)
{
    int arr[10] = {2, 1, 5, 4, 3, 7, 8, 9, 6, 0};
    BubbleSort(arr, 10);
    for (int i = 0; i < 10; i++)
        printf("%d ", arr[i]);
}
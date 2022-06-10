void    InsertionSort(int arr[], int size)
{
    int i, j, tmp;
    for (i = 1; i < size; i++)
    {
        tmp = arr[i];
        for (j = i; (j > 0) && (arr[j - 1] > tmp); j--)
            arr[j] = arr[j - 1];
        arr[j] = tmp;
    }
}

#include <stdio.h>

int main(void)
{
    int arr[10] = {0, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    InsertionSort(arr, 10);
    for (int i = 0; i < 10; i++)
        printf("%d", arr[i]);
    printf("\n");
}
void    SelectionSort(int arr[], int size)
{
    int i, j, max, tmp;
    for(i = 0; i < (size - 1); i++)
    {
        max = 0;
        for (j = 0; j <= size - 1 - i; j++)
            if (arr[j] > arr[max])
                max = j;
        tmp = arr[size - 1 - i];
        arr[size - 1 - i] = arr[max];
        arr[max] = tmp;
    }
}

#include <stdio.h>

int main(void)
{
    int arr[10] = {0, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    SelectionSort(arr, 10);
    for (int i = 0; i < 10; i++)
        printf("%d", arr[i]);
    printf("\n");
}
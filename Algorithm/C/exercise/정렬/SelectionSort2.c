void    SelectionSort2(int arr[], int size)
{
    int i, j, min, tmp;
    for (i = 0; i < size - 1; i++)
    {
        min = i;
        for (j = i + 1; j < size; j++)
            if (arr[j] < arr[min])
                min = j;
        tmp = arr[i];
        arr[i] = arr[min];
        arr[min] = tmp;
    }
}

#include <stdio.h>

int main(void)
{
    int arr[10] = {0, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    SelectionSort2(arr, 10);
    for (int i = 0; i < 10; i++)
        printf("%d", arr[i]);
    printf("\n");
}
#include <stdio.h>
#include <stdlib.h>

int *MaxMinArr(int arr[], int size)
{
    int i;
    int j;
    int k;
    int q;
    int *arr2;

    i = 0;
    j = 0;
    k = 1;
    q = 0;
    arr2 = (int *)malloc(sizeof(int) * size);
    while (i < size)
    {
        arr2[i] = arr[size - 1 - j];
//        printf("arr2[i] = %d, arr[size - 1 - j]= %d\n", arr2[i], arr[size - 1 - j]);
        i += 2;
        j++;
        if (i == size + 1)
            break;
        arr2[k] = arr[q];
//        printf("arr2[k] = %d, arr[q] = %d\n", arr2[k], arr[q]);
        k += 2;
        q++;
    }
    return (arr2);
}

void    printArr(int *arr, int size)
{
    int i;

    i = 0;
    printf("[");
    while (i < size)
    {
        printf("%d", arr[i]);
        if (i != size - 1)
            printf(", ");
        i++;
    }
    printf("]");
}

int main(void)
{
    int arr[] = {1, 2, 3, 4, 5, 6, 7};
    int size = sizeof(arr) / sizeof(int);
    int *arr2 = MaxMinArr(arr, size);
    printArr(arr2, size);
}
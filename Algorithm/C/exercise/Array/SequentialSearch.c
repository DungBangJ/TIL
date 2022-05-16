int SequentialSearch(int arr[], int size, int value)
{
    int i;

    i = 0;
    while (i < size)
        if (arr[i++] == value)
            return (i);
    return (-1);
}
/*
#include <stdio.h>

int main(void)
{
    int arr[6] = {3, 2, 1, 6, 5, 4};
    printf("the index of the value = %d\n", SequentialSearch(arr, 6, 1));
}
*/
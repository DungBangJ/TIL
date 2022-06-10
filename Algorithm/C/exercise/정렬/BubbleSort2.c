void    BubbleSort2(int arr[], int size)
{
    int i, j, tmp, swapped = 1;
    for (i = 0; i < (size - 1) && swapped; i++)
    {
        swapped = 0;
        for (j = 0; j < size - 1 - i; j++)
        {
            if (arr[j] > arr[j + 1])
            {
                tmp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = tmp;
                swapped = 1;
            }
        }
    }    
}

#include <stdio.h>

int main(void)
{
    int arr[10] = {3, 7, 5, 8, 2, 1, 0, 9, 4, 6};
    BubbleSort2(arr, 10);
    for (int i = 0; i < 10; i++)
        printf("%d ", arr[i]);
    printf("\n");
}
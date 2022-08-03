#include <stdio.h>
#include <stdlib.h>

void    BucketSort(int arr[], int size , int range)
{
    int i, j = 0;
    int *count = (int *)malloc(sizeof(int) * (range + 1));
    
    for (i = 0; i < range; i++)
        count[i] = 0;
    
    for (i = 0; i < size; i++)
        count[arr[i]]++;
    
    for (i = 0; i < range; i++)
        for (; count[i] > 0; count[i]--)
            arr[j++] = i;
    
    free(count);
}

int main(void)
{
    int arr[10] = {2, 6, 4, 1, 5, 8, 1, 4, 6, 1};
    BucketSort(arr, sizeof(arr) / sizeof(int), 10);
    for (int i = 0; i < 10; i++)
        printf("%d ", arr[i]);
    printf("\n");
}
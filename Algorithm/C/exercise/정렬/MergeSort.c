#include <stdlib.h>

void    Merge(int *arr, int *temparr, int lowerIndex, int middleIndex, int upperIndex)
{
    int lowerStart = lowerIndex;
    int lowerStop = middleIndex;
    int upperStart = middleIndex + 1;
    int upperStop = upperIndex;
    int count = lowerIndex;
    while (lowerStart <= lowerStop && upperStart <= upperStop)
    {
        if (arr[lowerStart] < arr[upperStart])
            temparr[count++] = arr[lowerStart++];
        else
            temparr[count++] = arr[upperStart++];
    }
    while (lowerStart <= lowerStop)
        temparr[count++] = arr[lowerStart++];
    while (upperStart <= upperStop)
        temparr[count++] = arr[upperStart++];
    for (int i = lowerIndex; i <= upperIndex; i++)
        arr[i] = temparr[i];
}

void MergeSortUtil(int *arr, int *temparr, int lowerIndex, int upperIndex)
{
    if (lowerIndex >= upperIndex)
        return ;
    int middleIndex = (lowerIndex + upperIndex) / 2;
    MergeSortUtil(arr, temparr, lowerIndex, middleIndex);
    MergeSortUtil(arr, temparr, middleIndex + 1, upperIndex);
    Merge(arr, temparr, lowerIndex, middleIndex, upperIndex);
}

void    MergeSort(int *arr, int size)
{
    int *temparr = (int *)malloc(sizeof(int) * size);
    MergeSortUtil(arr, temparr, 0, size - 1);
}

#include <stdio.h>

int main(void)
{
    int arr[10] = {0, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    MergeSort(arr, 10);
    for (int i = 0; i < 10; i++)
        printf("%d", arr[i]);
    printf("\n");
}
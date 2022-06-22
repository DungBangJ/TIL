void    QuickSortUtil(int arr[], int lower, int upper)
{
    int tmp;
    if (upper <= lower)
        return ;
    int pivot = arr[lower];
    int start = lower;
    int stop = upper;
    while (lower < upper)
    {
        while (arr[lower] <= pivot)
            lower++;
        while (arr[upper] > pivot)
            upper--;
        if (lower < upper)
        {
            tmp = arr[lower];
            arr[lower] = arr[upper];
            arr[upper] = tmp;
        }
    }
    
    tmp = arr[upper];
    arr[upper] = arr[start];
    arr[start] = tmp;
    QuickSortUtil(arr, start, upper - 1); //upper의 자리는 현재 pivot의 자리
    QuickSortUtil(arr, upper + 1, stop);
}

void    QuickSort(int arr[], int size)
{
    QuickSortUtil(arr, 0, size - 1);
}

#include <stdio.h>

int main(void)
{
    int arr[10] = {0, 9, 8, 7, 6, 5, 4, 3, 2, 1};
    QuickSort(arr, 10);
    for (int i = 0; i < 10; i++)
        printf("%d", arr[i]);
    printf("\n");
}
#include <stdio.h>

void    swap(int *arr, int a, int b)
{
    int tmp;
    tmp = arr[a];
    arr[a] = arr[b];
    arr[b] = tmp;
}

void    QuickSelectUtil(int arr[], int lower, int upper, int k)
{
    if (upper <= lower)
        return ;

    int pivot = arr[lower];
    int start = lower;
    int stop = upper;

    while (lower < upper)
    {
        while (lower < upper && arr[lower] <= pivot)
            lower++;
        while (lower <= upper && arr[upper] > pivot)
            upper--;
        if (lower < upper)
            swap(arr, upper, lower);
    }
    
    swap(arr, upper, start); //upper는 pivot의 위치

    if (k < upper)
        QuickSelectUtil(arr, start, upper - 1, k);
    if (k > upper)
        QuickSelectUtil(arr, upper + 1, stop, k);
}

int QuickSelect(int *a, int size, int index)
{
    QuickSelectUtil(a, 0, size - 1, index - 1);
    return (a[index - 1]);
}

int main(void)
{
    int arr[10] = {4, 5, 3, 7, 6, 1, 9, 2, 10, 8};
    printf("5번째 원소 : %d\n", QuickSelect(arr, sizeof(arr) / sizeof(int), 5));
}
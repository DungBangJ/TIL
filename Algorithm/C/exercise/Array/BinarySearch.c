int BinarySearch(int arr[], int size, int value)
{
    int first;
    int last;
    int mid;

    first = 0;
    last = size - 1;
    while (first <= last)
    {
        mid = (first + last) / 2;
        if (arr[mid] == value)
            return (mid);
        if (arr[mid] > value)
            first = mid + 1;
        if (arr[mid] < value)
            last = mid - 1;
    }
    return (-1);
}
/*
#include <stdio.h>

int main(void)
{
    int arr[6] = {1, 2, 3, 4, 5, 6};
    printf("the number is at arr[%d]\n", BinarySearch(arr, 6, 3));
}
*/
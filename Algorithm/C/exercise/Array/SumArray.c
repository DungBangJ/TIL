int sumarray(int arr[], int size)
{
    int i;
    int sum;

    i = 0;
    sum = 0;
    while (i < size)
        sum += arr[i++];
    return (sum);
}
/*
#include <stdio.h>

int main(void)
{
    int arr[6] = {1, 2, 3,  4, 5, 6};
    printf("sum of the array = %d\n", sumarray(arr, 6));
}
*/
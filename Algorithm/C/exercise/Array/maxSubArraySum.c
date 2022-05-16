int maxSubArraySum(int arr[], int size)
{
    int i;
    int sum;

    sum = 0;
    i = 0;
    while (i < size - 1)
    {
        if (arr[i] + arr[i + 1] > sum)
            sum = arr[i] + arr[i + 1];
        i++;
    }
    return (sum);
}

#include <stdio.h>

int main(void)
{
    int arr[6] = {3, 4, 1, 3, 6, 7};
    printf("the maximum sum = %d\n", maxSubArraySum(arr, 6));
}
#include <stdio.h>

int FirstRepeated(int data[], int size)
{
    for (int i = 0; i < size; i++)
        for (int j = i + 1; j < size; j++)
            if (data[i] == data[j])
                return (data[i]);
    return (0);
}

int main(void)
{
    int arr[10] = {1, 2, 3, 4, 5, 6, 3, 8, 9, 10};
    int answer = FirstRepeated(arr, 10);
    printf("%d\n", answer);
}
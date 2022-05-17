int GCD(int a, int b)
{
    if (a < b)
        return (GCD(b, a));
    if (a % b == 0)
        return (b);
    return (GCD(b, a % b));
}

#include <stdio.h>

int main(void)
{
    printf("%d와 %d의 최대 공약수 = %d\n", 12 ,8, GCD(12, 8));
}
int factorial(unsigned int i)
{
    if (i <= 1)
        return (1);
    return (factorial(i - 1)  * i);
}

#include <stdio.h>

int main(void)
{
    printf("7! = %d\n", factorial(7));
}
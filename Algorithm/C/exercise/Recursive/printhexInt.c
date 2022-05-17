#include <stdio.h>

void    printhexInt(unsigned int number, const int base)
{
    char *coonversion = "0123456789ABCDEF";
    char digit = number % base;
    if (number /= base)
        printhexInt(number, base);
    printf("%c", coonversion[digit]);
}
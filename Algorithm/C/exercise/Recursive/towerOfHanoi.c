#include <stdio.h>

void towerOfHanoi(int num, char src, char dest, char tmp)
{
    if (num < 1)
        return ;
    towerOfHanoi(num - 1, src, tmp, dest);
    printf("\n 디스크 %d를 막대 %c에서 막대 %c로 옮겨라.", num, src, dest);
    towerOfHanoi(num - 1, tmp, dest, src);
}

int main(void)
{
    int num = 4;
    printf("하노이의 탑에서 이동은 다음과 같습니다.\n");
    towerOfHanoi(num, 'A', 'C', 'B');
}
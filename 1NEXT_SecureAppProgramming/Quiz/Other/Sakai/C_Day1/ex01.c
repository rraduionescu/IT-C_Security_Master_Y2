#include <stdio.h>
#include <malloc.h>

void main()
{
	char a = 0x61;
	char* pa = NULL;
	printf("\n a = %c, a = %d, pa = %p", a, a, pa);
	pa = &a;
	*pa = 0x62;	
	printf("\n a = %c, a = %d, pa = %p", a, a, pa);

	int x = 9;
	int* px = (int*)malloc(1 * sizeof(int));
	*px = 8;
	printf("\n x = %d, *px = %d, px = %p", x, *px, px);
	px[0] = 7;
	printf("\n x = %d, *px = %d, px = %p, &x = %p", x, *px, px, &x);
	px = &x;
	*px = 1;
	printf("\n x = %d, *px = %d, px = %p, &x = %p", x, *px, px, &x);
}
#include <stdio.h>
#include <malloc.h>

void main()
{
	char a = 0x61;
	char* pa = NULL;
	int x = 9;
	int* px = (int*)malloc(1 * sizeof(int));
	int** py = &px;
	
	printf("\n a = %c, a = %d, pa = %p", a, a, pa);
	pa = &a;
	*pa = 0x62;	
	printf("\n a = %c, a = %d, pa = %p", a, a, pa);

	*px = 8;
	printf("\n x = %d, *px = %d, px = %p", x, *px, px);
	px[0] = 7;
	printf("\n x = %d, *px = %d, px = %p, &x = %p", x, *px, px, &x);
	px = &x;
	*px = 1;
	printf("\n x = %d, *px = %d, px = %p, &x = %p", x, *px, px, &x);
	
	printf("\n px = %p, &px = %p, py = %p, &py = %p", px, &px, py, &py);
}
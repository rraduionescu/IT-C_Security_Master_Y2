#include <stdio.h>

void interChange(int a, int b) { // input params passed by values (copies)
	int aux = 0;
	aux = a;
	a = b;
	b = aux;
}

void interChange1(int *a, int *b) { // input params passed by references as addresses
	int aux = 0;
	aux = *a;
	*a = *b;
	*b = aux;
}

/* void interChange2(int &a, int &b) { // input params passed by references - specific to CPP, not allowed here
	int aux = 0;
	aux = a;
	a = b;
	b = aux;
} */

void pointerValue(int a, int *px) { // pointer passed by value
	px = &a;
}

void pointerAddress(int *a, int * *px) { // pointer passed by reference
	*px = a;
}

int main()
{
	int x = 9; int y = 7;
	int *px;
	printf("x = %d, y = %d", x, y);
	interChange(x, y);
	printf("\nx = %d, y = %d", x, y); // same contents for both variables after the call
	
	interChange1(&x, &y);
	printf("\nx = %d, y = %d", x, y); // different contents after the call

	//////////

	px = &x;
	printf("\npx = %p, *px = %d", px, *px);
	pointerValue(y, px); // pointer is passed by its value to the function
						// internal change of the pointer is not visible/available outside the function
	printf("\npx = %p, *px = %d", px, *px);

	pointerAddress(&y, &px);
	printf("\npx = %p, *px = %d", px, *px);
	
	return 0;
}
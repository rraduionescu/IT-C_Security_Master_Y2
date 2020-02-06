#include <stdio.h>
#include <malloc.h>

int* Add1(int m, int n, int matr[3][5]) {
	int i = 0, j = 0;
	int rez[3];
	for ( i = 0; i < m; i++) {
		rez[i] = 0;
		for ( j = 0; j < n; j++) {
			rez[i] += matr[i][j];
		}
	}
	return rez; // temporary memory address
}

int* Add2(int m, int n, int matr[3][5]) {
	int i = 0, j = 0;
	int *rez = (int*)malloc(m * sizeof(int));
	for (i = 0; i < m; i++) {
		rez[i] = 0;
		for (j = 0; j < n; j++) {
			rez[i] += matr[i][j];
		}
	}
	return rez; // persistent heap memory address; available from any function call
}

// use input parameter list to get the result (pointer by value vs pointer by reference)

void Add3(int m, int n, int matr[3][5], int *rez) {
	int i = 0, j = 0;
	rez = (int*)malloc(m * sizeof(int)); // rez passed by value

	for (i = 0; i < m; i++) {
		rez[i] = 0;
		for (j = 0; j < n; j++) {
			rez[i] += matr[i][j];
		}
	}	
}

void Add4(int m, int n, int matr[3][5], int **rez) {
	int i = 0, j = 0;
	*rez = (int*)malloc(m * sizeof(int)); // rez passed by reference

	for (i = 0; i < m; i++) {
		(*rez)[i] = 0;
		for (j = 0; j < n; j++) {
			(*rez)[i] += matr[i][j];
		}
	}
}

void main()
{
	int nCars; int nDays;
	int matrc[3][5];
	int* rezc = NULL;
	int i = 0, j = 0;

	printf(" No. of cars="); scanf("%d", &nCars);
	printf(" No. of days="); scanf("%d", &nDays);

	for (i = 0; i < nCars; i++) {
		for (j = 0; j < nDays; j++) {
			printf(" matrc[%d][%d]=", i, j);
			scanf("%d", &matrc[i][j]);
		}
	}

	rezc = Add1(nCars, nDays, matrc);
	printf(" \n Add1:");
	for (i = 0; i < nCars; i++)
		printf(" \n rezc[%d]=%d", i, rezc[i]); // random content for rezc (temporary memory address returned from Add1)

	rezc = Add2(nCars, nDays, matrc);
	printf(" \n Add2:");
	for (i = 0; i < nCars; i++)
		printf(" \n rezc[%d]=%d", i, rezc[i]); // right content for rezc (persistent heap memory address returned from Add2)
	
	// deallocate the rezc vector (avoid memory leaks)
	free(rezc);
	rezc = 0;

	Add4(nCars, nDays, matrc, &rezc); // pointer passed vy reference
	printf(" \n Add4:");
	for (i = 0; i < nCars; i++)
		printf(" \n rezc[%d]=%d", i, rezc[i]); // expected results

	// deallocate the rezc vector (avoid memory leaks)
	free(rezc);
	rezc = 0;

	Add3(nCars, nDays, matrc, rezc);
	printf(" \n Add3:");
	for (i = 0; i < nCars; i++)
		printf(" \n rezc[%d] = %d", i, rezc[i]); // rezc is null due to passing the pointer by value to Add3

	// deallocate the rezc vector (avoid memory leaks)
	free(rezc);
}
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
	return rez;
}

int* Add2(int m, int n, int matr[3][5]) {
	int i = 0, j = 0;
	//int rez[3];
	int* rez = NULL;
	rez = (int*)malloc(m*sizeof(int));
	for ( i = 0; i < m; i++) {
		rez[i] = 0;
		for ( j = 0; j < n; j++) {
			rez[i] += matr[i][j];
		}
	}
	return rez;
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

	rezc = Add2(nCars, nDays, matrc);

	for (i = 0; i < nCars; i++)
		printf(" \n rezc[%d]=%d", i, rezc[i]);

}
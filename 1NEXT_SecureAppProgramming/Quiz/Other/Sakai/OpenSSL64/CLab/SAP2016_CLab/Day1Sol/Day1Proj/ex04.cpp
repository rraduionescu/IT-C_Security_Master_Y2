//#include <stdio.h>
//#include <malloc.h>
//
//short int* Add1(int m, int n, short int** matr) {
//	int i = 0, j = 0;
//	short int rez[3];
//	for(i = 0; i < m; i++) {
//		rez[i] = 0;
//		for (j = 0; j < n; j++) {
//			rez[i] += matr[i][j];
//		}
//	}
//
//	return rez; // content could be changed when main function accesses it
//}
//
//short int* Add2(int m, int n, short int** matr) {
//	int i = 0, j = 0;
//	short int* rez;
//
//	rez = (short int*)malloc(m*sizeof(short int));
//	for(i = 0; i < m; i++) {
//		rez[i] = 0;
//		for (j = 0; j < n; j++) {
//			rez[i] += matr[i][j];
//		}
//	}
//
//	return rez; // heap memory address saved in stack seg frame of the main function
//}
//
//void Add3(int m, int n, short int** matr, short int* rez) {
//	int i = 0, j = 0;
//	//short int* rez;
//
//	rez = (short int*)malloc(m*sizeof(short int)); // issue: temporary location; 
//												   // stored heap memory address is not available in function main()
//	for(i = 0; i < m; i++) {
//		rez[i] = 0;
//		for (j = 0; j < n; j++) {
//			rez[i] += matr[i][j];
//		}
//	}
//
//	return; // could be removed
//}
//
//void Add4(int m, int n, short int** matr, short int** rez) { // rez passedby its address
//	int i = 0, j = 0;
//
//	*rez = (short int*)malloc(m*sizeof(short int)); // content of rez is addressed; 
//													// heap memory address of the result array will be landed 
//													// into stack segment frame of the function main()
//	for(i = 0; i < m; i++) {
//		(*rez)[i] = 0;
//		for (j = 0; j < n; j++) {
//			(*rez)[i] += matr[i][j];
//		}
//	}
//
//	return;
//}
//
//void main()
//{
//	int nCars = 0; 
//	int nDays = 0;
//	int i=0, j=0;
//
//	//short int matrc[3][5];
//	short int** matrc = NULL;
//	short int* rezc = NULL;
//
//	printf(" No. of cars="); scanf("%d", &nCars);
//	printf(" No. of days="); scanf("%d", &nDays);
//
//	matrc = (short int**)malloc(nCars * sizeof(short int*));
//	for (i = 0; i <nCars; i++) {
//		matrc[i] = (short int*)malloc(nDays * sizeof(short int));
//	}
//
//	for(i = 0; i < nCars; i++) {
//		for(j = 0; j < nDays; j++) {
//			printf("matrc[%d][%d] = ", i, j);
//			scanf("%d", &matrc[i][j]);
//		}
//	}
//
//	while (1) { // memory leaks will lead to increased memory requirements
//				// watch Task Manager during execution of this loop
//		/*rezc = Add1(nCars, nDays, matrc);
//		for (i = 0; i < nCars; i++)
//			printf("\n rezc[%d]=%d", i, rezc[i]);*/
//	
//		/*rezc = Add2(nCars, nDays, matrc);
//		for (i = 0; i < nCars; i++)
//			printf("\n rezc[%d]=%d", i, rezc[i]);*/
//
//		/*Add3(nCars, nDays, matrc, rezc);
//		for (i = 0; i < nCars; i++)
//			printf("\n rezc[%d]=%d", i, rezc[i]);*/
//
//		Add4(nCars, nDays, matrc, &rezc);
//		for (i = 0; i < nCars; i++)
//			printf("\n rezc[%d]=%d", i, rezc[i]);
//	}
//}
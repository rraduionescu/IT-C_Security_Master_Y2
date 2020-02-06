//#include <stdio.h>
//#include <malloc.h>
//#include <string.h>
//
//struct Product {
//	int id;
//	char* name;
//	float stock;
//	float price;
//};
//
//void sumsStocksStore(int m, int n, struct Product*** mat, double** rez) {
//	int i=0, j=0;
//
//	if (*rez) {
//		free(*rez); // deallocate existing heap memory before a new allocation
//	}
//	*rez =(double*)malloc(m*sizeof(double)); // allocated heap memory address stored 
//											 // in stack seg frame of the function main()
//
//	for(i=0;i<m;i++){
//		(*rez)[i] = 0;
//		for(j=0;j<n;j++){
//			(*rez)[i] += mat[i][j]->price * mat[i][j]->stock;
//		}
//	}
//
//}
//
//void main()
//{
//	int nStores = 0; int nProducts = 0;
//	struct Product*** matr = NULL;
//	double* rezStocks = NULL;
//	struct Product* part = NULL;
//	char buf[10];
//	int i = 0, j = 0;
//
//	printf(" No. of stores="); scanf("%d", &nStores);
//	printf(" No. of products="); scanf("%d", &nProducts);
//	
//	matr = (struct Product***)malloc(nStores * sizeof(struct Product**));
//	for (i = 0; i < nStores; i++) {
//		matr[i] = (struct Product**)malloc(nProducts * sizeof(struct Product*));
//		for (j = 0; j < nProducts; j++) {
//			part = (struct Product*)malloc(1 * sizeof(struct Product));
//			printf("matr[%d][%d].id = ", i, j); scanf("%d", &part->id);
//
//			printf("matr[%d][%d].name = ", i, j); 
//			scanf("%s", &buf); // buf reused to store names for succesive products
//			part->name = (char*)malloc((strlen(buf) + 1) * sizeof(char)); // one more byte to store the terminator byte (0x00)
//			strcpy(part->name, buf);
//
//			printf("matr[%d][%d].stock = ", i, j); scanf("%f", &part->stock);
//			printf("matr[%d][%d].price = ", i, j); scanf("%f", &part->price);
//
//			matr[i][j] = part; // heap memory address of part saved into a location of input bi-dim array
//		}
//	}
//
//	//while(1) { // memory leaks; explicit deallocation needed
//		sumsStocksStore(nStores, nProducts, matr, &rezStocks);
//		for (i = 0; i < nStores; i++)
//			printf("\n rezStoc[%d] = %lf", i, rezStocks[i]);
//	//}
//
//	// deallocate the bidimensional array (Products)
//	for (i = 0; i < nStores; i++) {
//		for (j = 0; j < nProducts; j++) {
//			free(matr[i][j]->name); // deallocate the string namefor each product
//			free(matr[i][j]); // deallocate the product 
//		}
//
//		free(matr[i]); // deallocate line i containing pointers to products
//	}
//
//	free(matr); // deallocate the array with pointers to the bi-dim array lines
//}
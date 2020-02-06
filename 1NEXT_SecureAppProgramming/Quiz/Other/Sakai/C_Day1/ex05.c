#include <stdio.h>
#include <malloc.h>
#include <string.h>

struct Product {
	int id;
	char* name;
	float stock;
	float price;
};

void sumsStocksStore(int m, int n, struct Product*** mat, double** rez) {
	int i=0,j=0;

	*rez =(double*)malloc(m*sizeof(double));

	for(i=0;i<m;i++){
		(*rez)[i] = 0;
		for(j=0;j<n;j++){
			(*rez)[i] += mat[i][j]->price * mat[i][j]->stock;
		}
	}

}

void main()
{
	int nStores = 0; int nProducts = 0;
	struct Product*** matr = NULL;
	double* rezStocks = NULL;
	struct Product* part = NULL;
	char buf[10];
	int i = 0, j = 0;

	printf(" No. of stores="); scanf("%d", &nStores);
	printf(" No. of products="); scanf("%d", &nProducts);
	
	matr = (struct Product***)malloc(nStores * sizeof(struct Product**));
	for (i = 0; i < nStores; i++) {
		matr[i] = (struct Product**)malloc(nProducts * sizeof(struct Product*));
		for (j = 0; j < nProducts; j++) {
			part = (struct Product*)malloc(1 * sizeof(struct Product));
			printf("matr[%d][%d].id = ", i, j); scanf("%d", &part->id);

			printf("matr[%d][%d].name = ", i, j); 
			scanf("%s", &buf);
			part->name = (char*)malloc((strlen(buf) + 1) * sizeof(char));
			strcpy(part->name, buf);

			printf("matr[%d][%d].stock = ", i, j); scanf("%f", &part->stock);
			printf("matr[%d][%d].price = ", i, j); scanf("%f", &part->price);

			matr[i][j] = part;
		}
	}

	while(1) {
		sumsStocksStore(nStores, nProducts, matr, &rezStocks);
		for (i = 0; i < nStores; i++)
			printf("\n rezStoc[%d] = %lf", i, rezStocks[i]);
	}
}
//#include <stdio.h>
//#include <malloc.h>
//
//
//template <typename T> void Sum1(T x, T y){
//	x = x + 3;
//	y = y + x;
//}
//
//template <typename P> void Sum2(P *x, P y){
//	*x = *x + 3;
//	y = y + *x;
//}
//
//template <typename Q> void Sum3(Q *x, Q y){
//	*x = *x + 3;
//	x = &y;
//	*x = *x + 5;
//	y = y + *x;
//}
//
//template <typename T> void Sum4(T* *x, T y){
//	**x = **x + 3;
//	*x = (T*)malloc(1*sizeof(T));
//	**x = **x + 5;
//	y = y + **x;
//}
//
//
//template <typename T> T* SumRows(T mat[3][5], int m, int n){
//	int i, j;
//	T *rez=(T*)malloc(m*sizeof(T));
//
//	for(i=0; i<m; i++){
//		rez[i] = 0;
//		for(j=0; j<n; j++)
//			rez[i] += mat[i][j];
//	}
//
//	return rez;
//}
//
//
//void main(){
//	float a, b;
//	a = 4.1;
//	b = 8.2;
//	
//	Sum1(a,b);
//	Sum2(&a, b);
//	printf("a=%d, b=%d \n", a, b);
//	float *pa;
//	pa=&a;
//	printf("pa=%p, b=%d \n", pa, b);
//	Sum3(pa, b);
//	Sum4(&pa, b);
//	float **ppa;
//	ppa = &pa;
//	printf("pa=%p, b=%d \n", pa, b);
//
//
//	int M[3][5], m, n, i, j;
//	printf("\nNr. Linii:");
//	scanf("%d",&m);
//	printf("\nNr. coloane:");
//	scanf("%d",&n);
//
//	for(i=0; i<m; i++)
//		for(j=0; j<n; j++)
//			M[i][j] = i*1+j;
//
//	int *vSum=0;
//	vSum = SumRows(M,m,n);
//	for(i=0; i<m; i++)
//			printf(" %d ", vSum[i]);
//
//}
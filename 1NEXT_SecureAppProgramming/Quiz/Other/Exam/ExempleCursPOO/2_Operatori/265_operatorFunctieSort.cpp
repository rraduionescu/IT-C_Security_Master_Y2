#include <iostream>
using namespace std;

class less
   {
	public:
		bool operator() (int a, int b ) {  return f(a,b);  }
		bool f(int a, int b) {return a < b ;}
   };


class greater
   {
	public:
		bool operator() (int a, int b ) {  return f(a,b);  }
		bool f(int a, int b) {return a > b ;}
   };


void comuta(int &a, int &b)
{ 	int temp;	temp = a; a=b; b=temp; }

void sort( int x[], int n, less obj )
{
	register i,j;
	for(i=0;i<n-1;i++)
		for(j=i+1;j<n;j++)
			if( ! obj(x[i],x[j]) )
				comuta(x[i],x[j]);
}

void sort( int x[], int n, greater obj )
{
	register i,j;
	for(i=0;i<n-1;i++)
		for(j=i+1;j<n;j++)
			 if( ! obj(x[i],x[j]) )
				comuta(x[i],x[j]);
}
void scrie( int v[], int n)
{
	cout << "\n";
	for(int i=0; i<n; i++)   cout<< v[i] << '\t';
}

void main()
  {
    	int a=1,b=2;	less mai_mic; greater mai_mare;
		int v[]={2,6,3,-5,7,9,4,1,8,-23,6};
		sort(v,sizeof(v)/sizeof(v[0]), mai_mic);   scrie(v,sizeof(v)/sizeof(v[0]) );
		sort(v,sizeof(v)/sizeof(v[0]), mai_mare);  scrie(v,sizeof(v)/sizeof(v[0]) );
		getchar();
 }



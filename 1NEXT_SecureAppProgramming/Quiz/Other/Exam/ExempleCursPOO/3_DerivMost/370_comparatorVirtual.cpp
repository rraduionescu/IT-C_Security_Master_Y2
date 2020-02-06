#include <iostream>
using namespace std;

class Comparator
{
public: virtual bool operator() (int , int  )=0;
};

class Less:  public Comparator
   {
	public:
		bool operator() (int a, int b ) {  return a < b;  }
   };


class Greater: public Comparator
   {
	public:
		bool operator() (int a, int b ) {  return a > b;  }
   };


void comuta(int &a, int &b)
{ 	int temp;	temp = a; a=b; b=temp; }



void sort( int x[], int n, Comparator & obj )
{
	register int i,j;
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
    	int a=1,b=2;	Less mai_mic; Greater mai_mare;
		int v[]={2,6,3,-5,7,9,4,1,8};
		sort(v,sizeof(v)/sizeof(v[0]), mai_mic);   scrie(v,sizeof(v)/sizeof(v[0]) );
		sort(v,sizeof(v)/sizeof(v[0]), mai_mare);  scrie(v,sizeof(v)/sizeof(v[0]) );
 }
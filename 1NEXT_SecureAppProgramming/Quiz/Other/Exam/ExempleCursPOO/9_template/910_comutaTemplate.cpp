#include <iostream>
using namespace std;
#include <string.h>

template <class TIP>
void comuta(TIP &a, TIP &b) {  TIP aux;  aux=a, a=b,b=aux;   }


//#define COMUTA_GEN(TIP)   void comuta(TIP& x,TIP& y) \
//	{	TIP t;  t=x;   x=y;  y=t;		}
//
//COMUTA_GEN(int);
//COMUTA_GEN(double);


void main( )
{	
	double x=1.1, y=2.2;
	comuta( x,y);	cout <<x<<" "<<y<<endl;
	int a=1,b=2;
	comuta( a,b);	cout <<a<<" "<<b<<endl;
	cin >> x;
}
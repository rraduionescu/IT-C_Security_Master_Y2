#include "stdafx.h"

#include <iostream>
using namespace std;
class X;
class Y
{		int y;
	public:
		Y(int n=0):y(n) {   }
		explicit Y(X );
};

class X
{	
	public:
		int x;
	 	operator Y() { cout << "\nY cast";   return Y(x); }
};

Y::Y(X  ox) : y(ox.x)	{    cout << "\nY cons";   }

void f(Y oy) { }

void main( ) 
 {
	X ox; Y oy;
	oy = ox;
	f( Y(ox) );
	f( ox );getchar();
 }
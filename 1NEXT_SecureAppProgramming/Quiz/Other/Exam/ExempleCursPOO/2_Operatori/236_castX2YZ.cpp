#include <iostream>
using namespace std;

class Y
{		int y;
	public:
		Y(int n = 0): y(n) { }
};

class Z
{		int z;
	public: 
		Z(int n=0):z(n)		{ }
};

class X
{	
	public:
		int x;
	 	operator Y( ) { cout << "\nY cast";  return Y(x); }
		operator Z( ) { cout << "\nZ cast";   return Z(x); }
};

void f(Y oy) { }
void f(Z oz) { }

void main( ) 
 {
	X ox; 
	//f( ox );
  	f( (Y)ox );getchar();
 }

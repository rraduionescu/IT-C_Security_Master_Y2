#define M_PI 3.1415926535

#include <math.h>
#include <iostream>
using namespace std;

class neagaTot
{
public:
	float operator()  (float x) const { return -x; }
	double operator() (double (*pf) (double), double x) {return -(*pf)(x); }
};

int main()
{
	neagaTot Gica;
	 float x = -12.f;
	 cout << "\n pe double:    "<< Gica(x) <<  endl;
	cout << "\n pe functie:    "<< Gica(sin, 30*M_PI/180.) <<  endl; // ??
	cout << "\n pe functie:    "<< Gica(sin, Gica(30*M_PI/180.)  ) <<  endl;
	getchar();
}


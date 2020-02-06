#include <iostream>
using namespace std;
#include <string.h>
class persoana
 {
public: 
	persoana( char *n="Noname") { strcpy(nume,n); }
	char nume[50];
	friend ostream& operator<<(ostream &out, persoana p)
			{ out << p.nume; return out; }
};

template <class TIP>
void comuta ( TIP &a, TIP &b )   {  TIP aux;  aux = a, a = b,b = aux;   }

void main( )
{	
	double x = 1.1, y =2.2;
	comuta( x,y);	cout <<x<<" "<<y<<endl;
	persoana a ("A"), b ("B");
	comuta( a,b);	cout <<a<<" "<<b<<endl;
	cin >> x;
}

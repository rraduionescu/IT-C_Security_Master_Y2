#include "stdafx.h"

using namespace std;
#include <iostream>

#include <string.h>
class persoana
    {
       public:
	char nume[20];
	double salariu;
	persoana(char *n="Anonim  ",double s=0.) : salariu(s) 
						{  strcpy(nume,n);   }
	persoana( persoana &p ) { p.salariu=0.;}
	operator double( ) { return salariu; }
	double cumul( double s) { return s + salariu ;}
     };

void main( )
  {
    double  s=1000000.;
    persoana p1("Popa Ion  ",85000.);
    persoana p2("Popa Elena",87000.);
    persoana p3("Adamescu Virgil",75000);
// 1
    cout << "\n"  << p3.nume<<" are "<< (double)p3 << " lei.";
// 2 
    cout << "\nFamilia " << p1.nume<<" are "<< p1.cumul(p2)<<" lei";
// 3
    s =  p1 + p2;
    cout << "\nFamilia " << p1.nume<<" are "<< s<<" lei";
// 4
   cout << "\nTrei persoane "  << " au " << p1+p2+p3 << " lei";
// 5
cout << "\nCele trei persoane "	<< " au impreuna " 
        << p1.cumul(p2) + p3 << " lei";
// 6
   cout << "\nLe lipsesc 53000. " << " pentru a avea impreuna "
	   << p1+p2+p3+53000 << " lei"<<endl;
  }

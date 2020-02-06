#include "stdafx.h"
#include <iostream>
using namespace std;

class Pers
    {
      private:	
	friend double operator+( Pers &, double );
	friend double operator+( double, Pers & );
      public:
	char nume[20];
	double salariu;
	Pers(char *n="Anonim  ",double s=0):salariu(s)
	    		{  strcpy(nume,n);  }

	double operator+( Pers& );
	double operator+=( double );	 	
     };

double operator+( Pers &p, double spor)    { return p.salariu + spor ; }
double operator+( double spor, Pers &p)    { return p.salariu + spor ; }
double Pers::operator+(Pers &p)      {  return salariu + p.salariu ; }
double Pers::operator+=( double spor)       { return salariu += spor ;  }

void main( )
  {
    double spor = 1.;
    Pers p1("Popa Ion",85000.), p2=Pers("Popa Elena",87000.);
    Pers p3("Adamescu Virgil",75000);
    cout << "\nDupa spor " << p3.nume<<" ar avea " << p3 + spor;
    p3+=spor;
    cout << "\n"  << p3.nume <<" chiar are acum "  << p3.salariu    << " lei.";
    cout << "\nFamilia " << p1.nume << " are "  <<   p1+p2  << " lei\n";
	getchar();
  }


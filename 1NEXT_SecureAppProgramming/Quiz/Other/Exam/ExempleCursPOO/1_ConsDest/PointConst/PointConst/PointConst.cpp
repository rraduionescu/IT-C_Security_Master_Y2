#include "stdafx.h"
#include <iostream>
using namespace std;

class Pers
    {
      public:
		int varsta;
		double salariu;
		Pers(int v=1, float s=1000000. ) :
		 				varsta(v), salariu(s) { }
		int getVarsta( ) const { return varsta; }
		double setSalariu( )   { return salariu; }
		void f_m (const Pers p) {}
     };

void main( )
{
	Pers const p1; Pers p2;
	Pers * pvov;
	Pers * const pcov = &p2;
	Pers const * pvoc; 
	Pers const * const pcoc=&p1;
	pvov=&p2;
	// Pers * const pcov=&p1;
	// pvov =&p1; 
	// pvov =&p2;
	 //pcov->getVarsta( );
 	 // pvoc = &p2;
	// pvov->f_m(p2); 
	cout << "p1 are varsta " << p1.getVarsta();
	getchar();
     }


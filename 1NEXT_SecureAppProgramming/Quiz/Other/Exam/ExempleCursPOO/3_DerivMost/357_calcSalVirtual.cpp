// 357_calcSalVirtual.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <iostream>
using namespace std;

class Pers
    {
     private:	float salariu;
     public:	char nume[20];
		 virtual void calc_sal( )  { cout << "\n Salariu persoanei"; }
     };

class Inginer : public Pers
      {
public:	void calc_sal( )    { cout << "\n Salariu in regie"; }
      };

class Muncitor : public Pers
      {
       	public:	void calc_sal( )   { cout << "\n Salariu in acord"; }
      };

void main( )
   {
      Pers p, *pp; Inginer i,*pi; Muncitor m, *pm;
      pp = &p; pi=&i; pm=&m;
      cout << "\nFolosind obiecte si pointeri la obiecte: ";
      p.calc_sal( ); pp->calc_sal( );
      i.calc_sal( ); pi->calc_sal( );
      m.calc_sal( ); pm->calc_sal( );
      cout << "\nFolosind conversia in pointer la obiect de baza: ";
      pp=pi; pp->calc_sal( );
      pp=pm; pp->calc_sal( );
      cout << "\nFolosind conversia in obiect de baza: ";
      p=i; p.calc_sal( );
      p=m; p.calc_sal( );
	  getchar();
   }

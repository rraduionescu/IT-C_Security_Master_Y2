#include "stdafx.h"
#include <fstream>
#include <string.h>
#include <stdlib.h>

#include <iostream>
using namespace std;
class Pers
  {    
      public:
		char nume[30]; int virsta;
		friend ostream & operator<< ( ostream &, Pers  );
		friend istream & operator>> ( istream &, Pers &);
		friend ifstream & operator>> ( ifstream &, Pers &);
   };
ostream & operator<< ( ostream & iesire, Pers p )
      {    iesire <<p.nume << " " << p.virsta << endl;	return iesire;      }
istream & operator>> ( istream & intrare, Pers & p )
      {
   	cout <<"Nume:   "; intrare>> p.nume;
	cout <<"Virsta: "; intrare>> p.virsta;
	return intrare;
      }
ifstream & operator>> ( ifstream & intrare, Pers & p )
      		{    intrare >>p.nume >>p.virsta;	return intrare;      }

void main( )
 {
   Pers p1;
   cin >>p1;     // incarcare obiect
   cout <<p1;    // afisare obiect
    {
     // scriere obiect în fisier pe disc
     ofstream fisout("FIS.DAT");
     if(!fisout) { cout <<"\nEroare de alocare fisier"; exit(1); }
     fisout <<p1;     fisout.close( );
   }
   {
     // citire obiect din fisier pe disc si afisare pe ecran
     ifstream fisin("FIS.DAT");
     if(!fisin) { cout <<"\nFisier negasit!"; exit(1); }
     fisin >>p1;     cout << "\nS-a citit din fisier: "; cout <<p1;    fisin.close( );
   }
   fflush(stdin); getchar();
 }


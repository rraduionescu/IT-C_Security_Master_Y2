#include <iostream>
#include <string>
using namespace std;
class Pers
    {
      private: 	int virsta;
			friend ostream & operator<<(ostream & ies, Pers p)
					{ ies << endl<< p.nume << " " << p.virsta; return ies;}
      public: 	char nume[20];
		Pers(char *n="Anonim  ", int v=0 ):virsta(v)
	    	    			{  strcpy(nume,n); }
	 	int spune_virsta( ) { return virsta; }
     };
 
class Stud: public Pers
  	{ 	
			public: 	int matricol; 
	} ;

void main( )
  {
    Pers p1;    Stud s1, s2;
    cout << "\n"  << s1.nume  << p1.nume  << s2.nume; // trece si prin cons baza
	
    s1.matricol=101; strcpy(s1.nume,"Transferat");
    cout << "\n"  << s1.matricol <<" "<< s1.nume  <<" "<< s1.spune_virsta( );
	cout << s1 ; // mosteneste metoda + friend + conv implicita in ob baza

    Pers *pp = &s1;    // conv fortata de pointer
	cout << "\n"  << (*pp).nume  <<" "<< pp->spune_virsta( ); getchar();
  }
#include "stdafx.h"
#include <set>
#include <iostream>
using namespace std ;

class Pers 
{
public:
	int varsta; char nume[50];
	Pers(int v=0, char * n="Anonim") : varsta(v)  { strcpy(nume,n); }
	bool operator<(const Pers p2) const { return varsta < p2.varsta ? true:false; }
	friend ostream & operator<<(ostream & o, Pers &p)
		{ 		o<<"\n"<<p.nume<< "\t\t" << p.varsta; 	return o; 	}
	friend istream & operator>>(istream & i, Pers &p)
		{ 		i>>p.nume>>p.varsta; return i; 	}
	
};

void main()
{
  Pers p[10]={ Pers(45,"Mateescu Dan"), Pers(25,"Ionescu Tudor") };
  set< Pers> echipa_1;
  set< Pers, less<Pers>, allocator<Pers> > echipa_2;
  echipa_1.insert(p[1]);	echipa_1.insert(p[6]);
  cout << "\n"<< p[0].nume << " este " <<
 	   (echipa_1.key_comp()( p[0],  p[1])  ? 
 	  " mai tinar(a) ": " mai batrin(a) " ) 
					<< " decat " << p[1].nume << endl;
  
  set< Pers>::iterator iter_pers;
  p[5].varsta=25; 
  iter_pers = echipa_1.find(p[5]);
  //	iter_pers = echipa_1.find(p[0]);
  iter_pers!= echipa_1.end() ? 
	 cout << "\n Gasit: "<< iter_pers->nume  : cout << "\n Negasit ! ";
  system("pause");
}


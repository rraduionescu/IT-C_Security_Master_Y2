#include "stdafx.h"


#include <iostream>
using namespace std;

class Pers
{
private:
	int varsta;
public:
	char nume[100];
	int getVarsta() const { return varsta; }

	friend ostream & operator<<(ostream & ies, const Pers &p)
	{
		ies << "\n" << p.nume <<"   " << p.varsta;
		return ies;
	}
	Pers(int v=0, char *n="Noname"): varsta(v)
	{
		strcpy(nume,n);
	}
	~Pers()
	{
		
	}

	
	/*Pers (Pers &srs):varsta(srs.varsta)
		{
			pnume = new char[strlen(srs.pnume)+1]; strcpy(pnume,srs.pnume);
		}*/



};


void main()
{
	
	const Pers p1(20,"Elena"); Pers p2;
	//p1=p2;
	 const Pers * const pp  =&p2;  ;  cout << pp->nume;

	//cout << p1.getVarsta();
	getchar();

}


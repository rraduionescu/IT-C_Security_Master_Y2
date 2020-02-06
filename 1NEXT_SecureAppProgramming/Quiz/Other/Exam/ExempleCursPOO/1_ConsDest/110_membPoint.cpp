#include "stdafx.h"

#include <iostream>
using namespace std;

class Pers
{
	int varsta;
public:
	char *pnume;
	Pers (int v=0, char *n="Noname"):varsta(v)
	{
		pnume = new char[strlen(n)+1]; strcpy(pnume,n);
	}
	friend ostream & operator<<(ostream & ies, Pers &p)
	{
		ies << "\n" << p.pnume <<"   " << p.varsta;
		return ies;
	}
	~Pers()
	{
		delete [] pnume;
	}
	Pers & operator=(Pers & p2)
	{
		delete [] pnume;
		pnume = new char[strlen(p2.pnume)+1]; strcpy(pnume,p2.pnume);
		this->varsta=p2.varsta;
		return *this;
	}
	Pers (Pers &srs):varsta(srs.varsta)
		{
			pnume = new char[strlen(srs.pnume)+1]; strcpy(pnume,srs.pnume);
		}
};


void main()
{
	Pers p1(20,"Elena"), p2,p3=p1;
	cout <<p1<<p2;
	p2=p1;cout <<p3;
	getchar();

}

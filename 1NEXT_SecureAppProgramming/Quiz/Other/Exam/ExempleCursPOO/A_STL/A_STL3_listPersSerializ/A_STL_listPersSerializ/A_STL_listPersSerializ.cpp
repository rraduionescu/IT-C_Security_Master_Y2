#include "stdafx.h"

#include <iostream>
#include <fstream>
#include <list>
using namespace std;

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
	friend ofstream & operator<<(ofstream & f, Pers &p)
		{ 		f<<p.nume<<" "<<p.varsta; 	return f; 	}
};

void main()
{
	list<Pers*> lpp;
	
	lpp.push_back(new Pers(60, "Savulescu_Gh"));
	lpp.push_back(new Pers(70, "Septimiu_X"));lpp.push_back(new Pers(50, "Ciolacu_P"));
	lpp.sort();
	list<Pers*>::iterator iter_pers;
	// serializare cu iterator
	ofstream fisOut("fisPers.dat");
	for(iter_pers = lpp.begin(); iter_pers != lpp.end(); iter_pers++ ) fisOut << **iter_pers;
    lpp.clear(); fisOut.close();
	
	ifstream fisInp("fisPers.dat");	
	//deserializare
	Pers * pp=new Pers(); lpp.push_back(pp); fisInp>>*pp;
	while(!fisInp.eof())
	{
		pp=new Pers() ;lpp.push_back(pp); fisInp>>*pp;
	}
	//lpp.sort();
	for(iter_pers = lpp.begin(); iter_pers != lpp.end(); iter_pers++ ) cout << **iter_pers;
	fisInp.close();

	system("pause");
}
#include "stdafx.h"
#include <iostream>
using namespace std;

class persoana
{
public:	
	int virsta;
	persoana(int v=0):virsta(v) { }
	friend   const persoana & operator++(persoana &a)	    // prefixat
	{
			a.virsta++;
			return a;
	}
	friend   const persoana  operator++(persoana &a, int)  // postfixat 
	{
		persoana aux=a; // conservarea starii initiale
		a.virsta++;
		return aux;
	}
};

void main( )
{
	persoana p1(25),p2;
	p2=++p1; cout << p2.virsta<<" ";
	p2=p1++; cout << p2.virsta<<" "; 
	cout << p1.virsta;
getchar();
}

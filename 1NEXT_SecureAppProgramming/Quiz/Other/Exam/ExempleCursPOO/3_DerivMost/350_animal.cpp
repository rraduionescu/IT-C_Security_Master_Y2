#include "stdafx.h"

#include <iostream>
using namespace std;

class Animal
{
	public: 
		/* virtual */ void Speak(){ cout << "\t Speaks"; }
};

class Bird: public Animal
{
public:
	void Speak()
	{cout << "\t Ciripeste"; }
};

class Horse: public Animal
{
public:
	void Speak()
	{cout << "\t Necheaza"; }
};


void main()
{
	auto Animal a; static Animal b; Horse c; Bird p;
	Animal *pa[]={&a,&c,&p};
	for(int i=0; i<sizeof(pa)/sizeof(pa[0]); i++)
		(*pa[i]).Speak();
	getchar();
}

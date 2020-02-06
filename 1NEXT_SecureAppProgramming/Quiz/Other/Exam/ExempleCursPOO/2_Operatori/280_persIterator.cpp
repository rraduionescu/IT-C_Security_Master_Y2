#include <iostream>
using namespace std;

#include <string.h>
#include <stdio.h>

class Persoana
 {
    public:
	   Persoana(char *nm="Anonymus") { strcpy(nume,nm); }
	   char nume[50];	
};

class Container 
{
	static Persoana * vpp[100];
	static int total_pers;
    public:
	const static int dim;
	Container()
		{
			total_pers = 0;
			memset(vpp,NULL,dim * sizeof(Persoana*));
		}
static	void add()
		{
			if(total_pers >= dim) return;
			char aux[50];
			sprintf(aux,"Pers_%3d ", total_pers);
			vpp[total_pers++] = new Persoana( aux);
		}
friend class Iterator;
};

const int Container::dim = 100;
int Container::total_pers= 0;
Persoana * Container::vpp[100];

class Iterator 
{
	Container* grup ;
	int index;
  public:
	Iterator(Container* pop)
		{	index = 0;	grup = pop; }

int operator++()
 { 
    if((grup->vpp[++index] == NULL)|| (index >=  grup->dim) )
		return 0;
    else	return 1;
}
Persoana* operator->() const 
	{
	if(grup->vpp[index])		return grup->vpp[index];
	static Persoana nil;		return &nil;
	}
};

void main() 
{
	const int nrp = 10;
	Container pop;
	for(int i =0;i < nrp;i++)		pop.add(); 
	Iterator sp(&pop);
	do {	cout << sp->nume << endl;	} while(++sp);
	getchar();
}


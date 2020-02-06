#include "stdafx.h"

#include <string.h>
#include <iostream>
using namespace std;

class Index;

class Pers
 {		int marca;
			friend class Index;
		public:
		
     		char nume[50];
			Pers( char *, int);
			~Pers();
			static Index bdPers;
  };

class Index
  {
		friend class Pers;
		Pers *lista[100]; int n;
		bool cautBin(int , int &);
		void Insert( Pers*);
		void Remove( Pers *);	
	public:
		Pers * operator[](int);
		void afis();
		Index(){ n=0; }
  };

Index Pers::bdPers;

bool Index::cautBin(int marca, int &poz)
  {
	  int inc=0, sf= n-1, m;
	  while( inc <= sf)
		{
		 m =(inc+sf)/2;
		 if(lista[m]->marca == marca)
			 {  poz = m; return 1; }
		 if(lista[m]->marca < marca ) inc= m+1;else sf = m-1;
		}
	  poz = inc;  return 0;
  }

void Index::Insert( Pers* pp)
  {
	int i, poz;
	if( ! cautBin(pp->marca, poz)  )
	  {
		for(i=n; i>poz; i--)
				  lista[i] = lista[i-1];
		lista[poz]=pp; n++;
	  }
   }


Pers * Index::operator[ ](int marca)
     {
	 int poz;
	 if(cautBin( marca, poz) ) return lista[poz];
	  else return NULL;
     }


void Index::Remove( Pers *pp)
   {
	int poz;
	if( cautBin(pp->marca, poz) )
	{
		for(; poz< n-1; poz++)
			lista[poz] = lista[poz+1];
		n--; 
	}
   }

void Index::afis()
  {
	cout << "\n\n\n";
	for(int i=0; i<n; i++)
	  cout << "\n " << lista[i]->marca << "  " << lista[i]->nume;
  }

Pers::Pers(char n[20]="Anonim ", int m=0) : marca(m)
			{  strcpy(nume,n); bdPers.Insert(this);}

Pers:: ~Pers(){ bdPers.Remove(this); }

void main()
  {
	 Pers p1("Popa Ion  ",7), *pp;
	 Pers p2("Cinci", 5), p3("Trei",3);
	  {
		Pers	p4("patru", 4);
		Pers::bdPers.afis();
		if( pp=Pers::bdPers[7] ) cout << "\nPers cu marca 7 este " << pp->nume;
		if( pp=Pers::bdPers[33] ) cout << "\nPers cu marca 33 este " << pp->nume;
	  }
	 Pers::bdPers.afis(); getchar();
  }

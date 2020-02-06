#include "stdafx.h"

#include <string.h>
#include <iostream>
using namespace std;

class Index;

class Pers
 {			int marca;			
			friend class Index;
	public:
			char nume[50];
			char cnp[14];
			Pers( char *,char *, int);
			~Pers();
			static Index bdPers;
  };

class Index
  {
		friend class Pers;
		Pers *lista[100]; 	int n;
		bool cautBin(char * , int &);
		void Insert( Pers*);
		void Remove( Pers *);	
	public:
		Pers * operator[](char *);
		void afis();
		Index(){ n=0; }
  };

Index Pers::bdPers;

bool Index::cautBin(char *s, int &poz)
  {
	  int inc=0, sf= n-1, m;
     
	  while( inc <= sf)
	  {
			m =(inc+sf)/2;
			if(strcmp(lista[m]->cnp,s)==0) 
			{  
				 poz = m; 
				 return 1; 
			}
			if(strcmp(lista[m]->cnp,s)<0 ) 
				inc= m+1;
			else sf = m-1;
	  }
	  poz = inc;  
	  return 0;
  }

void Index::Insert( Pers* pp)
  {
	int i, poz;
	if ( ! cautBin(pp->cnp, poz)  )
	  {
		for(i=n; i>poz; i--)
				  lista[i] = lista[i-1];
		lista[poz]=pp; n++;
	  }
   }


Pers * Index::operator[ ](char *c)
{
	 int poz;
	 if(cautBin( c, poz) ) 
		 return lista[poz];
	 else return NULL;
}


void Index::Remove( Pers *pp)
{
	int poz;
	if( cautBin(pp->cnp, poz) )
	{
		for(; poz< n-1; poz++)
			lista[poz] = lista[poz+1];
		n--; 
	}
}

void Index::afis()
  {
	cout << "\n\n\n CNP \t\t Nume\n";
	for(int i=0; i<n; i++)
		cout << "\n " << lista[i]->cnp << "  " << lista[i]->nume;
  }

Pers::Pers(char n[20]="Noname",char s[10]="0000000000000", int m=0) : marca(m)
{  
	strcpy(nume,n); strcpy(cnp,s); 
	bdPers.Insert(this);
}

Pers:: ~Pers(){ bdPers.Remove(this); }

void main()
  {
	 Pers p1("AAA","1872327150432",7), *pp;
	 Pers p2("BBB","2870328295894",5), p3("CCC","1813492217485",3);
	  {
		Pers	p4("DDD","2884323342956",4);
		Pers::bdPers.afis();
	
		cout<<endl;
		if( pp=Pers::bdPers["2870328295894"] ) cout <<endl<< "\nPers cu cnp 2870328295894 este " << pp->nume; 
		else cout<<endl<<"Nu exista acest CNP";
		if( pp=Pers::bdPers["1872327150432"] ) cout << "\nPers cu cnp 1872327150432 este " << pp->nume<<endl;
		else cout<<"Nu exista acest CNP"<<endl;
	  }
		char s[14],s1[14]; 	strcpy(s1,"");
		cin.clear(); 
		cout<<endl<<"Introduceti CNP:"<<endl;fgets(s,14,stdin); 
		strncat(s1,s,14); //concatenare 13+1 caractere din sirul s citit de la tastatura
		//strncat(s1,"2870328295894",14);
		if( pp=Pers::bdPers[s1] ) 
			{cout <<endl<< "\nPers cu cnp: "<<s1<<" este " << pp->nume; }
		else cout<<"Nu exista acest CNP"<<endl;	
	  	Pers::bdPers.afis(); 
	 fflush(stdin);getchar();
  }


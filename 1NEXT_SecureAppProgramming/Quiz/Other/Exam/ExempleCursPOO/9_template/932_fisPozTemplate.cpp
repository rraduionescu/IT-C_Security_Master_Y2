#include "stdafx.h"

// Clasele fisier-pozitie facute template
#include "stdafx.h"

#include<stdio.h>
#include<string.h>
#include<iostream>
using namespace std;

template <class T> class fisier; // anunt formal clasa fisier

template <class T>
class poz_fis
{
	public:
		friend fisier<T>;
		void operator=(T );				 // scriere cu op= la pozitia i*sizeof(T)
		operator T();					 // cast din pozitie spre tip generic T
		poz_fis(fisier<T> &f):rfis(f) { }// fixare fisier asociat
	private:
		fisier<T> &rfis;				 // referinta fisier asociat 
};

template <class T>
class fisier
{
	public:
		friend poz_fis<T>;
		fisier(char *nume)  {  pf=fopen(nume,"w+b");  }
		~fisier() {   fclose (pf); }
		poz_fis<T> operator[] (long poz)
			{
			   fseek(pf,poz*sizeof(T),SEEK_SET);
			   return poz_fis<T>(*this);
			 }
		int sf(){return eof;}
		FILE *pf;
		int eof;
};

template <class T>
void poz_fis<T>::operator=(T a)// scriere cu op= la pozitia i*sizeof(T)
	{
	  if(rfis.pf!=NULL) fwrite(&a,sizeof(a),1,rfis.pf);
	}

template <class T>
poz_fis<T>::operator T()		// cast din pozitie spre tip generic T
	{							// face de fapt citire
	  T a;
	  if(rfis.pf!=NULL) rfis.eof=!fread(&a,sizeof(a),1,rfis.pf);
	  return a;
	}

struct art 
{	
	int cod; char den[20];
	friend ostream & operator<<(ostream & out, art s)
		{ out<<s.cod<<" "<<s.den; return out;}
};

class persoana
 {
   float sal;
   public:
     char nume[20];
     persoana(char n[20]="Anonim",float s=0.0)
				{  strcpy(nume,n);sal=s;     };
	 friend ostream & operator<<(ostream & out, persoana p)
		{ out << p.nume << "\t " << p.sal; return out;}
 };

void main()
  {
     int i;	 	  char c;	   double d;

   fisier<char> fc("testc.dat");
   fc[0]='a';   fc[1]='b';   fc[2]='c';
   for(i=0;c=fc[i],!fc.sf();i++)cout<<c<<endl;
   
   fisier<double> fd("testd.dat");
   fd[0]=5.7;   fd[1]=8.901;
   for(i=0;d=fd[i],!fd.sf();i++)cout<<d<<endl;

   fisier<struct art> fs("testa.dat");
   struct art aux,va[]={{100,"tabla"},{102,"tigla"},{103,"ciment"}};
   fs[0]=va[0];   fs[1]=va[1];   fs[2]=va[2];
   for(i=0;aux=fs[i],!fs.sf();i++) cout<<aux<<endl;

   fisier<persoana> fp("pers.dat");
   persoana p1("Popa",1000),p2("Doi",2000), tmp;
   fp[1]=p1;fp[2]=p2;
   for(i=0;tmp=fp[i],!fp.sf();i++)cout <<tmp<< endl;
cin.get();
  }

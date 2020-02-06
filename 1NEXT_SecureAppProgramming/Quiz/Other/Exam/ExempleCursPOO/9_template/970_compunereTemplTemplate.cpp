#include "stdafx.h"
#include <iostream> 
using namespace std;

template<class T>
class lista
{
	class nod
	{
		friend lista;
		
		nod *next;
	public:T info;
		nod(T k, nod *urm=NULL):info(k),next(urm) { }
		nod *get_next() { return next; }
		friend ostream& operator<<(ostream& os, nod n) 
						{  os<< n.info; return os;  }
	};
	
public: nod *cap;
	int n;
	void sterge_tot(nod *);
public:
	lista():cap(NULL),n(0) { }
	void ins_incep(T );
	int count() { return n; }
	friend ostream& operator<<(ostream & os , lista<T> & l  )
	{
		lista<T>::nod *cp=l.cap;
		os<<"\n\t (";
		while(cp) { os<< *cp ; if(cp->get_next())os<<" , "; cp=cp->get_next(); }
		os<<")";
		return os;
	}
	~lista() { sterge_tot(cap); }
};


template<class T>
void lista<T>::sterge_tot(nod *cp)
{
	if(cp)	{ 	sterge_tot(cp->next);	delete cp;  	}
	cap=NULL; n=0;
}

template<class T>
void lista<T>::ins_incep(T k)   {  nod *t=new nod(k,cap); cap=t; n++; }


template<class T>
class persoana
{
public:	char nume[50];
	T sal;

	persoana(char *np, T s):sal(s) {  strcpy(nume, np);	}
	friend ostream& operator<<(ostream &os , persoana<T>  & p)
		{
		os<<p.nume<<"  "<< p.sal;
		return os;
	};
	
};


template<class T>
class echipa
{
	lista< persoana<T> > ls;
public:
	void add(persoana<T> prs)  {    ls.ins_incep(prs);   }
	friend ostream& operator<<(ostream& os, echipa& l)
	{
		os<<l.ls;
		return os;
	}
};



void main()
{
	lista<persoana<int> > lp; 
	persoana<int> p1("doi", 3000); 	lp.ins_incep(p1);   
	lp.ins_incep(persoana<int>("unu",1000)); 
	 cout<<"Lista de persoane:"<<lp;
	echipa<int> interventie;
	interventie.add(persoana<int>("Valentina",96785));
	interventie.add(persoana<int>("Daniel",90333));
	interventie.add(persoana<int>("Liviu",4585));
	cout<<"\n Echipa de serviciu: "<< interventie;
  getchar();
}








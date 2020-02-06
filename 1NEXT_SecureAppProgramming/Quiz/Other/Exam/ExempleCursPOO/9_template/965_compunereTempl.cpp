#include "stdafx.h"

#include <iostream> 
using namespace std;

template<class T>
class lista
{
	class nod
	{
		friend lista;
		T info;
		nod *next;
	public:		nod(T k, nod *urm=NULL):info(k),next(urm) { }
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
	friend ostream& operator<<(ostream& os, lista& l)
{
	nod *cp=l.cap;
	os<<"(";
	while(cp) { os<< *cp; if(cp->get_next())os<<" , "; cp=cp->get_next(); }
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


void main()
{
  lista<int> li;
  li.ins_incep(10); li.ins_incep(56); 
  cout<<"Lista de intregi:"<<li<<" are "<<li.count()<<" elemente"<<endl;
  lista<double> lr;
  lr.ins_incep(140.44); lr.ins_incep(54.67); lr.ins_incep(901.7);
  cout<<"Lista de nr reale:"<<lr<<endl;
  getchar();
}

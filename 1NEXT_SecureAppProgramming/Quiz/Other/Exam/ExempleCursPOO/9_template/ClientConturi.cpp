#include "stdafx.h"

#include <iostream> 
using namespace std;

// clasa independenta si se poate declara prima
class Cont 
{
	char tip; 	float sold; char valuta[4];
public:
	int contID;
	Cont(int id,char t, float s=0, char *v="RON"): contID(id),tip(t),sold(s)
		{strcpy(valuta,v); }
	friend ostream& operator<<(ostream& os, Cont& c)
	{
		os<< "\n\t" <<c.contID<<" sold: " << c.sold << " " <<c.valuta;
		return os;
	}
};

class lista
{
	class nod
		{
			friend lista;
			Cont & info;
			friend ostream& operator<<(ostream& os, nod &n)
			{
				os<< endl << (n.info) << " ... " ;		
				return os;
			}
		public:
			nod *next;
			nod(Cont& x) :info ( x) {  next=NULL; }
		}*cap;
	
	public:
		lista() {cap=NULL;}
		void lista:: Add(Cont & c)
		{
			nod *tmp = new nod(c); tmp->next = cap; cap=tmp;
			cout<< "\n\nAdauga la :" << & cap->info <<" care trimite la adresa:  " << cap->next;
		}
		
		friend ostream& operator<<(ostream& os, lista& l)
		{
			nod * aux = l.cap;
			while(aux)
			{
				os << (*aux);
				aux = aux->next;
			}
			return os;
		}
};

class Client
{
	int codClient;	
public:
	char nume[30];
	lista lstConturi;
	Client(int c,char *n):codClient(c) {strcpy(nume,n); }
	friend ostream& operator<<(ostream& os, Client& c)
	{
		os<<endl<<c.codClient<<"  " << c.nume;
		// os << c.lstConturi;
		return os;
	}
};


void main()
 {	
	Client c1(1,"ClientUNU"), c2(2,"ClientDOI");	cout << c1;
	Cont ct1(1,'D',1000);	c2.lstConturi.Add(ct1);
	Cont ct2(2,'C',100,"EUR");	c2.lstConturi.Add(ct2);
	cout << "\n\nLista conturilor lui " << c2.nume << c2.lstConturi;
	getchar();
 }


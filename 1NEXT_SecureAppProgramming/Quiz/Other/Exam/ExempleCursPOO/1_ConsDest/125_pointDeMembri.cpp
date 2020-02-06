#include <iostream>
using namespace std;

class Pers
{
private:
	int varsta, vechime;
public:
	char nume[100];
	
	int getVarsta()		{ return varsta; }
	int getVechime()	{ return vechime; }

	friend ostream & operator<<(ostream & ies, const Pers &p)
	{
		ies << "\n" << p.nume <<"   " << p.varsta;
		return ies;
	}
	Pers(char *n="Noname", int v=20, int vch=0): varsta(v),vechime(vch)
	{
		strcpy(nume,n);
	}
};

//double calcPrima1( Pers &p, int Pers::*pi)
//{
//	return p.*pi*10;
//}

double calcPrima2( Pers &p, int (Pers::*pmf)())
{
	return (p.*pmf)()*10;
}

void main()
{
	Pers p1("Adam",30,10), p2("Popa",20);
	int Pers::*pmi;
	//pmi=&Pers::varsta;
	int (Pers::*pmf)(); 
	pmf=&Pers::getVechime;
	 
	//pi=&Pers::vechime; 
	//cout <<"\n "<<p1.nume <<" are " << calcPrima1( p1, pmi)<< " lei";
	cout <<"\n "<<p1.nume <<" are " << calcPrima2( p1, pmf)<< " lei";
	getchar();

}

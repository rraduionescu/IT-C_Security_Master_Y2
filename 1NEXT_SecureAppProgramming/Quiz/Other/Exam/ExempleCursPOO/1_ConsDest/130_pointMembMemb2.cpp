#include <iostream>
using namespace std;

class Pers
{
private:
	int  vechime;
public:
	int  varsta;
	int Pers::*pmim;
	int (Pers::*pmfm)();
	int getVechime()	{ return vechime; }

	double calcPrima2()
	{
		return (this->*pmfm)()*10;
	}

	void setCheieData(int Pers::*pmi)
	{
		pmim=pmi;
	}
	void setCheieFunctie(int (Pers::*pmf)() )
	{
		pmfm=pmf;
	}
	double calcPrima3( )
	{
		return this->*pmim*10;
	}
	char nume[100];	

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

double calcPrima1( Pers &p )
	{
		return (p.*p.pmfm)()*10.;
		// daca pmfm e static ?
	}

void main()

{
	Pers p1("Adam",30,10), p2("Popa",20);

	p1.setCheieData(&Pers::varsta);
	p1.setCheieFunctie(&Pers::getVechime);
	cout <<"\nDupa varsta "<<p1.nume <<" are o prima de " << p1.calcPrima3( )<< " lei";
	cout <<"\nDupa vechime "<<p1.nume <<" are o prima de " << p1.calcPrima2( )<< " lei";
	cout <<"\nObiect self contained "<<p1.nume <<" are o prima de " << calcPrima1( p1)<< " lei";
	getchar();

}

#include "stdafx.h"

#include <iostream>
using namespace std;

class Vector
{
private:
	double *pe; int dim;
	static double errPoint;
public:
	Vector(int n=1):dim(n)
	{
		pe= new double[n];
		for(int i=0;i< dim;i++)	pe[i]=0.0;

	}
	double & operator[](int k)
	{
		if(k>=0 && k<dim) return pe[k];
		else
		{
			cerr <<endl << "Eroare de incadrare in dim!";
			return errPoint;
		}
	}
	friend ostream & operator<<(ostream &ies, Vector &v)
	{
		ies << endl;
		for(int i=0;i<v.dim;i++) ies <<v.pe[i]<<"   ";
		return ies;
	}

};

double Vector::errPoint=0.0;

void main()
{
	
	int n; cout << endl<<"dati dim; "; cin>>n;
	Vector v(n);	
	v[1]=10;
	cout << v;  fflush(stdin); getchar();
}

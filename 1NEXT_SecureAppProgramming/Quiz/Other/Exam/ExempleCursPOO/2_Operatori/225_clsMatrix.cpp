#include "stdafx.h"

#include<iostream>
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
		for(int i=0;i<v.dim;i++) ies <<v.pe[i]<<"   ";
		return ies;
	}
	~Vector()
	{
		delete[]pe;
	}

};

double Vector::errPoint=0.0;


class Matrix
{

      Vector ** pv;     
      int nlin, ncol;
      static Vector errVect;
public:    
      Matrix(int M=1, int N=1) : nlin(M), ncol(N)
    { 
      cout<<"\nConstructor clasa Matrix";
      pv =  new Vector *[ M]; 
      if (!pv) { cout << "Esec alocare pointeri de vectori in matrix"; exit(1); }
      for (int i=0; i<M; i++) 
      { 
          pv[i] = new Vector( N); 
          if (!pv[i]) { cout << "Esec alocare vector " << i; exit(2); }
      } 
	}

  Vector & operator[](int i)
      {
            if(i>=0 && i<nlin ) return *pv[i];  else return Matrix::errVect;
      } 

  ~Matrix()
  {
        cout<<"Destructor clasa Matrix"<<endl;
		for(int i=0;i<nlin;i++) pv[i]->~Vector(); 
			delete[] pv;
  }
  friend ostream & operator<<(ostream &ies, Matrix &m)
	{
		ies << endl;
		for(int i=0;i<m.nlin;i++)
			{	cout<<endl << *m.pv[i];	}
		return ies;
	}
};

Vector Matrix::errVect;


void main()
{
	int nl=10,nc=10;
	Matrix m1(	nl,nc);
	for(int i=0;i<nl;i++)
	   for(int j=0;j<nc;j++) m1[i][j]=i*10+j;
	cout <<m1;
   cout << endl<< endl << m1[nl-1][nc-1];
   getchar();
 }


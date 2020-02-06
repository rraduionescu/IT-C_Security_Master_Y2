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

class Cube  
{
	Matrix **a;
	int nrMatr,nrLinii,nrColoane;
	static Matrix errMat;
public:
    Cube(int L, int M, int N): nrMatr(L), nrLinii(M), nrColoane(N)
      {
        cout<<"\nConstructor clasa Cub"<<endl;
        a= new Matrix*[L];
        for(int i=0; i<nrMatr; i++) 
			a[i] = new Matrix(nrLinii,nrColoane);
      } 
	  Matrix& operator[](int i)
      {
            if(i>=0 && i<nrMatr ) return *(a[i]);
			else return errMat;

      } 
	  friend ostream & operator<<(ostream &ies, Cube &c)
	{
		ies << endl;
		cout<<endl;
		for(int i=0;i<c.nrMatr;i++)
		  { cout<<endl << (*(c.a[i])) ;  }
		return ies;
	}
	      
      ~Cube()
      {
            for(int i=0;i<nrMatr;i++) a[i]->~Matrix();
            delete[] a;
      }
};
Matrix  Cube::errMat;

void main()
{
    int nm=10, nl=10, nc=10;
	Cube c1(nm,nl,nc); 
	for(int i=0;i<nm;i++)
	   for(int j=0;j<nl;j++)
		  for(int k=0;k<nc;k++)
		  { 
			c1[i][j][k]=i*100+j*10+k; //  cin>>c1[i][j][k];
		  }
     cout << c1;
	 cout <<endl<<c1[6][6][6];
	getchar();
}
 


#include <iostream>
using namespace std;
template <class T>
class vector
{
   T * pe;   int dim;
public:
   vector(int);
   ~vector( ) { delete[ ] pe; }
   int spune_dim() {return dim; }
};

template <class T>
vector<T>::vector(int n) : dim(n)
{
   pe = new T[n];   
   for (int i = 0; i < dim; i++)
     { cout <<"\n elem_"  << i << " "; cin >>  pe[i];   }
}

template <class TT1, class TT2>
int masoara( vector<TT1>& v1, vector<TT2>& v2)
{
	int dim1=v1.spune_dim(), dim2=v2.spune_dim();
	if(dim1==dim2) return 0;
	else
		if(dim1<dim2) return -1;
		else return +1;
}
void main()
 {
   	vector<int> vi(2);
   	vector<double> vd(2);
   	cout << masoara(vi,vd); getchar();getchar();
 }

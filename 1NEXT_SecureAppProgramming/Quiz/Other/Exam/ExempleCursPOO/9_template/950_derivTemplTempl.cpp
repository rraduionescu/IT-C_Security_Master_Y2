#include <iostream> 
using namespace std;
template <class T> class vector
{
   	T * pe;
   	int dim;
public:
   	vector(int);
   	vector & operator+=(vector &);
   	T& operator[ ] (int i) { return pe[i]; }
   	void afis();  
void sort();
~vector( ) { delete[ ] pe; }
};

template <class T> vector<T>::vector(int n):dim(n)
{
 	 pe = new T[n];   
  	 for (int i = 0; i < dim; i++)
    		 { cout <<"\n elem_"  << i << " "; cin >>  pe[i];   }
}

template <class T> 
vector<T> &  vector<T>::operator+=(  vector<T>& v2)
{
	T *nou = new T[dim + v2.dim];
	for (int i = 0; i < dim; i++) nou[i]=pe[i];
	for (  i = dim; i < dim+v2.dim; i++) nou[i]=v2.pe[i-dim];
	delete [] pe; pe=nou; dim+=v2.dim;
	return *this;
}
 
template <class T> void vector<T>::afis()
{
      	cout << endl;
      	for (int i = 0; i < dim; i++)	 cout << pe[i] << "   ";
}

template <class T> void vector<T>::sort()
{
      	int i,j; T aux;
      	for (i = 0; i < dim-1; i++)
     		 for ( j = i+1; j < dim; j++)
          			 if(pe[i] > pe[j])
             			{ aux= pe[i]; pe[i]=pe[j]; pe[j]=aux; }
}

template <class T>
class vect_sort:public vector<T>
{
	public:
		vect_sort(int );
		vect_sort &  operator+=(vect_sort<T> & v2);
};

template <class T>
vect_sort<T>::vect_sort(int n) : vector<T>(n)  {    sort();     }

template <class T>
vect_sort<T>&  vect_sort<T>::operator+=(vect_sort<T>& v2)
{   
(*this).vector<T>:: operator+=(v2);  sort();   return *this; 
}

void main()
 {
     	//vector<int> vi(3), vi2(2);
		//vi+=vi2; vi.afis(); vi.sort();  vi.afis();
		// vector<float> vf(3);
		// vf.afis();  vf.sort();  vf.afis();
   	vect_sort<int> vis1(3), vis2(2);
 	vis1+=vis2;	vis1.afis();
	getchar(); getchar();
 }




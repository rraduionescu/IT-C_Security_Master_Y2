#include "stdafx.h"
#include <iostream> 
using namespace std;

template <class T>
class comparator 
{
public: 
	comparator() {;}
	~comparator() {}
	virtual bool operator()(T x, T y)=0;
};

template class comparator<int>;

template <class T>
class maiMic:  public comparator<T>
 {
public: 
	maiMic (){} ;
	~maiMic () {}
	bool operator()(T x, T y)
		{ return x<y ? true:false; }
 };

//template class maiMic<int>;
//template class maiMic<int>::maiMic();

template <class T>
class maiMare : public comparator<T>
 {
public: bool operator()(T x, T y)
		{ return x>y ? true:false; }
 };

template <class T>
 class vector
{
   	T * pe;
   	int dim;
public:
   	vector(int);
   	T& operator[ ] (int i) { return pe[i]; }
   	void afis();  void sort( comparator<T> &);
~vector( ) { delete[ ] pe; }
};

template <class T> vector<T>::vector(int n):dim(n)
{
 	 pe = new T[n];   
  	 for (int i = 0; i < dim; i++)
    		 { cout <<"\n elem_"  << i << " "; cin >>  pe[i];   }
}


 
template <class T> void vector<T>::afis()
{
      	cout << endl;
      	for (int i = 0; i < dim; i++)	 cout << pe[i] << "   ";
}

template <class T> void vector<T>::sort( comparator<T> & sens)
{
      	int i,j; T aux;
      	for (i = 0; i < dim-1; i++)
     		 for ( j = i+1; j < dim; j++)
          			 if(!sens (pe[i] , pe[j]) )
             			{ aux= pe[i]; pe[i]=pe[j]; pe[j]=aux; }
}

void main()
 {	
	maiMic<int> mm; maiMare<int> MM; maiMare<double> MMD;
    	vector<int> vi(3); 	vi.afis(); 
		vi.sort( mm );		vi.afis(); 
		vi.sort( MM );		vi.afis(); 
vector<double> vd(3); vd.sort( MMD );	vd.afis(); 

	getchar(); getchar();
 }


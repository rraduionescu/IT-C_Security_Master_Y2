#include <iostream>
using namespace std;

template <class T> class vector
{
   	T * pe;
   	int dim;
public:
   	vector(int);
   	~vector( ) { delete[ ] pe; }
   	T& operator[ ] (int i) { return pe[i]; }
  	 void afis();  void sort();
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

template <class T> void vector<T>::sort()
{
     	int i,j;    T aux;
      	for (i = 0; i < dim-1; i++)
     		 for ( j = i+1; j < dim; j++)
          			 if(pe[i] > pe[j])
			             { aux= pe[i]; pe[i]=pe[j]; pe[j]=aux; }
 }
 
#define L_SIR 100
vector<char*>::vector(int n):dim(n)
	{
	  pe = new char*[dim];  
	  cin.ignore(L_SIR,'\n');
	  for (int i = 0; i < dim; i++)  
		{ 
		  cout <<"\n Sir_"  << i << " "; 
		  pe[i]=new char[L_SIR];
		  cin.getline(pe[i],L_SIR);
		}
	}

void vector<char*>::sort()
    {
      int i,j; char *aux;
      for (i=0; i < dim-1; i++)
     	 for ( j = i+1; j < dim; j++)
          		 if(strcmp(pe[i],pe[j])>0)
		             { aux= pe[i]; pe[i]=pe[j]; pe[j]=aux; }
    }
vector<char*>::~vector()
	{
	  for (int i = 0; i < dim; i++)  delete [L_SIR] pe[i];
	  delete [dim]pe;  
	}

void main()
 {
   vector<int>  vi(3);   vi.afis(); vi.sort();  vi.afis();
   vector<char*> vs(3);   vs.afis(); vs.sort();  vs.afis();
   getchar(); getchar();
 }


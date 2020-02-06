#include <iostream> 
using namespace std;
#include <string.h>
#define L_SIR 100
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

template <> class vector<char*>
{
   char **pe;
   int dim;
public:
   vector(int);
   char*& operator[ ] (int i) { return pe[i]; }
   void afis();  void sort();  
   int lungime_element(int);
   ~vector( );
};

vector<char*>::vector(int n):dim(n)
{
	  pe = new char*[dim];  
	  cin.ignore(L_SIR,'\n');
	  for (int i = 0; i < dim; i++)  
		{ 
		  cout <<"\n elem_"  << i << " "; 
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

void vector<char*>::afis()
{
      cout << endl;
      for (int i = 0; i < dim; i++)	 cout << pe[i] << "   ";
}

vector<char*>::~vector()
{
  	for (int i = 0; i < dim; i++)  delete [L_SIR] pe[i];
  	delete [dim]pe;  
}

int vector<char*>::lungime_element(int i)
{
	  return strlen(pe[i]);
}
void main()
 {
   vector<int>  vi(3);   vi.afis(); vi.sort();  vi.afis();
   vector<char*> vs(3);  vs.afis(); vs.sort();  vs.afis();
   cout<<"\n Primul sir:"<<vs[0]<<" are lungimea:"<<vs.lungime_element(0);
   getchar();
 }



#include <iostream>
using namespace std;
class persoana
  {
  public:   char nume[40];
     persoana( char *n="Noname") { strcpy(nume,n); }
     friend ostream & operator<< ( ostream & iesire, persoana p )
        { iesire << p.nume << " ";	return iesire;  }
     friend istream & operator>> ( istream & intrare, persoana & p )
      { cout << "Nume: "; intrare>> p.nume;   return intrare;    }
     int operator> (persoana p) { return strcmp(nume,p.nume)>0 ? 1 :0; }
  };

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

void main()
 {
    vector<int>  vi(3);     vi.afis(); vi.sort();  vi.afis();
    vector<persoana> vp(2); vp.afis(); vp.sort();  vp.afis();   
	cin>> vi[0];
}
#include <iostream> 
using namespace std;

template<class T1>
class b1
{
protected:	T1 a;
public:		b1(T1 x):a(x) { }
	void afis() {    cout<<"Membru din b1:"<<a<<endl;	}
};
template<class T2>
class b2
{
protected:	T2 c;
public:		b2(T2 x):c(x) { }
	void afis() {     cout<<"Membru din b2:"<<c<<endl;	}
};


template<class T1, class T2>
class d : public b1<T1>, public b2<T2>
{
public:
	d(T1 y, T2 z):b1<T1>(y),b2<T2>(z) { }
	void afis() {	 cout<<"Valorile :"<<a<<" si "<<c<<endl;	}
};

// Pentru a testa cand cele doua tipuri nu difera !!
//template<class T>
//class d : public b1<T>, public b2<T>
//{
//public:
//	d(T y, T z):b1<T>(y),b2<T>(z) { }
//	void afis() { cout<<"Valorile :"<<a<<" si "<<c<<endl;	}
//}; 

void main()
{	d<float,int> a(1.1,2);	a.afis(); getchar(); } 








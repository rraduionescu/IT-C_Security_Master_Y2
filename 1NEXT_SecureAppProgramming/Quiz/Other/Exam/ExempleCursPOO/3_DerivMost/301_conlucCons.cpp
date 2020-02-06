#include <iostream>
using namespace std;

class B
{ 
private: int a;
public: B( int i=1) {a=i;} 
	friend ostream& operator<<(ostream& ies, B x) 
		{ ies << x.a; return ies ;}
};

class D : public B
{		int b;
	public: D(int i=0 ) {b=i;}
};

void main()
{	D od; cout << od; getchar(); }

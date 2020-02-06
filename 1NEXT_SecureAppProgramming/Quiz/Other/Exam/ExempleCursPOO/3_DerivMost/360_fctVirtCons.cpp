#include <iostream>
using namespace std;

class B
{ 
private: int a;
public: B( int i=1) {a=i; f();} 
		virtual void f() {cout << "\n f din B";}
	friend ostream& operator<<(ostream& ies, B x) 
		{ ies << x.a; return ies ;}

		
};

class D : public B
{		int b;
	public: D(int i=3 ) {b=i;f();}
		void f() {cout << "\n f din D";} // apel de functie in cons
};

void main()
{	
	D od;
	getchar(); 
}
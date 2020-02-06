#include <iostream>
using namespace std;

class B
{ 
private: int a;
public: B( int i=1) {a=i;} 
		 ~B() { cout << "\n destr B"; }
friend ostream& operator<<(ostream& ies, B x) 
		{ ies << x.a; return ies ;}

		
};
class D : public B
{		int b;
	public: D(int i=3 ) {b=i;}
		~D() { cout << "\n destr D"; }
};
void main()
{	
	B *pb= new D(1); 	delete pb;
	getchar(); 
}
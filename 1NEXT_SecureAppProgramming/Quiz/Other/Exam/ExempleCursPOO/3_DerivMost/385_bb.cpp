#include<iostream>
using namespace std;


class BB	{	public:		int x;	};
class B1 :  public BB 	{ public:    B1( ) { x = 1;}	};
class B2 :  public BB 	{ public:    B2( ) { x = 2;}	};
class D :    public B1, public B2   {	};

void main()
{
	D d; 
	cout << "\n x mostenit via B1: " << d.B1::x; 
	cout << "\n x mostenit via B2: " << d.B2::x; 
	
	getchar();
}

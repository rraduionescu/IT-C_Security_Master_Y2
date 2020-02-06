#include <iostream>
#include <iomanip>
using namespace std;

ostream &  money_format( ostream &s)
{
		s<< setiosflags( ios::fixed);
 		s<< setprecision(5) << setfill('$') << setw(15);
		s<< setiosflags( ios::showpoint);
		return s;
}

void main()
{
	double a=12345.6789;
	cout << money_format <<a<< setprecision(2); 
	cout << "\n" <<a; 
	getchar();
}

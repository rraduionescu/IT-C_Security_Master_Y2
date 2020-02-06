
#include <iostream>
using namespace std;
class b  
{
public:
	b() { }
	virtual ~b() { }
};
template <typename T>
class d : public b 
{
public:
	T x;
	d() { }
	virtual ~d(){ }
};
void main()
{	b a;	d<float> c;}


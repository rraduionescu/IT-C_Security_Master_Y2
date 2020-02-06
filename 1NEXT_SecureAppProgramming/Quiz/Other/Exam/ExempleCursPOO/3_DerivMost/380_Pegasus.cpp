#include <iostream>
using namespace std;

class Animal
{
	
public: void Speak(){ cout << "\t Speaks"; }
		int varsta;
};

class Bird: public Animal
{
public:
	void Speak()
	{cout << "\t Ciripeste"; }

};

class Horse: public Animal
{
public:
	void Speak()
	{cout << "\t Necheaza"; }

};


class Pegasus: public Horse, public Bird
{
public:
	Pegasus(){this->Horse::varsta=1;}
};

void main()
{
	Pegasus pg;
	cout << pg.Horse::varsta;
	getchar();

}

#include <string>
#include <iostream>
using namespace std;

class Student
{
private:
	int age;
	char* name;
public:
	Student();
	Student(int v, char* n);
	char* getName() {
		return this->name;
	}
	void setName() {
		if (this->name != NULL) 
			this->name[0] = 'Y';
	}
};

Student::Student() {
	this->age = 0;
	this->name = NULL;
}

Student::Student(int v, char* n) {
	this->age = v;
	if (n != NULL) {
		this->name = new char[strlen(n)+1];
		strcpy(this->name, n);
	} else this->name = NULL;
}

void main() 
{
	Student x;
	int age1 = 21; int age2 = 20;
	char* name1 = "John"; char* name2 = "Smith";

	Student s1(age1, name1);
	Student s2(age2, name2);
	Student s3 = s1;

	cout<<"Name s1: "<<s1.getName()<<", Name s2: "<<s2.getName()<<", Name s3: "<<s3.getName()<<endl;
	s3.setName();	
	cout<<"Name s1: "<<s1.getName()<<", Name s2: "<<s2.getName()<<", Name s3: "<<s3.getName()<<endl;
}

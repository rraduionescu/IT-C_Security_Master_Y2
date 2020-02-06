#include <string>
#include <iostream>
using namespace std;

class Student
{
private:
	int age;
	char* name;
public:
	Student(); // default constructor
	Student(int v, char* n); // constructor with parameters
	Student(Student, int); // constructor with parameters
	Student(Student&); // copy constructor

	void operator=(Student&); // overload the assignment operator (=)

	~Student(); // destructor: mandatory to be in this class (name is allocated in heap mem)

	char* getName() {
		return this->name;
	}

	void changeName() {
		if (this->name != NULL) 
			this->name[0] = 'Y';
	}

	void setNullName() { // called to avoid runtime errors when object this shares heap mem with other objects for the field name
		this->name = 0; // this->name = NULL;
	}
};

Student::Student() {
	cout<<" Default constructor."<<endl;
	this->age = 0;
	this->name = NULL;
}

Student::Student(int v, char* n) {
	cout<<" Constructor with parameters."<<endl;
	this->age = v;
	if (n != NULL) {
		this->name = new char[strlen(n)+1];
		strcpy(this->name, n);
	} else this->name = NULL;
}

Student::Student(Student& obr) {
	cout<<" Copy constructor."<<endl;
	this->age = obr.age;
	if (obr.name != NULL) {
		this->name = new char[strlen(obr.name) + 1];
		strcpy(this->name, obr.name);
	} else this->name = NULL;
}

Student::Student(Student obr, int i) {
	cout << " Overloaded constructor with parameters." << endl;
	this->age = i;
	this->name = obr.name;
}

Student::~Student() {
	cout<<" Destructor."<<endl;
	if (this->name != NULL)
		delete[] this->name;
}

void Student::operator =(Student& obr) {
	cout<<"Operator ="<<endl;
	this->age = obr.age;
	if (obr.name != NULL) {
		if(this->name != NULL) delete[] this->name;
		this->name = new char[strlen(obr.name) + 1];
		strcpy(this->name, obr.name);
	} else {
		if(this->name != NULL) delete[] this->name;
		this->name = NULL;
	}
}

// Test scenarios:
// 1. default constructor, constructor with params (memory leaks)
// 2. default constructor, constructor with params, destructor (runtime error due to heap addresses sharing between s1 & s3 and s2 & s4. 
		// solution: additonal setter to put the NULL value on the fields name for s1 and s3 before to end the instruction block where s1, s2, s3 and s4 are defined 
// 3. default constructor, constructor with params, copy constructor, destructor
// 4. default constructor, constructor with params, copy constructor, destructor, operator =
// 5. different scope for x and s1, s2, s3, s4

void main() 
{
	Student x; // default constructor
	Student y(x, 24); // overloaded constructor with parameters

	while(1) // scenario no. 2, scenario no. 3, scenario no. 4
	{ // start nested instruction block
		int age1 = 21; int age2 = 20;
		char* name1, * name2;

		name1 = new char[strlen("John") + 1];
		strcpy(name1, "John");
		name2 = new char[strlen("Smith") + 1];
		strcpy(name2, "Smith");

		Student s1(age1, name1); // constructor with parameters
		Student s2(age2, name2); // constructor with parameters
		Student s3 = s1; // Student s3(s1); // copy constructor
		Student s4; // default constructor

			s4 = s2; // overloaded version of operator = is called

			cout<<"Name s1: "<<s1.getName()<<", Name s2: "<<s2.getName()<<", Name s3: "<<s3.getName()<<
				", Name s4: "<<s4.getName()<<endl;
			s3.changeName();	
			s4.changeName();
			cout<<"Name s1: "<<s1.getName()<<", Name s2: "<<s2.getName()<<", Name s3: "<<s3.getName()<<
				", Name s4: "<<s4.getName()<<endl;

			// for the scenario 2
			/* s3.setNullName();
			s4.setNullName();
			delete[] name1;
			delete[] name2; */

			// for scenario 3
			/* s4.setNullName();
			delete[] name1;
			delete[] name2; */

			// for scenario 4
			delete[] name1;
			delete[] name2;
	} // end of nested block - destructor called for s1, s2, s3, s4; age1, ag2, name1 and name2 are not availbale anymore for the rest od the app
	cout<<"End of application!"<<endl;
}

//#include <string>
//#include <iostream>
//using namespace std;
//
//class Student
//{
//private:
//	int age;
//	char* name;
//public:
//	Student(); // default constructor
//	Student(int v, char* n); // constructor with parameters
//
//	// getters/setters
//	char* getName() { // get the addressof name string from private section
//		//return this->name;
//		return " ";
//	}
//	void setName(char* n) {
//		if (n != 0) {
//			if(this->name != 0) {
//				delete [] this->name;
//			}
//
//			this->name = new char[strlen(n) + 1];
//			strcpy(this->name,n);
//		}
//	}
//
//
//	// change first letter of the name
//	void changeName() {
//		if (this->name != NULL) 
//			this->name[0] = 'Y'; // change the name in private section
//	}
//};
//
//Student::Student() {
//	this->age = 0;
//	this->name = NULL;
//}
//
//Student::Student(int v, char* n) {
//	this->age = v;
//	if (n != NULL) {
//		this->name = new char[strlen(n)+1];
//		strcpy(this->name, n);
//	} else this->name = NULL;
//}
//
//void main() 
//{
//	Student x; // default constructor called
//	int age1 = 21; int age2 = 20;
//	char* name1 = "John"; char* name2 = "Smith";
//
//	Student s1(age1, name1); // constructor with parameters called
//	Student s2(age2, name2); // constructor with parameters called
//	Student s3 = s1; // default copy constructor; s3 shares same heap address with s1 for the attribute name
//					 // issues when name attributes are changedand deallocated
//
//	if(x.getName() != 0) {
//		cout<<"Name x: "<<x.getName()<<endl;
//	}
//	else {
//		cout<<"Anonymous name for x. "<<endl;
//	}
//
//	cout<<"Name s1: "<<s1.getName()<<", Name s2: "<<s2.getName()<<", Name s3: "<<s3.getName()<<endl;
//	s3.changeName();	
//	cout<<"Name s1: "<<s1.getName()<<", Name s2: "<<s2.getName()<<", Name s3: "<<s3.getName()<<endl;
//}

#include <iostream>

using namespace std;

class Student
{
private :
	  int cnp;
	  char* nume;
	  int nrnote;
	  double* note;
public :
	//student(){}

	 Student(int c = 0, char *n = "Anonimus", int nr = 0, double* vnote = NULL);
	 Student(const Student&);
	 Student& operator=(const Student&);
	 ~Student();

	 double calcMedie();
	 double& operator[](int);
	 bool operator>(Student);
	 bool operator<(Student);

	 int getCnp();
	 void setCnp(int);

	 char* getNume();
	 void setNume(char*);

	 int getNrnote();
	 void setNrnote(int);

	 double* getNote();
	 void setNote(double*);

/*
	 friend ostream& operator<<(ostream& iesire, Student& s) {
		iesire<<s.cnp<<":"<<s.nume<<endl;
		return iesire;
	}

	 friend istream& operator>>(istream& intrare, Student& s) {
		cout<<"Cnp: "; intrare>>s.cnp;
		cout<<"Nume: "; intrare>>s.nume;
		return intrare;
	}
*/
};

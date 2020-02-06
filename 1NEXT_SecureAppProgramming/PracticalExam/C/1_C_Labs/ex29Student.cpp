#include "ex29Student.hpp"
#include <iostream>
using namespace std;

Student::Student(int c, char* n, int nr, double* vnote) {	
	cout << "\n Constructor normal \n";
	int i=0;
	this->cnp=c;
	this->nume=new char[strlen(n)+1];
	strcpy(this->nume,n);
	this->nrnote=nr;
		
	if(nr != 0 && vnote != NULL) {
		this->note=new double[nr];
		for(i=0;i<nr;i++) this->note[i]=vnote[i];
	} else this->note = NULL;
}

Student::Student(const Student& st)	{
	cout << "\n Constructor de copiere \n";
	int i;
	this->cnp=st.cnp;
	
	this->nume=new char[strlen(st.nume)+1];
	strcpy(this->nume,st.nume);
	
	if (st.nrnote != 0) {
		this->nrnote=st.nrnote;
		this->note=new double[st.nrnote];

		for(i=0;i<st.nrnote;i++)
			this->note[i]=st.note[i];
        } else {
		this->nrnote = 0; this->note = NULL;
	}
}

Student& Student::operator=(const Student& st) {
	cout << "\n Operator= \n";
	int i;
	this->cnp=st.cnp;
	
	delete[] nume;
	this->nume=new char[strlen(st.nume)+1];
	strcpy(this->nume,st.nume);

	if (st.nrnote != 0) {
		this->nrnote=st.nrnote;
		delete[] this->note;
		this->note=new double[st.nrnote];

		for(i=0;i<st.nrnote;i++)
			this->note[i]=st.note[i];
        } else {
		this->nrnote = 0; this->note = NULL;
	}
	
	return *this;
}

Student::~Student() {
	cout << "\n Destructor";
	if(this->nume !=NULL) delete [] this->nume;
	if(this->note!=NULL) delete [] this->note;
}


double Student::calcMedie()	{
	int i=0;
	double rez=0;
		
	for(i=0;i < this->nrnote;i++)
		rez += this->note[i];
	if(nrnote!=0)
		rez /= this->nrnote;
	else  rez=-1;

	return rez;
}

double& Student::operator[](int pos) { 
	cout << "\n Supraincarcare op[]";
	if((pos>0) && (pos < this->nrnote)) {
		return this->note[pos];
	} else return this->note[0];
    
}

bool Student::operator>(Student st) { 
	return(strcmp(st.nume,this->nume) > 0)?1:0;
}

bool Student::operator<(Student st) { 
	return(strcmp(this->nume,st.nume) < 0)?1:0;
}

int Student::getCnp() {
	return this->cnp;
}

void Student::setCnp(int cnp) {
	this->cnp = cnp;
}

char* Student::getNume() {
	return this->nume;
}

void Student::setNume(char* n) {
	delete[] nume;
	this->nume=new char[strlen(n)+1];
	strcpy(this->nume, n);
}

int Student::getNrnote() {
	return this->nrnote;
}

void Student::setNrnote(int nr) {
	this->nrnote = nr;
}

double* Student::getNote() {
	return this->note;
}

void Student::setNote(double* stnote) {
	if (stnote != NULL) {
		if(this->note != NULL) delete[] this->note;
		this->note=new double[this->nrnote];

		for(int i=0;i<this->nrnote;i++)
			this->note[i]=stnote[i];
        } else {
		this->nrnote = 0; this->note = NULL;
	}
}

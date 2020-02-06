#include <iostream>
using namespace std;

class Employee {
private:
	int payRatio;
public:
	Employee() {
		cout<<"\n Employee::default constructor"<<endl;
		this->payRatio = 0;
	}

	Employee(int rp) {
		cout<<"\n Employee::parameter constructor"<<endl;
		this->payRatio = rp;
	}

	int getPayRatio() {
		cout<<"\n int Employee::getPayRatio()"<<endl;
		return this->payRatio;
	}

	virtual float pay(float hw) { // virtual
		cout<<"\n float Employee::pay(float hw)"<<endl;
		return hw * this->payRatio;		
		//other source code lines
	}

	virtual void raise(float grc) { // virtual
		cout<<"\n void Employee::raise(float grc)"<<endl;
		this->payRatio = this->payRatio + grc;
		//other source code lines
	}

	virtual void funct() = 0;
};
class SoftDev;
class Manager : public Employee {
private:
	int hasSalary;
public:
	Manager() {
		cout<<"\n Manager::default constructor"<<endl;
		this->hasSalary = 0;
		//this->payRatio = 0; 
	}

	Manager(int hs) {
		cout<<"\n Manager:: parameter constructor"<<endl;
		this->hasSalary = hs;
	}

	Manager(int hs, int tmp) : Employee(tmp) {
		cout<<"\n Manager:: parameters constructor"<<endl;
		this->hasSalary = hs;
	}

	int isSalary() {
		cout<<"\n int Manager::isSalary()"<<endl;
		return this->hasSalary;
	}

	float pay(float hw) {
		cout<<"\n float Manager::pay(float hw)"<<endl;
		if(!this->hasSalary)
			hw = 170;
		else
			hw = hw*1.25;
		
		return Employee::pay(hw);
		//other source code lines
	}
};

class SoftDev : public Employee {
private:
	int hasHigherEducation;
public:
	SoftDev(int hhe = 0, int tmp = 0) : Employee(tmp) {
		cout<<"\n SoftDev:: default/parameter constructor"<<endl;
		this->hasHigherEducation = hhe;
	}

	int getHigherEducation() {
		cout<<"\n int SoftDev::getHigherEducation()"<<endl;
		return this->hasHigherEducation;
	}
	
	float pay(float hw) {
		cout<<"\n float SoftDev::pay(float hw)"<<endl;
		if(!this->hasHigherEducation)
			hw = hw * 1.05;
		else
			hw = hw * 1.20;

		return Employee::pay(hw);
		//other source code lines
	}

	void raise(float grc) {
		cout<<"\n void SoftDev::raise(float grc)"<<endl;
		if(!this->hasHigherEducation)
			grc = grc * 1.10;
		else
			grc = grc * 1.30;

		Employee::raise(grc);
		//other source code lines
	}

	operator Manager() {
		return Manager();
	}
};

void main()
{
	Employee* pEmp = NULL;
	Employee Emp(5.);

	Manager Man(1, 7.5);
	SoftDev sDev(1, 7.);

	Emp = Man; //default cast
	float Payment = Emp.pay(180);  // this is not polymorphism
	
	Emp = sDev; //default cast
	Payment = Emp.pay(180); // this is not polymorphism

	pEmp = &Man; 
	// pEmp = new Manager(1, 7.5);
	Payment = pEmp->pay(180); // this is polymorphism ?
	pEmp = &sDev; 
	// pEmp = new SoftDev(1, 7.);
	Payment = pEmp->pay(180); // this is polymorphism ?

	/* Manager *pMan = &Man;
	pMan = pEmp; */   // conversion of base class pointer to sub-class pointer: 
				      // cannot be done with default implementation

}
#include "ex29Student.hpp"
#include <vector>

#include <list>

#include <algorithm>

using namespace std;


void afis(Student& St) {
	cout<<"\n Cnp==="<<St.getCnp();
	cout<<"\n Nume==="<<St.getNume();

}

bool comp(Student& St1, Student& St2) {
	return (St1 < St2);
}

void main(){
	//first use
	vector<int> v1;
	int dimv1;
	int x;
	
	cout<<"Size="<<v1.size()<<" Capacity="<<v1.capacity()<<endl;
	v1.reserve(20);
	cout<<"Size="<<v1.size()<<" Capacity="<<v1.capacity()<<endl;

	cout<<"Numarul de elemente=";cin>>dimv1;
	for (int i=0; i < dimv1; i++) {
		cin>>x;
		v1.push_back(x);
	}

	vector<int>::iterator it;
	for (it = v1.begin(); it != v1.end(); it++)
		cout<<(*it)<<endl;

	for(int i = 0; i < v1.size(); i++)
		cout<<v1[i]<<endl;

	cout<<endl<<"list:"<<endl;

	list<double> l;
	list<double>::iterator itl;

	l.push_back(45.02);
	l.push_front(103.22);
	l.push_back(77.01);
	l.insert(l.begin(), 99.11);
	l.insert(l.end(), 12.14);

	for (itl = l.begin(); itl != l.end(); itl++)
		cout<<(*itl)<<endl;

	double vd[3] = {105, 107, 109};
	l.insert(l.begin(), vd+0, vd+3);
	l.insert(l.end(), vd+1, vd+3);

	//gresit => musai trebuie itl = l.begin()
	for (; itl != l.end(); itl++)
		cout<<(*itl)<<endl;
	cout<<endl<<"end pass wrong for statement"<<endl;

        
	//second use
	vector<Student> v2;
	int dimv2;
	Student s2;

	cout<<"Size="<<v2.size()<<" Capacity="<<v2.capacity()<<endl;
	v2.reserve(20);
	cout<<"Size="<<v2.size()<<" Capacity="<<v2.capacity()<<endl;

	cout<<"Numarul de studenti=";
	cin>>dimv2;
	for (int j=0; j < dimv2; j++) {
		int cnpt; char numet[100]; int nrnotet; double notet[100]; 
		cout<<"Cnp=";cin>>cnpt;
		cout<<"Nume=";cin>>numet;
		cout<<"NrNote=";cin>>nrnotet;
		for(int k = 0; k < nrnotet; k++) {
			cout<<"\tNote["<<k<<"]=";
			cin>>notet[k];
		}

		s2.setCnp(cnpt); s2.setNume(numet); s2.setNrnote(nrnotet); s2.setNote(notet);

		v2.push_back(s2);
	}

	vector<Student>::iterator its;
	for (its = v2.begin(); its != v2.end(); its++)
		cout<<(*its).getCnp()<<":"<<(*its).getNume()<<endl;

	sort(v2.begin(), v2.end(), comp);
	for_each(v2.begin(), v2.end(), afis);
}

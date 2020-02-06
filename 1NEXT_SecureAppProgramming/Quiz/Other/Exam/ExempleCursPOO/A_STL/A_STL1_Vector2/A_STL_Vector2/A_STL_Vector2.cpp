#include "stdafx.h"

#include <iostream>
#include <vector>
using namespace std;

class Pers 
{
public:
	int varsta; char nume[50];
	Pers(int v=0, char * n="Anonim") : varsta(v)  { strcpy(nume,n); }
	bool operator<(const Pers p2) const { return varsta < p2.varsta ? 1:0; }
	friend ostream & operator<<(ostream & o, Pers &p)
		{ 		o<<"\n"<<p.nume<< "\t\t" << p.varsta; 	return o; 	}
};

void main()
{
	vector<int> vi(3);	// caz ideal: stim cate elemente
	vi[0]=10; vi[1]=15; vi[2]=98; 
	cout<<"\n Afisare elemente!!"<<endl; 
	// parcurgere folosind operator[] 
	for(int i=0;i<vi.size();i++) cout<<vi[i]<<endl;

	vector<Pers> vp(2); vp.reserve(3); // caz probabil: stim aproximativ, 2 sau 3 elemente
	Pers p1(20, "Domintan E"), p2(30, "Traistaru V"), p3(40, "Patrulescu I"), p4(50, "Cincinat P");
	vp[0]=p1; cout<<"\nNrElemente="<<vp.size()<<" cu extindere pana la ="<<vp.capacity()<<endl;
	vp[1]=p2; 
	// vp[2]=p3;  err depasire capacitate
	vp.push_back(p3); // utilizeaza rezervarea!
	vp.push_back(p4); // necesita mutare ?! depinde ... 
	for(int i=0;i<vp.size();i++) cout<<vp[i];
	vp[0]=p1; cout<<"\nDupa realocari: size ="<<vp.size()<<" capacity="<<vp.capacity()<<endl;
	
	vector<Pers*> vpp; // nu avem idee despre cate elemente vom tine
	vpp.push_back(new Pers(50, "John")); // adaugam, dar probabil nu muta, caci un element are doar 4 B (pointer)
	vpp.push_back(new Pers(60, "Helen")); vpp.push_back(new Pers(70, "Rita"));
	vector<Pers*>::iterator iter_pers; // parcurgere cu iterator, ineficienta
	for(iter_pers = vpp.begin(); iter_pers != vpp.end(); iter_pers++ ) //end() pointeaza dupa ultimul element
		cout << **iter_pers <<" " ;
    cout <<endl;

	// stergere element din mijloc
	iter_pers=vpp.begin()+vpp.size()/2; vpp.erase(iter_pers); cout << *vpp[1];

	// iterator reverse
	vector<Pers*>::reverse_iterator rit;
	for(rit= vpp.rbegin(); rit!= vpp.rend(); rit++) cout<<(**rit);

	system("pause");
}

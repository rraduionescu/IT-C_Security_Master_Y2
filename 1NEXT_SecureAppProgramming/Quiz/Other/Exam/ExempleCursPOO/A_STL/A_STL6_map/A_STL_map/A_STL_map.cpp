#include "stdafx.h"
#include <iostream>
#include <map> // vector asociativ
#include <string> // contine definitia clasei string
using namespace std;
typedef pair<string, int> pereche; // key : value
void main()
{
  string nume;  char aux[50];
  map<string, int> agenda; map<string, int>::iterator it;
  agenda.insert(pereche("Ionescu F",722123456)); // construire agenda
  agenda.insert(pereche("Popescu A",744123456));

  while(cout<<"\n Nume sau CTRL/Z:",  cin.getline(aux,50)  )//cautare in agenda 
  {
     nume=aux; it=agenda.find(nume);
     if(it!=agenda.end()) 
		cout<<"\n"<<(*it).first<<" tel: "<<(*it).second; // sau cout<<"\n"<<agenda[nume]; // acces prin cheie
     else 
		cout<<"\n"<<nume<<" nu exista in agenda!!!";
  }
  agenda.insert(pair<string,int>("aaa nnn",722123456));

  for(it=agenda.begin(); it!=agenda.end(); it++) // afisare continut agenda
	cout<<"\n"<<(*it).first<<" : "<<(*it).second;
  system("pause");
}

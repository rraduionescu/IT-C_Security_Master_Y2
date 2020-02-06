#include "stdafx.h"
#include<iostream>

using namespace std;
class Pers
{
private: 
	
	double salBaza, salEfectiv;
public:
	Pers(double sb=900, double se=1000): salBaza(sb), salEfectiv(se){ }
	double getSalBaza() { return salBaza;}
	double getSalEfectiv() { return salEfectiv;}

};

double calcPrima1(double sal, double procent) { return sal * procent/100;}
		// pointerul a fost incarcat complet si dereferit, in main

double calcPrima2(double (Pers::*pf)(), Pers &p, double procent) { return (p.*pf)() * procent/100;}
		// pointerul a fost incarcat doar cu deplasament si va fi dereferit in functie

void main()
{ 
	Pers p1;
	double (Pers::*pf)(); 
	pf=& Pers::getSalBaza;		cout <<"\n La sal Baza:    "<<calcPrima1( (p1.*pf)(), 10);
	pf=& Pers::getSalEfectiv;	cout <<"\n La sal Efectiv: "<<calcPrima2(pf, p1, 10);
	getchar();
}
#include "stdafx.h"
#include<iostream>

using namespace std;
class Pers
{
public: 
	Pers(double sb=900, double se=1000): salBaza(sb), salEfectiv(se){}
	double salBaza, salEfectiv;
};

double calcPrima1(double sal, double procent) { return sal * procent/100;}
		// pointerul a fost incarcat complet si dereferit, in main

double calcPrima2(double Pers::*psal, Pers &p, double procent) { return p.*psal * procent/100;}
		// pointerul a fost incarcat doar cu deplasament si va fi dereferit in functie

void main()
{ 
	Pers p1;
	double Pers::*ps; 
	ps=& Pers::salBaza;		cout <<"\n La sal Baza:    "<<calcPrima1(p1.*ps, 10);
	ps=& Pers::salEfectiv;	cout <<"\n La sal Efectiv: "<<calcPrima2(ps, p1, 10);
	getchar();
}


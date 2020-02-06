// A_STL_list.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <list>
#include <iostream>
using namespace std;

void main()
{
	list<double> ld;
	ld.push_back(10.1); ld.push_front(20.2); ld.push_back(30.3);
	ld.insert(ld.begin(),50.5); ld.insert(ld.end(),60.6);

	double vd[]={123.4,234.5,345.6, 456.7, 567.8};
	ld.insert(ld.begin(),vd+1,vd+4); // insert segment din vector clasic
	ld.sort();
	list<double>::iterator it;
	for(it= ld.begin(); it!= ld.end();it++) cout<<(*it)<<" ";
	system("pause");
}


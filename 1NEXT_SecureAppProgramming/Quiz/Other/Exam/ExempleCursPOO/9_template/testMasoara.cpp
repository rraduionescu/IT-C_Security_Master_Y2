// testMasoara.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"

#include <iostream> 
using namespace std;

template <class T=char, int n = 50>
class vector
{
	T v[n]; 		
	public:
	int spune_dim()  {   return n;   }
};

void main()
{
	vector<> v1;
	vector<double> v2;
	vector<int, 4> v3;
	cout<<v1.spune_dim()<<endl;
	cout<<v2.spune_dim()<<endl;
	cout<<v3.spune_dim(); getchar();
} 

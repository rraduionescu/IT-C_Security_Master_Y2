
#include "stdafx.h"
#include <iostream>
#include <math.h>
using namespace std;

class Punct
{
	double x,y;
public:
	Punct(double a=0, double o=0):x(a),y(o)  { }
	friend ostream & operator<<(ostream &ies, Punct  & p)
		{ ies << "  (" <<p.x <<" ," << p.y<<")";  return ies;}
	friend double dist(Punct &p1,Punct &p2)
	{ return sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y) ); }

};

class FigGeom
{
	int n;
	Punct vp[100];
public:
	FigGeom(Punct *v = new Punct(0,0), int nr=1):n(nr)
	{
		for(int i=0;i<nr;i++)
			vp[i]=v[i];
	}
	double perim()
	{
		double r=0;
		if(n==1)return 0.0;
		if(n==2)return dist(vp[0],vp[1]);
		for(int i=0;i<n-1;i++)
			r+= dist(vp[i],vp[i+1]);
		r+=dist(vp[n-1],vp[0]);
		return r;
	}
	friend ostream & operator<<(ostream &ies,   FigGeom& f)
	{ 
		ies << endl; 
		for(int i=0;i<f.n;i++)
			ies << f.vp[i];  
		return ies;
	}
};




void main()
{
	Punct p1(5,12), p2, p3(1,1);
	cout << p1<<" si "<<p2;
	Punct vp[]={p1,p2,p3, Punct(50,70), Punct(30,40)};
	FigGeom f1(vp, sizeof(vp)/sizeof(Punct)), f2;
	cout << endl<< dist(p2,p3);
	cout << endl<< f2.perim();
Punct vp2[]={p2,p3}; FigGeom f3(vp2, sizeof(vp2)/sizeof(Punct));cout << endl<< f3.perim();

	getchar();
}


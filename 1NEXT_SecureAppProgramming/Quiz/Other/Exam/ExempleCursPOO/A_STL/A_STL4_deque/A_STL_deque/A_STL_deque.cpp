#include "stdafx.h"
#include <deque>
#include <vector>
#include <algorithm>
#include <iostream>
using namespace std ;

int Fibonacci(void)
{
    static int r, f1 = 0, f2 = 1;
    r = f1 + f2 ;    f1 = f2 ;    f2 = r ;
    return f1 ;
}

void main()
 {
	int n = 10;
	deque<int> dq(15);	deque<int>::iterator pd;
	dq.push_front(0); // creste size la 16 !
	pd = dq.begin()+1;
	generate_n( pd, n, Fibonacci  ); // populare cu functie generatoare
	vector<int> vi(dq.size());
	for(pd = dq.begin(); pd != dq.end(); pd++ ) cout << *pd <<" " ;
    cout <<endl;
    for(int i=0; i<dq.size(); i++) { vi[i]=dq[i];  cout << vi[i] << " ";  }
	system("pause");
}



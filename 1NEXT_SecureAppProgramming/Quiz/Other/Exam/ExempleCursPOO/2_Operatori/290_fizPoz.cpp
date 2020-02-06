#include <iostream>
using namespace std;

#include <stdio.h>
#include <process.h>

class fisier;
class pozitie
   {
     private:   fisier *pof;       long poz;
     public:
       friend fisier;
       pozitie(fisier &f, long p): pof(&f), poz(p) { }
       void operator=(char c );
       void operator=(char *);
       operator const char( );
    };

class fisier
  {
    private : FILE *pf;
    public  :
		friend pozitie;
		fisier( const char *nume)
		{ 
		  pf=fopen(nume,"w+");
		  if(!pf) { cout<< "\n Esuare deschidere fisier"; exit(1); }
		}
		~fisier() { fclose(pf); }
		pozitie operator[ ](long p)
	   	     { fseek(pf,p,SEEK_SET); return pozitie(*this,p); }
 } ;

void pozitie::operator=(char c) 	{ if( pof->pf ) putc(c,pof->pf); }
void pozitie::operator=(char *s) 	{ if( pof->pf ) fputs(s,pof->pf); }
pozitie::operator const char ( )
  	{ if( pof->pf ) return getc(pof->pf);   return EOF;  }

void main()
  {
    fisier f("fisvect.dat");
    int i; char c;
    for(i=0;i<5; i++)  f[i]=i+48;//’0’; 
    f[5]='A';     f[6]="BBBBBBBBBB";
    cout << "\nPrimii 16B sunt: ";  
    for(i=0;i<16; i++)  { c=f[i];   cout << c; }
	getchar();
 }


#include <iostream>
using namespace std;

class persoana
    {
      private:	int virsta;
      public:
		char nume[20];
		double salariu;
		persoana(char n[20]="Anonim  ", int v=0, float s=1000000. ) 
				: virsta(v), salariu(s) { strcpy(nume,n);  }
		int get_virsta( ) const { return virsta; }
		double get_salariu( )   { return salariu; }
		void set_salariu(double s)  { salariu=s; }
		void f_m(const persoana p) { }
     };

void f_a(  persoana *p) 	{ }
void f_r(  persoana &p) 	{ }
void f_v( persoana p)		{ }

void main( )
{
	persoana const p1;
	persoana p2, p3;

	p2 = p1;
	// p1=p2;	- Eroare: obiectul const nu e l_valoare
	
// f_r(p1);    	- Eroare: f_r nu a declarat const obiectul referit, 
//	sub forma	  void f_r( const persoana &p) { }
	
// f_a(&p1);	- Eroare: f_a nu a declarat const obiectul adresat, 
		//	 sub forma	  void f_a(const persoana *p) {}
	
f_v(p1);

	cout << p1.get_virsta(); cout << p1.salariu;
	// cout << p1.get_salariu();	- Eroare: functie nedeclarata const
	// cout <<p1.set_salariu();  - Eroare: functie care modifica obiectul
	
	// p1.f_m(p1);
		// - Eroare: unul din obiecte, cel implicit nu e declarat const
		//   sub forma void f_m(const persoana p) const { }
}

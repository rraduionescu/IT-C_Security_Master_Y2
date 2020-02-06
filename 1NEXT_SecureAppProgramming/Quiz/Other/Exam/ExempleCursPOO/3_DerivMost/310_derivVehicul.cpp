#include <iostream>
using namespace std;
class Persoana
 {
	 char nume[50];
   public: 
		char *spune_nume() { return nume; }
};

class Vehicul 
{
  public:
	Vehicul() { cout << "Vehicul()\n"; }
	Vehicul(Vehicul&) { cout << " Vehicul(Vehicul & )\n " ; }
	Vehicul(int)	{ cout << " Vehicul(int)\n " ; }
	Vehicul& operator=(const Vehicul&)
		{
		  cout << "Vehicul::operator=()\n";
		  return *this;
		}
	Persoana proprietar;
	operator Persoana() const 
	{
		cout << "Vehicul::operator cast spre Persoana()\n";
		return proprietar;//Persoana();
	}
	~Vehicul() { cout << "~Vehicul()\n"; }
};

class Autoturism : public Vehicul {	};

void impoziteaza(Persoana i) {/* impoziteaza cladiri, auto ...*/ }

void main()
 {
    Autoturism d1;		// Apeleaza implicit si constructor din Vehicul
    Autoturism d2 = d1; // Apeleaza implicit copy-constructor din Vehicul
    d1 = d2;	// Operator= implicit pus de compilator, nu mostenit
		// El apeleaza pe cel din baza !
    impoziteaza(d1);	// cast catre Persoana, mostenit prin derivare
    ( (Persoana) d1 ).spune_nume(); getchar();
}

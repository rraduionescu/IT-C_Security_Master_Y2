#include <iostream>
using namespace std;

class persoana
   {
     private:	float salariu;
     protected:	int virsta;
     public:	
char nume[20];
	 	virtual void citire( )
	   	  {
	     		cout << "\n citeste pers";
	   	   }
		friend void apel( persoana &) ;
     };

class student : public persoana
      {
       private: int matricol;
       public: 
void citire( )
		    {
		     cout << "\n citeste stud";
		     }
      };

void apel( persoana &p) {   p.citire( );   }

void main( )
   {
      persoana p; student s; 
      apel(s); apel( p);
	  getchar();
   }


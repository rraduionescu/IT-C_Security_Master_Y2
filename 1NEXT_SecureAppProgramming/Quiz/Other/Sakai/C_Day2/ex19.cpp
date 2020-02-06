#include <iostream>
#include <string>
using namespace std;

namespace ism
{
	class string
	{
	private:
		int length;
		char* ps;
	public:
		string();
		string(const char*);
		string(const string&);
		~string();
		const string& operator=(const string&);

		const char* getCString();
		int getLength();

		const string& operator+=(const string&);
		
		//concatenation
		const string& operator+(const string&);
		const string& operator+(const char*);
		friend const string& operator+(const char*, const string&);
		
		friend ostream& operator<<(ostream&, ism::string&);
		friend istream& operator>>(istream&, ism::string&);

		//overloading unary operators  
		bool operator!() const; //tes empty string 
		char& operator[](int);
		const char& operator[](int) const;
		
		//overloading binary operators
		bool operator==(const string&) const; //test equality of 2 string objects 
		bool operator!=(const string&) const; //test difference of 2 string objects 
		bool operator<(const string&) const;
		bool operator>(const string&) const;
		bool operator<=(const string&) const;
		bool operator>=(const string&) const;

	}; //end class

	string::string(){
		this->length = 0;
		this->ps = NULL;
	}

	string::string(const char* str){
		this->length = strlen(str);
		this->ps = new char[this->length + 1];
		strcpy(this->ps, str);
	}

	string::~string() {
		if(this->ps) delete[] this->ps;
		this->length = 0;
		this->ps = NULL;
	}

	string::string(const string& strSrc){
		this->length = strSrc.length;
		if (strSrc.ps) {
			this->ps = new char[this->length + 1];
			strcpy(this->ps, strSrc.ps);
		} else this->ps = NULL;
	}

	const string& string::operator=(const string& strSrc) {
		if (this != &strSrc) {
			this->length = strSrc.length;
			if (this->ps) delete[] this->ps;
			this->ps = new char[this->length + 1];
			strcpy(this->ps, strSrc.ps);
		}
		return *this;
	}

	//same result like cout<<
	const char* string::getCString() {
		return this->ps;
	}

	int string::getLength() {
		return this->length;
	}

	const string& string::operator+=(const string& strSrc) {
		char* ptemp = this->ps;
		this->length += strSrc.length;
		this->ps = new char[this->length + 1];
		strcpy(this->ps, ptemp);
		strcat(this->ps, strSrc.ps);
		if (ptemp) delete[] ptemp;
		
		return *this;
	}

	const string& string::operator+(const string& strSrc) {
		string* tempS;
		tempS = new string();
		tempS->length = this->length + strSrc.length;
		if (tempS->ps != NULL) delete[] tempS->ps;
		tempS->ps = new char[tempS->length + 1];
		strcpy(tempS->ps, this->ps);
		strcat(tempS->ps, strSrc.ps);
		return (*tempS);
	}

	const string& string::operator+(const char* str) {
		string* tempS;
		tempS = new string();
		tempS->length = this->length + (int)strlen(str);
		if (tempS->ps != NULL) delete[] tempS->ps;
		tempS->ps = new char[tempS->length + 1];
		strcpy(tempS->ps, this->ps);
		strcat(tempS->ps, str);
		return (*tempS);
	}
	

	//overloading unary operators
	bool string::operator!() const {
		return (this->length == 0);
	}

	char& string::operator[](int pos) {
		if (pos >= 0 && pos < this->length) return this->ps[pos];
		else return this->ps[0];
	}

	const char& string::operator[](int pos) const {
		if (pos >= 0 && pos < this->length) return this->ps[pos];
		else return this->ps[0];
	}

	//overloading binary operators
	bool string::operator==(const string& s2) const { // test identity of two string  
		return strcmp(this->ps, s2.ps) == 0;
	}

	bool string::operator!=(const string& s2) const { // test difference between two string
		return !(*this == s2);
		//return strcmp(this->ps, s2.ps) != 0;
	}

	bool string::operator<(const string& s2) const {
		return strcmp(this->ps, s2.ps) < 0;
	}

	bool string::operator>(const string& s2) const {
		return !(s2 < *this);
		//return strcmp(this->ps, s2.ps) > 0;
	}

	bool string::operator<=(const string& s2) const {
		return !(s2 < *this);
	}
		
	bool string::operator>=(const string& s2) const {
		return !(*this < s2);
	}

	ostream& operator<<(ostream& out, ism::string& S) {
		out<<S.ps;
		return out;
	}

	istream& operator>>(istream& input,ism::string& S) {
		char temp[100];
		//input>>temp; 		//does not consider the blanks 
		input.getline(temp, sizeof(temp));

		//S=temp; <=> string tempObj(temp); S.operator =(tempObj);
		string tempObj(temp);
		S.operator =(tempObj);
		return input;
	}

	const ism::string& operator+(const char* op1, const ism::string& op2) {
		ism::string* tempS;
		tempS = new string();
		tempS->length = op2.length + (int)strlen(op1);
		if (tempS->ps != NULL) delete[] tempS->ps;
		tempS->ps = new char[tempS->length + 1];
		strcpy(tempS->ps, op1);
		strcat(tempS->ps, op2.ps);
		return (*tempS);
	}
}//end namespace

void main()
{

	//ism::string s1("First string ");
	//ism::string s2("and the second one.");
	////while(1) {
	//	s2 = s1;
	////}
	//std::string s3("First string again ");
	//std::string s4("and the second one.");
	//s3+=s4;
	////cout<<s1.getCString()<<endl;
	//cout<<s1<<endl;
	//cout<<s2<<endl;
	//cout<<s3<<endl;
	//cout<<s4<<endl;


	ism::string s1("Other string ");
	ism::string s2("and the second one.");

	s1 += s2;

	cout<<"s1="<<s1;

	ism::string s3, s4, s5;
	cout<<endl;
	cout<<"s3="; 
	cin>>s3;
	cout<<"s4="; 
	cin>>s4;

	s5=s3+s4;

	cout<<"\n s5="<<s5;

	s5 = s5 + " new text added to the string";		//commutativity
	s5 = " the final text " + s5;		//commutativity

	cout<<"\n s5="<<s5;
}
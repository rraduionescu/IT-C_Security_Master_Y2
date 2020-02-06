#include <iostream>
using namespace std;

namespace ism 
{
	class Array 
	{
	private:
		int dim;
		float* elem;
	public:
		Array();
		Array(int, float*);
		Array(Array&);
		~Array();
		Array& operator=(Array&);
		void dispElem();
		Array& operator+(Array&);
		Array& operator+(float);
		friend Array& operator+(float, Array&);
		float& operator[](int);
		float avg();
	};
	Array::Array() {
		this->dim = 0;
		this->elem = NULL;
	}
	Array::Array(int d, float* v) {
		this->dim = d;
		this->elem = new float[this->dim];
		for(int i = 0; i < this->dim; i++)
			this->elem[i] = v[i];
	}
	Array::Array(Array& v) {
		this->dim = v.dim;
		this->elem = new float[this->dim];
		for(int i = 0; i < this->dim; i++)
			this->elem[i] = v.elem[i];
	}
	Array::~Array() {
		if (this->elem != NULL) delete[] this->elem;
		this->elem = NULL;
		this->dim = 0;
	}
	Array& Array::operator =(Array& v) {
		if ((this->elem == v.elem)&&(this->dim == v.dim)) return (*this);
		if (this->elem != NULL) delete[] this->elem;
		this->dim = v.dim;
		this->elem = new float[this->dim];
		for(int i = 0; i < this->dim; i++)
			this->elem[i] = v.elem[i];

		return (*this);
	}
	void Array::dispElem() {
		cout<<"elements of the array: ";
		for(int i = 0; i < this->dim; i++)
			cout<<this->elem[i]<<", ";
		
		cout<<endl;
	}

	Array& Array::operator+(Array& v) {
		Array* temp = new Array();
		temp->dim = this->dim;
		temp->elem = new float[temp->dim];
		int i = 0;
		for(i = 0; i < this->dim; i++) temp->elem[i] = this->elem[i] + v.elem[i];
	
		return (*temp);
	}

	Array& Array::operator+(float el) {
		this->dim++;
		int i;
		float* temp = this->elem;
		if (temp) {
			this->elem = new float[this->dim];
			for(i = 0; i < (this->dim-1); i++) this->elem[i] = temp[i];
			
			this->elem[i] = el;
			delete[] temp;
		}
		return (*this);
	}
	Array& operator+(float el, Array& v) {
		v.dim++;
		float* temp = v.elem;
		if (temp) {
			v.elem = new float[v.dim];
			for(int i = 1; i < (v.dim); i++) v.elem[i] = temp[i-1];
			
			v.elem[0] = el;
			delete[] temp;
		}
		return v;
	}

	float& Array::operator[](int poz) {
		return (this->elem[poz]);
	}

	float Array::avg() {
		float rez = 0;
		for(int i = 0; i < this->dim; i++)
			rez += this->elem[i];
		rez /= this->dim;
		return rez;
	}
} //end namespace ism

void main() 
{
	float x[3] = {7,5,3};
	float y[3] = {8,5,6};

	ism::Array v1(3, x);
	ism::Array v2(3, y);
	ism::Array v3;

	v3 = v1 + v2;
	v3.dispElem();
} //end main
#include <iostream>
using namespace std;

namespace ism {
	template <class T>
	class Array {
	private:
		int dim;
		T* elem;
	public:
		Array();
		Array(int, T*);
		Array(Array<T>&);
		~Array();
		Array<T>& operator=(Array<T>&);
		void dispElem();
		Array<T>& operator+(Array<T>&);
		Array<T>& operator+(T);
		friend Array<T>& operator+(T, Array<T>&);
		T& operator[](int);
		T avg();
	};

	template <class T>
	Array<T>::Array() {
		this->dim = 0;
		this->elem = NULL;
	}

	template <class T>
	Array<T>::Array(int d, T* v) {
		this->dim = d;
		this->elem = new T[this->dim];
		for(int i = 0; i < this->dim; i++)
			this->elem[i] = v[i];
	}

	template <class T>
	Array<T>::Array(Array<T>& v) {
		this->dim = v.dim;
		this->elem = new T[this->dim];
		for(int i = 0; i < this->dim; i++)
			this->elem[i] = v.elem[i];
	}

	template <class T>
	Array<T>::~Array() {
		if (this->elem != NULL) delete[] this->elem;
		this->elem = NULL;
		this->dim = 0;
	}

	template <class T>
	Array<T>& Array<T>::operator =(Array& v) {
		if ((this->elem == v.elem)&&(this->dim == v.dim)) return (*this);
		if (this->elem != NULL) delete[] this->elem;
		this->dim = v.dim;
		this->elem = new T[this->dim];
		for(int i = 0; i < this->dim; i++)
			this->elem[i] = v.elem[i];

		return (*this);
	}

	template <class T>
	void Array<T>::dispElem() {
		cout<<"elements of the array: ";
		for(int i = 0; i < this->dim; i++)
			cout<<this->elem[i]<<", ";
		
		cout<<endl;
	}
	template <class T>
	Array<T>& Array<T>::operator+(Array<T>& v) {
		Array<T>* temp = new Array<T>();
		temp->dim = this->dim;
		temp->elem = new T[temp->dim];
		int i = 0;
		for(i = 0; i < this->dim; i++) 
			temp->elem[i] = this->elem[i] + v.elem[i];
		
		return (*temp);
	}

	template <class T>
	Array<T>& Array<T>::operator+(T el) {
		this->dim++;
		T* temp = this->elem;
		if (temp) {
			this->elem = new T[this->dim];
			for(int i = 0; i < (this->dim-1); i++) this->elem[i] = temp[i];
			
			this->elem[i] = el;
			delete[] temp;
		}
		return (*this);
	}

	template <class T>
	Array<T>& operator+(T el, Array<T>& v) {
		v.dim++;
		T* temp = v.elem;
		if (temp) {
			v.elem = new T[v.dim];
			for(int i = 1; i < (v.dim); i++) v.elem[i] = temp[i-1];
			
			v.elem[0] = el;
			delete[] temp;
		}
		return v;
	}
	template <class T>
	T& Array<T>::operator[](int poz) {
		return (this->elem[poz]);
	}
	template <class T>
	T Array<T>::avg() {
		T rez = 0;
		for(int i = 0; i < this->dim; i++)
			rez += this->elem[i];
		rez /= this->dim;
		return rez;
	}

	class Airplane {
	private:
		int Seats;
		int Engines;
	public:
		Airplane(int S = 0, int Eng = 0) {
			this->Seats = S;
			this->Engines = Eng;
		}
		Airplane& operator+(Airplane& a) {
			Airplane* temp = new Airplane(this->Seats, this->Engines);
			temp->Seats += a.Seats;
			temp->Engines += a.Engines;
			return (*temp);
		}
		friend ostream& operator<<(ostream& out, Airplane& a) {
			out<<"No of the seats="<<a.Seats<<" , No. of the engines ="<<a.Engines;
			return out;
		}
	};
}// end namespace ism

void main() {
	float x[3] = {7,5,3};
	float y[3] = {8,5,6};
	//float* z;

	ism::Array<float> v1(3, x);
	ism::Array<float> v2(3, y);
	ism::Array<float> v3;

	v3 = v1 + v2;
	v3.dispElem();
	
	ism::Airplane a1(300, 4); ism::Airplane a2(301, 6); 
	ism::Airplane a3(400, 4); ism::Airplane a4(500, 6);
	
	ism::Airplane av1[2] = {a1, a2}; 
	ism::Airplane av2[2]; 
	av2[0] = a3; av2[1] = a4;

	ism::Array<ism::Airplane> v4(2, av1);
	ism::Array<ism::Airplane> v5(2, av2);
	ism::Array<ism::Airplane> v6;

	v6 = v4 + v5;
	v6.dispElem();
}

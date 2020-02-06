//
//  main.cpp
//  sapGrileNoi
//
//  Created by Cătălina Șerban on 24/06/2019.
//  Copyright © 2019 Cătălina Șerban. All rights reserved.
//

#include <iostream>
#include<string.h>
#include<vector>
#include<list>
#include<algorithm>

using namespace std;

class Polygon {
public:
    void f() { cout<<"Polygon class"<<endl; }
};

class Rectangle: public Polygon {
public:
    void f() { cout<<"Rectangle class"<<endl; }
};

class Triangle: public Rectangle {
public:
    virtual void f() { cout<<"Triangle class"<<endl; }
};

class Person {
public:
    int age;
    char* name;
    
    Person(int v = 0, char* n = "Name"):age(v) {
        this->name = new char[strlen(n) + 1];
        strcpy(this->name, n);
        
        cout<<"Constructor with params"<<endl;
    }
    
    Person(Person& p) {
        this->age = p.age;
        this->name = new char[strlen(p.name) + 1];
        strcpy(this->name, p.name);
        
        cout<<"Copy constructor"<<endl;
    }
    
    ~Person() {
        delete [] this->name;
        
        cout<<"Destructor"<<endl;
    }
    
    void operator=(const Person& p) {
        this->age = p.age;
        delete[] this->name;
        this->name = new char[strlen(p.name) + 1];
        strcpy(this->name, p.name);
        
        cout<<"= oeprator call"<<endl;
    }
    
    friend Person operator+(Person p, int v) {
        Person t;
        t.age = p.age + v;
        return t;
        
        cout<<"+ operator call"<<endl;
    }
};

Person avgAge(Person a, Person b) {
    Person p;
    p.age = (a.age + b.age)/2;
    return p;
}


class Car {
private:
    int prodDate;
    char* color;
public:
    Car() {
        prodDate = 20160104;
        color = 0;
    }
    Car(int year, char* col) {
        this->prodDate = year;
        this->color = new char[strlen(col) + 1];
        strcpy(this->color, col);
    }
    
    Car(Car& p) {
        this->prodDate = p.prodDate;
        this->color = new char[strlen(p.color) + 1];
        strcpy(this->color, p.color);
        
        cout<<"Copy constructor"<<endl;
    }
    
//    void operator=(const Car& c) {
//        this->prodDate = c.prodDate;
//        delete[] this->color;
//        this->color = new char[strlen(c.color) + 1];
//        strcpy(this->color, c.color);
//
//        cout<<"= oeprator call"<<endl;
//    }
//
    ~Car() {
        delete[] this->color;
    }
};


class Car2 {
private:
    int prodDate;
    char* color;
public:
    Car2() {
        cout<<"Default constr without values";
    }
    Car2(int year = 0, char* col = "" ) {
        this->prodDate = year;
        this->color = new char[strlen(col) + 1];
        strcpy(this->color, col);
    }
    ~Car2() {
        delete[] this->color;
    }
};



class Bus {
private:
    int prodDate;
    char* color;
public:
    Bus() {
        prodDate = 20151215;
        color = 0;
    }
    Bus(int year = 0, char* initColor = "") {
        this->prodDate = year;
        this->color = new char[strlen(initColor) + 1];
        strcpy(this->color, initColor);
    }
    ~Bus() {
        delete[] this->color;
    }
};

//********** some code from the internet
class base
{
public:
    virtual void print ()
    { cout<< "print base class" <<endl; }
    
    void show ()
    { cout<< "show base class" <<endl; }
};

class derived:public base
{
public:
    void print ()
    { cout<< "print derived class" <<endl; }
    
    void show ()
    { cout<< "show derived class" <<endl; }
};

int main() {
    Polygon p, *pp;
    Rectangle r, *pr;
    Triangle t, *pt;
    
    //pp, pr, pt - adrese de memorie
    pp = &p; cout<<pp<<endl<<endl;
    pr = &r; cout<<pr<<endl<<endl;
    pt = &t; cout<<pt<<endl<<endl;

    p = r; //shallow copy the same as pp = pr6
    p.f();

    pp = pr; cout<<pp<<endl<<endl;
    pp->f();
    pp = pt; cout<<pp<<endl<<endl;
    pp->f();
    
    
    Person p1, p2(20, "John");
    Person p3;
    p3 = p2+10; //this is wrong
//    p3 = p1 + (p2, 10); //correct, after adding the const in front of the parameter of = overloading
    
    Person p4 = p1;
    p4 = avgAge(p1, p2); //aici se apeleaza de doua ori copy constructor - pentru p1 si p2 sa se copieze in parametrii a si b
    
    
    Car carA, carB(20160115, "Red");
    Car carC = carB; //aici ar trebui sa se apeleze copy constructorul pentru ca se face un obiect nou
    
    
    
    vector<int> v1;
    int dim = 10;
    int x;
    v1.reserve(dim);

    for(int i = 0; i < dim; i++) {
        x = (i + 1) * 10;
        v1.push_back(x);
    }

    list<int> l;
    list<int>::iterator itl;
    

    for(int i = 0; i < 10; i++)
//        l.pop_back(); //nu e nimic in lista, lista e empty
//    itl = l.begin();
//    cout<<(*itl)<<endl;
    
//    Bus busC; Call to constr is ambiguous - it is a compilation error 
    Bus busA(20151217, "Green"), busB(20151217, "Blue");
    
//    Car2 m1; //daca sunt mai multi constructori si nu apelezi explicit unul, el nu va sti ce sa apeleze => compile error
    Car2 m2(2000, "White");
    Car2 m3(1000);
    
    //atributele sunt private, nu pot fi acesate asa
//    cout<<m1.prodYear<<""<<m1.color<<endl;
//    cout<<m2.prodYear<<""<<m2.color<<endl;
//    cout<<m2.prodYear<<""<<m2.color<<endl;
    
    vector<int> v;
    int dimensiune = 20;
    int y;
    v.reserve(dimensiune);
    
    for(int i = 0; i < dimensiune; i++) {
        y = (i + 1) * 10;
        v.push_back(y);
    }
    
    list<int> lista;
    list<int>::iterator it;
    for(int i = 0; i < 10; i++) {
        lista.push_back(v[i]);
    }
    
    for(int i = 10; i < dimensiune; i++) {
        lista.insert(lista.begin(), v[i]);
    }
    
    for(it = lista.begin(); it != lista.end(); it++) {
        cout<<(*it)<<endl;
    }
    
    return 0;
}


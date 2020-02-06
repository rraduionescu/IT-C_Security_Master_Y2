package ro.ase.ism;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
	
/*	public final static long serialVersionUID = 
			-1263772154719801865l;*/
	
	String nume;
	int nrMatricol;
	ArrayList<Integer> note;
	int varsta;
	
	public Student(String nume, 
			int cod, 
			int[] note){
		this.nume = nume;
		this.nrMatricol = cod;
		this.note = new ArrayList<>();
		for(int nota:note)
			this.note.add(nota);
	}
	
	@Override
	public String toString(){
		String descriere = "Studentul "+this.nume+
				" are codul "+this.nrMatricol+ 
				" si notele: ";
		for(int nota:this.note)
			descriere+=nota+" ";
		return descriere;
	}
	
	@Override
	public int hashCode(){
		return this.nrMatricol;
	}
	
	@Override
	public boolean equals(Object stud){
		if(stud instanceof Student){
			Student student = (Student)stud;
			if(this.nrMatricol == student.nrMatricol &&
					this.nume.equals(
							student.nume))
				return true;
			else
				return false;
		}
		else
			return false;
	}
}

package ro.ase.ism;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable{
	
	public final static long serialVersionUID = 3869967928383931313l;
	String name;
	transient int code;
	//ArrayList<Integer> grades;
	int[] grades;
	char gender = 'F';
	
	public Student(String Name, int Code){
		name = Name;
		code = Code;
		//grades = new ArrayList<>();
	}
	
	public void addGrade(int grade){
		//this.grades.add(grade);
		grades = new int[]{9,10,9};
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Name "+this.name+"\n");
		sb.append("Code "+this.code+"\n");
		sb.append("Grades: ");
		for(int value : grades)
			sb.append(" "+value);
		return sb.toString();
	}
}

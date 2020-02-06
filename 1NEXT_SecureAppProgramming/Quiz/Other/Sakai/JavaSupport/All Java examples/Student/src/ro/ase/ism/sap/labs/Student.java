package ro.ase.ism.sap.labs;

import java.util.Arrays;

public class Student {
	private String name;
	private char faculty[];
	private float averageGrades[];
	private int grades[];
	
	public Student(){
		
	}
	public Student(String name, char facultyName[]){
		this.name = name;
		//this.faculty = facultyName;
		
		//this.faculty = facultyName.clone();
		
		this.faculty = new char[facultyName.length];
		for(int i=0;i<facultyName.length;i++)
			faculty[i] = facultyName[i];
		
		
	}
}

package ro.ase.ism;

public class Student implements Comparable<Student>{
	int code;
	int age;
	String name;
	
	public Student(int Code, int Age, String Name){
		code = Code;
		age = Age;
		name = Name;
	}
	
	@Override
	public boolean equals(Object object){
		if(object instanceof Student){
			Student temp = (Student)object;
			if(this.code == temp.code)
				return true;
			else
				return false;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return this.code;
	}

	@Override
	public int compareTo(Student o) {
		return this.name.compareTo(o.name);
	}
}

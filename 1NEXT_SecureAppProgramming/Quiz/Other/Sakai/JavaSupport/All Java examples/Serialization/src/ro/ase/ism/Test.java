package ro.ase.ism;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Test {

	public static void main(String[] args) {
		try{
			Student s = 
					new Student("John", 1);
			s.addGrade(9);
			s.addGrade(10);
			s.addGrade(9);
			System.out.println(s);
			
			File file = 
					new File("Students.bin");
			if(!file.exists())
				file.createNewFile();
			
/*			FileOutputStream fos = 
					new FileOutputStream(file);
			ObjectOutputStream oos = 
					new ObjectOutputStream(fos);
			oos.writeObject(s);
			
			oos.close();
			
			System.out.println("Student saved !");*/
			
			FileInputStream fis =
					new FileInputStream(file);
			ObjectInputStream ois = 
					new ObjectInputStream(fis);
			
			Student s2 = 
					(Student)ois.readObject();
			
			ois.close();
			
			System.out.println("Student recovered !");
			System.out.println(s2);
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

}

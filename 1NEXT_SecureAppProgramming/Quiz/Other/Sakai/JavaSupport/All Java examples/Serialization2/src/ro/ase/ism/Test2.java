package ro.ase.ism;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Test2 {

	public static void main(String[] args) {
		File fisier = new File("studenti2.dat");
		if(!fisier.exists())
			try {
				fisier.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		try {
			
/*			int[] note = {10,10,8};
			Student stud = 
					new Student("Gigel",1001,note);
			
			FileOutputStream fos = 
					new FileOutputStream(fisier);
			ObjectOutputStream oos = 
					new ObjectOutputStream(fos);
			oos.writeObject(stud);
			
			oos.close();*/
			
			FileInputStream fis = 
					new FileInputStream(fisier);
			ObjectInputStream ois =
					new ObjectInputStream(fis);
			Student student2 = 
					(Student)ois.readObject();
			ois.close();
			
			System.out.println(student2.toString());
					
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

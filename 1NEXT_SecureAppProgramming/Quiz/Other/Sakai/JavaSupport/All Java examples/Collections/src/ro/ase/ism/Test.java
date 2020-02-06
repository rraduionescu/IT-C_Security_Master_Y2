package ro.ase.ism;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class Test {

	public static void main(String[] args) {
		
		ArrayList<Integer> values = 
				new ArrayList<>();
		values.add(23);
		values.add(12);
		values.add(30);
		values.add(12);
		
		System.out.println("Array list:");
		for(Integer value : values){
			System.out.println(value);
		}
		
		//set
		HashSet<Integer> set = 
				new HashSet<>();
		set.add(23);
		set.add(12);
		set.add(30);
		set.add(12);
		
		System.out.println("Set values: ");
		for(Integer value : set){
			System.out.print(" "+value);
		}
		
		HashMap<Integer,String> map =
				new HashMap<>();
		
		map.put(23, "John");
		map.put(30, "Alice");
		map.put(12,	"Bob");
		map.put(23,	"Alex");
		
		System.out.println("Map values:");
		for(Integer key : map.keySet()){
			System.out.println(
					key + " - "+map.get(key));
		}
		
		TreeSet<Student> students = 
				new TreeSet<>();
		students.add(new Student(1,21,"John"));
		students.add(new Student(2,21,"Alice"));
		students.add(new Student(3,21,"Bob"));
		students.add(new Student(1,21,"Alex"));
		
		for(Student s : students){
			System.out.println(
					s.name + " - "+s.code);
		}
	}

}

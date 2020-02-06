package ro.ase.ism.sap.labs;

public class Test {
	public static void main(String[] args) {
		String name = "Popescu";
		name = name  + " Ionut";
		
		System.out.println(
				"The name is "+name);
		
		String name1 = "John";
		String name2 = "John";
		
		if(name1 == name2)
			System.out.println("They are equal");
		
		String name3 = new String("John");
		String name4 = new String("John");
		
		if(name3.equals(name4))
			System.out.println("They are equal");
		else
			System.out.println("NOT equal");
		
		name2 = "Alice";
		
		name2 = name2 + "Flower";
		
		float f1 = 12.3f;
		char c = 'a';
		String s = "a";
		
		int vb1 = 10;
		Integer v1 = 10;
		v1 = 20;
		vb1 = v1;	//unboxing
		v1 = vb1;	//boxing
		
		Integer v3 = 127;
		Integer v4 = 127;
		
		if(v3 == v4)
			System.out.println("The numbers are equal !");
		else
			System.out.println("They are not equal");
		
		//arrays
		int[] vect1;
		int vect2[] = {2,3,4,5,6};
		System.out.println("Vect2 size is "+vect2.length);
		
		
		vect1 = new int[10];
		for(int i=0;i<vect1.length;i++){
			vect1[i] = i+1;
			int aux;
		}
		
		
		System.out.println("The array values are:");
		for(int value : vect1)
			System.out.print(" "+value);
		
		//shallow copy
		vect2 = vect1;
		
		vect2[1] = 99;
		
		System.out.println("The array values are:");
		for(int value : vect1)
			System.out.print(" "+value);
		
		vect2 = vect1.clone();
		
		vect2[1] = 55;
		
		System.out.println("The array values are:");
		for(int value : vect1)
			System.out.print(" "+value);
		
		//int vb5;
		//System.out.println(vb5);
		
		String test1 = "Test1" + "Test2" + "Test3";
		
		StringBuilder sb = new StringBuilder();
		sb.append("Test1");
		sb.append("Test2");
		sb.append("Test3");
		System.out.println(sb.toString());
		
		int x1 = 10;
		if(x1==10){
			System.out.println("It's 10");
		}
		
		Integer y1  = 10;
		Integer y2 = ++y1;
		Integer y3 = y1++;
		
		System.out.println(y1);
		System.out.println(y2);
		System.out.println(y3);
	}
}

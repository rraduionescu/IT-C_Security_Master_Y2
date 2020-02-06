package ro.ase.ism.sap.labs;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Test {

	static void solveEquation(int a, int b, int c) 
			throws NegativeDeltaException{
		int delta = b*b - 4*a*c;
		
		if(delta < 0)
			throw new NegativeDeltaException("Negative");
		
		System.out.println("Computing square root");
		
		double sqr = Math.sqrt(delta);
		
		double x1 = ((-1)*b+sqr)/(2*a);
		double x2 = ((-1)*b-sqr)/(2*a);
		
		System.out.println("x1 = "+x1);
		System.out.println("x2 = "+x2);
		
		int vb = Integer.parseInt("dsfdsf");
	}
	
	public static void main(String[] args) {
		Scanner scanner = 
				new Scanner(System.in);
		try{
		System.out.println("a = ");
		int a = scanner.nextInt();
		System.out.println("b = ");
		int b = scanner.nextInt();
		//int b = Integer.parseInt(scanner.nextLine());
		System.out.println("c = ");
		int c = scanner.nextInt();
	
		Test.solveEquation(a,b,c);
		}
		catch(InputMismatchException e){
			System.out.println("Wrong input");
			//System.out.println(e.getMessage());
			//e.printStackTrace();
		}
		catch(NegativeDeltaException e){
			System.out.println("Negative delta ex");
		}
		catch(Exception e){
			System.out.println("Something bad has happened");
		}
		finally{
			System.out.println("Program terminated");
		}
		
	}

}

package ro.ase.ism.sap;

public class Test {

	public static void main(String[] args) {
		//Product p = new Product("Test",12.5);
		//it's used as a reference type
		System.out.println("Program started");
		
		Product p;
		
		try {
			KidsProduct kp = 
					new KidsProduct("RC car",
							250, -6);
			System.out.println("Toy created");
			
			
			Product list[] = new Product[3];
			//Product[] list2 = new Product[3];
			list[0] = kp;
			list[1] = new ElectronicProduct("Fridge",
					300, "A+");
			list[2] = new KidsProduct(
					"Doll", 56, 3);
			
			for(Product prod : list){
				System.out.println(
						prod.getDescription());
				
			}
			
		} 

		catch (MinAgeException e) {
			System.out.println("Wrong age");
		}
		catch(Exception ex){
			System.out.println("Error:"+ex.getMessage());
		}
		finally{
			System.out.println("Program terminated");
		}
		
	}

}

package ro.ase.ism.sap;

public class Test {

	public static void main(String[] args) {
		BankAccount account = 
				new BankAccount(1000);
		BankCard husband = new BankCard("Husband", 
				account);
		BankCard wife = new BankCard("Wife", 
				account);
		
		Thread t1 = new Thread(husband);
		Thread t2 = new Thread(wife);
		
		t1.start();
		t2.start();
	}

}

package ro.ase.ism.sap.labs;

public class Test {

	public static void main(String[] args) {
		
		Account a1;
		a1 = new Account("John", 1);
		Account a2 = new Account("Alice", 1);
		
		System.out.println("Account:"+a1);
		System.out.println("Account owner:"
				+a1.getOwner());
		
		//shallow copy
		a1  = a2;
		System.out.println("Account owner:"
				+a1.getOwner());
		try {
			a2.setOwner("Bianca");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Account owner:"
				+a1.getOwner());
		
		Account a3 = a1;
		System.out.println("Account owner:"
				+a3.getOwner());
		
		Account a4 = new Account(a3);
		
		BankAccount ba1 = 
				new BankAccount("Mike",1,1001);
		System.out.println("The owner is "+
				ba1.getOwner());
		
		BankAccount ba2 = 
				new BankAccount("Jonh", 1, 557);
		
		try {
			ba1.Transfer(100, ba2);
		} catch (IllegalTransferException e) {
			System.out.println("Illegal transfer");
		} catch (InsufficientFundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

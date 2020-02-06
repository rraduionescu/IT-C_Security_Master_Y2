package ro.ase.ism.sap;

public class BankAccount {
	double balance;
	
	public BankAccount(double Balance){
		this.balance = Balance;
	}
	
	 public synchronized void withdraw(double amount){
		System.out.println(
				"Trying to withdraw "+amount);
		if(amount<=balance){
			System.out.println(
					"It's OK. Withdraw "+amount);
			balance -= amount;
			System.out.println("Current balance "+
			balance);
		}
	}
}

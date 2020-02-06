package ro.ase.ism.sap;

import java.util.Random;

public class BankCard implements Runnable{
	String owner;
	BankAccount account;
	
	public BankCard(
			String name,BankAccount Account) {
		this.owner = name;
		this.account = Account;
		
	}
	
	@Override
	public void run() {
		if(account!=null){
			while(account.balance>0){
				Random rand = new Random();
				double value = 
						rand.nextInt(10);
				System.out.println(owner + 
						"is trying to buy something "+
						"with the price "+value);
				this.account.withdraw(value);
				
			}
		}
	}
	
}

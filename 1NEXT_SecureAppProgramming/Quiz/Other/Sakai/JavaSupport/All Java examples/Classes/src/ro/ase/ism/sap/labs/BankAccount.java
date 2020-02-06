package ro.ase.ism.sap.labs;

public class BankAccount extends Account implements Profitable{
	private double balance;
	private int id;

	public BankAccount(
			String owner,
			int type,
			int id){
		super(owner,type);
		this.id = id;
		this.balance = 0;
		int accType = this.currencyType;
	}
	

	
	@Override
	public void Transfer(
			double amount, Account destination) 
					throws IllegalTransferException, 
					InsufficientFundsException{
		super.Transfer(amount, destination);
		if(this.balance > amount){
			this.balance-= amount;
			((BankAccount)destination).balance+=amount;
		}
		else
			throw new InsufficientFundsException();
	}



	@Override
	public void applyInterest(double interestRate) {
		this.balance*=(1+interestRate);
	}
	
}

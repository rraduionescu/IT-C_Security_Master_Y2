package ro.ase.ism.sap.labs;

public class Account extends AbstractAccount {
	protected String owner;
	protected final int currencyType;
	
	public Account(String owner,int Type){
		this.owner = owner;
		currencyType = Type;
	}
	
	//public interface
	String getOwner(){
		return owner;
	}
	void setOwner(String name) throws Exception{
		if(name == null || name.equals(""))
			throw new Exception("NUll input");
		else
			this.owner = name;
	}
	int getCurrencyType(){
		return this.currencyType;
	}
	
	//overriding methods inherited from Object
	@Override
	public String toString(){
		return "The owner is "+this.owner+
				" and the type is "+
				this.currencyType;
	}
	@Override
	public boolean equals(Object a){
		Account refA = (Account)a;
		if(this.owner.equals(refA.owner))
			return true;
		else
			return false;
	}
	
	public Account(Account a) {
		this.currencyType = a.currencyType;
		this.owner = a.owner;
	}
	
	public void Transfer(
			double value, Account destination) 
					throws IllegalTransferException,
					InsufficientFundsException
	{
		if(value <= 0 || destination == this )
			throw new IllegalTransferException();
	}

	
	public void Withdraw(double amount) throws IllegalTransferException{
		if(amount <= 0)
			throw new IllegalTransferException();
	}

}

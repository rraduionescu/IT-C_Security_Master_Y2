package ro.ase.ism.sap.labs;

public abstract class AbstractAccount {
	
	int value;
	
	public abstract void Withdraw(double amount) throws IllegalTransferException;
	
	public void doSomething(){
		
	}
}

package ro.ase.ism.sap.labs;

public class Card {
	private final int id;
	private String owner;
	private double balance;
	private Banks bank;
	
	private static int noCards = 0;
	
	public Card(String owner, 
			Banks bank){
		
		noCards++;
		
		this.id = noCards;
		this.owner = owner;
		this.bank = bank;
		this.balance = 0;
	}
}

package ro.ase.ism.sap;

public abstract class Product {
	final int id;
	protected String description;
	protected float price;
	static int counter = 0;
	
	//block of code
	{
		System.out.println("Building an object");
	}
	
	//static block of code
	static {
		System.out.println("Product loaded");
	}
	
	public Product(String description,
			float Price){
		id = ++Product.counter;
		this.description = description;
		this.price = Price;
	}
	
	public abstract String getDescription();
}

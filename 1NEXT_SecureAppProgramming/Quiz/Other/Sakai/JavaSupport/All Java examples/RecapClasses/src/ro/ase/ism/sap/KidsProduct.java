package ro.ase.ism.sap;

public class KidsProduct extends Product 
	implements Sellable{
	int minAge;
	
	public KidsProduct(String description,
			float price,
			int minAge) throws MinAgeException{
		super(description,price);
		
		if(minAge<1)
			throw new MinAgeException("Incorrect age");
		
		this.minAge = minAge;
	}

	@Override
	public String getDescription() {
		return "The toy "+description+
				" has a price of "+price+
				" and is for kids with at leat "+
				minAge+" year old";
	}
	
	void setPrice(float price){
		this.price = price;
	}

	@Override
	public void reducePrice(float amount) {

	}
}

package ro.ase.ism.sap;

public class ElectronicProduct extends Product{
	String greenLabel;
	
	public ElectronicProduct(String description,
			float price,
			String label){
		super(description,price);
		this.greenLabel = label;
	}

	@Override
	public String getDescription() {
		return this.description+
				" has green label "+greenLabel;
	}
}

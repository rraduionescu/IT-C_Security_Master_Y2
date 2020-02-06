package ro.ase.ism.sap;

public class GenericBox<T> {
	float weight;
	T content;
	
	public GenericBox(T Content,
			float Weight){
		this.content = Content;
		this.weight = Weight;
	}
	
	public void display(){
		System.out.println(content
				+" - "+weight);
	}
		
}

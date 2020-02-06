package ro.ase.ism.sap;

public class Box {
	float weight;
	Object content;

	public Box(Object Content, float Weight) {
		content = Content;
		weight = Weight;
	}
	
	public void display(){
		System.out.println(content + 
				" weights "+weight);
	}
	
	Object getContent(){
		return content;
	}

}

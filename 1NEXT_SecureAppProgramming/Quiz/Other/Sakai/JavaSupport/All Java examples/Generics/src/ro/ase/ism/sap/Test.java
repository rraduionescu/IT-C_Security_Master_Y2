package ro.ase.ism.sap;

public class Test {

	public static void main(String[] args) {
		
		Computer computer = new Computer();
		Flower flower = new Flower();
		
		Box b1 = new Box(computer,1.5f);
		Box b2 = new Box(flower,0.1f);
		
		b1.display();
		b2.display();
		
		
		
		//Flower f2 = (Flower)b1.getContent();
		
		GenericBox<Flower> gb1 =
				new GenericBox<Flower>(flower, 0.1f);
		//GenericBox<Flower> gb2 =
				//new GenericBox<Flower>(computer, 0.1f);
		
		
	}

}

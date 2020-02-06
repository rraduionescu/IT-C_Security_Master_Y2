package ro.ase.ism.sap;

public class FunctionSum implements Processable{

	@Override
	public double doSomethingWithData(
			int[] values) {
		if(values!=null){
			int s = 0;
			for(int value : values)
				s+=value;
			return s;
		}
		return 0;
	}

}

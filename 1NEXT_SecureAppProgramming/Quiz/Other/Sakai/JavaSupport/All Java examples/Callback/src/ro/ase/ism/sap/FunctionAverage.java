package ro.ase.ism.sap;

public class FunctionAverage implements Processable{

	@Override
	public double doSomethingWithData(int[] values) {
		if(values!=null && values.length>0){
			double s = 0;
			for(int value : values)
				s+=value;
			return s/values.length;
		}
		return 0;
	}

}

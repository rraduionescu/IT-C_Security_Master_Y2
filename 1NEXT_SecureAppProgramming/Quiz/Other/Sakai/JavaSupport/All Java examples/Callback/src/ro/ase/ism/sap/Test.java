package ro.ase.ism.sap;

public class Test {
	public static void main(String[] args){
		
		DataEntity data = new DataEntity();
		data.addValue(20);
		data.addValue(30);
		data.addValue(40);

		double result = 0;
		
		data.setFunction(new FunctionSum());
		try {
			result = data.processData();
			data.setFunction(new FunctionAverage());
			result = data.processData();
			
			data.setFunction(new Processable() {
				
				@Override
				public double doSomethingWithData(int[] values) {
					if(values!=null&&values.length>0){
						int min = values[0];
						for(int value : values){
							if(min > value)
								min = value;
						}
						return min;
					}
					else
						return Double.POSITIVE_INFINITY;
				}
			});
			
			result = data.processData();
			
			
		} catch (Exception e) {

		}
		
		System.out.println("Result "+result);
		
	}
}

package ro.ase.ism.sap;

public class DataEntity {
	int[] values;
	
	private Processable entity;
	
	public void addValue(int value){
		if(values == null){
			values = new int[1];
			values[0] = value;
		}
		else
		{
			int[] temp = 
					new int[values.length+1];
			for(int i=0;i<values.length;i++)
				temp[i] = values[i];
			temp[values.length] = value;
			values = temp;
		}
	}
	
	public void setFunction(Processable ref){
		this.entity = ref;
	}
	
	public double processData() throws Exception{
		if(entity!=null)	
			return entity.doSomethingWithData(values);
		else
			throw new Exception("No processing enity");			
	}
}

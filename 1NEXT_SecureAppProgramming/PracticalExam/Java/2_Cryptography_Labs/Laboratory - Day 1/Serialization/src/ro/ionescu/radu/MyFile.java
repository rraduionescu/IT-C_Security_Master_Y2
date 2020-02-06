package ro.ionescu.radu;

public class MyFile
{
	private String fileName;
	private int attribute;
	private HashValue hashValue;

	public MyFile(String fileName, int attribute, HashValue hashValue)
	{
		this.fileName = fileName;
		this.attribute = attribute;
		this.hashValue = (HashValue) hashValue.clone();
	}

	@Override
	public String toString()
	{
		return fileName + " has the attribute " + attribute + " and hash: " + hashValue;
	}
}

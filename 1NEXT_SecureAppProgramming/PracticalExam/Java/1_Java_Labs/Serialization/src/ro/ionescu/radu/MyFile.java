package ro.ionescu.radu;

import java.io.*;

public class MyFile implements Serializable
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

	public MyFile()
	{
	}

	@Override
	public String toString()
	{
		return fileName + " has the attribute " + attribute + " and hash: " + hashValue;
	}

	public void serialize(String binaryFile) throws IOException
	{
		File file = new File(binaryFile);
		if (!file.exists())
		{
			file.createNewFile();
		}

		FileOutputStream fos = new FileOutputStream(file);
		DataOutputStream dos = new DataOutputStream(fos);

		dos.writeInt(this.attribute);
		dos.writeUTF(this.fileName);
		dos.writeUTF(this.hashValue.getAlgorithm());
		dos.write(this.hashValue.getHash().length);
		dos.write(this.hashValue.getHash());

		fos.close();
	}

	public void deserialize(String binaryFile) throws FileNotFoundException, IOException
	{
		File file = new File(binaryFile);
		if (!file.exists())
		{
			throw new FileNotFoundException();
		} else
		{
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);

			this.attribute = dis.readInt();
			this.fileName = dis.readUTF();

			String algorithm = dis.readUTF();
			int hashValueSize = dis.readInt();
			byte[] value = new byte[hashValueSize];
			dis.read(value, 0, hashValueSize);
			this.hashValue = new HashValue(value, algorithm);

			fis.close();
		}
	}

	public void javaSerialize(String binaryFile) throws IOException
	{
		File file = new File(binaryFile);
		if(!file.exists())
		{
			file.createNewFile();
		}

		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		fos.close();
	}
}

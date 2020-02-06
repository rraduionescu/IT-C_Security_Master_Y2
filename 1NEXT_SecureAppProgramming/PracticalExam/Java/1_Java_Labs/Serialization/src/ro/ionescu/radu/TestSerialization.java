package ro.ionescu.radu;

import java.io.IOException;

public class TestSerialization
{
	public static void main(String[] args)
	{
		byte[] value = new byte[]{(byte) 0x0A, (byte) 0xD3, (byte) 0b11110010, (byte) 200};

		HashValue hashValue = new HashValue(value, "MD5");
		MyFile myFile = new MyFile("Secret.txt", 2, hashValue);

		try
		{
			myFile.serialize("MyData.dat");
			myFile.javaSerialize("BinaryData.dat");

			MyFile newFile = new MyFile();
			myFile.deserialize("MyData.dat");
			System.out.println(newFile);
		} catch (IOException e)
		{
			System.out.println("Error: " + e.getMessage());
		}

		System.out.println(hashValue);
		System.out.println(myFile);
	}
}

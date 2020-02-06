package ro.ionescu.radu;

public class TestSerialization
{
	public static void main(String[] args)
	{
		byte[] value = new byte[]{(byte) 0x0A, (byte) 0xD3, (byte) 0b11110010, (byte) 200};

		HashValue hashValue = new HashValue(value, "MD5");
		MyFile myFile = new MyFile("Secret.txt", 2, hashValue);

		System.out.println(hashValue);
		System.out.println(myFile);
	}
}

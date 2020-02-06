package ro.ionescu.radu;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class SHAFactory
{
	public static byte[] getHash(String inputFileName, String algorithm, String provider) throws NoSuchProviderException, NoSuchAlgorithmException, IOException
	{
		byte[] hashValue;
		MessageDigest messageDigest = MessageDigest.getInstance(algorithm, provider);

		File inputFile = new File(inputFileName);
		if(!inputFile.exists())
		{
			throw new FileNotFoundException();
		}

		FileInputStream fileInputStream = new FileInputStream(inputFile);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		byte[] buffer = new byte[8];
		int numberOfBytes;

		while((numberOfBytes = bufferedInputStream.read(buffer)) > 0)
		{
			messageDigest.update(buffer,0,numberOfBytes);
		}

		bufferedInputStream.close();
		fileInputStream.close();

		hashValue = messageDigest.digest();

		return hashValue;
	}
}

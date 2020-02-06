package ro.ionescu.radu;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class CryptographyFactory
{
	// 1. Generate hash value for a given file

	public static byte[] getHashValue(String inputFileName, String hashFunctionName, String provider) throws NoSuchProviderException, NoSuchAlgorithmException, IOException
	{
		byte[] hashValue;
		MessageDigest messageDigest = MessageDigest.getInstance(hashFunctionName, provider);

		File inputFile = new File(inputFileName);
		if(!inputFile.exists())
		{
			throw new FileNotFoundException();
		}

		FileInputStream fis = new FileInputStream(inputFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		byte[] buffer = new byte[8];
		int numberOfBytes;

		System.out.println("Blocks:");
		int j = 0;
		while((numberOfBytes = bis.read(buffer)) > 0)
		{
			System.out.print(" Block " + ++j + " ");
			for(int i = 0 ; i < numberOfBytes ; i++)
			{
				System.out.print((char)buffer[i]);
			}
			System.out.println();

			messageDigest.update(buffer, 0, numberOfBytes);
		}

		fis.close();
		hashValue = messageDigest.digest();

		return hashValue;
	}

	// 2. Generate hash value for a given String

	public static byte[] getStringHashValue(String message, String algorithm) throws NoSuchAlgorithmException
	{
		byte[] hashValue = null;

		MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
		messageDigest.update(message.getBytes());

		hashValue = messageDigest.digest();

		return hashValue;
	}

	// 3. Encrypt a file with a block cipher in ECB mode

	public static void encryptECB(String fileName, String algorithm, String key, String provider, String outputFile) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException
	{
		File input = new File(fileName);
		if(!input.exists())
		{
			throw new FileNotFoundException();
		}
		File output = new File(outputFile);
		if(!output.exists())
		{
			output.createNewFile();
		}

		FileInputStream fis = new FileInputStream(input);
		FileOutputStream fos = new FileOutputStream(output);

		String cipherType = algorithm + "/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(cipherType, provider);

		SecretKeySpec spec = new SecretKeySpec(key.getBytes(), algorithm);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm, provider);
		SecretKey secretKey = keyFactory.generateSecret(spec);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] buffer = new byte[cipher.getBlockSize()];
		int noBytes = 0;
		byte[] cipherBlock;

		while((noBytes = fis.read(buffer)) > 0)
		{
			int outputNoBytes = cipher.getOutputSize(noBytes);
			cipherBlock = new byte[outputNoBytes];
			outputNoBytes = cipher.update(buffer, 0, noBytes, cipherBlock);
			fos.write(cipherBlock, 0, outputNoBytes);
		}

		cipherBlock = cipher.doFinal();
		fos.write(cipherBlock);

		fis.close();
		fos.close();
	}

	public static void decryptECB(String fileName, String algorithm, String key, String provider, String outputFile) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException
	{
		File input = new File(fileName);
		if(!input.exists())
		{
			throw new FileNotFoundException();
		}
		File output = new File(outputFile);
		if(!output.exists())
		{
			output.createNewFile();
		}

		FileInputStream fis = new FileInputStream(input);
		FileOutputStream fos = new FileOutputStream(output);

		String cipherType = algorithm + "/ECB/PKCS5Padding";
		Cipher cipher = Cipher.getInstance(cipherType, provider);

		SecretKeySpec spec = new SecretKeySpec(key.getBytes(), algorithm);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm, provider);
		SecretKey secretKey = keyFactory.generateSecret(spec);

		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		byte[] buffer = new byte[cipher.getBlockSize()];
		int noBytes = 0;
		byte[] cipherBlock;

		while((noBytes = fis.read(buffer)) > 0)
		{
			int outputNoBytes = cipher.getOutputSize(noBytes);
			cipherBlock = new byte[outputNoBytes];
			outputNoBytes = cipher.update(buffer, 0, noBytes, cipherBlock);
			fos.write(cipherBlock, 0, outputNoBytes);
		}

		cipherBlock = new byte[cipher.getBlockSize()];
		int outputNoBytes = cipher.doFinal(cipherBlock, 0);
		fos.write(cipherBlock, 0, outputNoBytes);

		fis.close();
		fos.close();
	}
}

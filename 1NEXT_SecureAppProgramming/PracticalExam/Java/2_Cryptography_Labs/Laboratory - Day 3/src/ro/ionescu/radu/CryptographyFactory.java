package ro.ionescu.radu;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

public class CryptographyFactory
{
	// Electronic Code Book - All File Data in a Single Run
	// 						- using only doFinal() on the entire data ( no update() loop )
	public static void encryptECB(String inputFileName, String password, String algorithm, String outputFileName) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, ShortBufferException, IllegalBlockSizeException
	{
		// INPUT FILE
		RandomAccessFile raf = new RandomAccessFile(inputFileName, "r");

		// OUTPUT FILE
		File outputFile = new File(outputFileName);
		if (!outputFile.exists())
		{
			outputFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(outputFile);

		// CIPHER OBJECT
		Cipher cipher = Cipher.getInstance(algorithm + "/ECB/PKCS5Padding");

		// SECRET KEY
		SecretKeySpec specification = new SecretKeySpec(password.getBytes(), algorithm);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey secretKey = keyFactory.generateSecret(specification);

		// CIPHER INITIALIZATION
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		// INPUT READING
		long fileSize = raf.length();
		byte[] fileContent = new byte[(int) fileSize];
		raf.read(fileContent);

		// ENCRYPTION
		byte[] cipherText = new byte[cipher.getOutputSize((int) fileSize)];
		int noBytes = cipher.doFinal(fileContent, 0, (int) fileSize, cipherText);

		// OUTPUT WRITING
		fos.write(cipherText, 0, noBytes);

		//FILE CLOSING
		raf.close();
		fos.close();
	}

	public static void decryptECB(String inputFileName, String password, String algorithm, String outputFileName) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, ShortBufferException, IllegalBlockSizeException
	{
		// INPUT FILE
		RandomAccessFile raf = new RandomAccessFile(inputFileName, "r");

		// OUTPUT FILE
		File outputFile = new File(outputFileName);
		if (!outputFile.exists())
		{
			outputFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(outputFile);

		// CIPHER OBJECT
		Cipher cipher = Cipher.getInstance(algorithm + "/ECB/PKCS5Padding");

		// SECRET KEY
		SecretKeySpec specification = new SecretKeySpec(password.getBytes(), algorithm);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey secretKey = keyFactory.generateSecret(specification);

		// CIPHER INITIALIZATION
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		// INPUT READING
		long fileSize = raf.length();
		byte[] fileContent = new byte[(int) fileSize];
		raf.read(fileContent);

		// DECRYPTION
		byte[] cipherText = new byte[cipher.getOutputSize((int) fileSize)];
		int noBytes = cipher.doFinal(fileContent, 0, (int) fileSize, cipherText);

		// OUTPUT WRITING
		fos.write(cipherText, 0, noBytes);

		//FILE CLOSING
		raf.close();
		fos.close();
	}

	// Cipher Block Chaining
	public static void encryptCBC(String inputFileName, String password, String algorithm, String outputFileName) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, ShortBufferException, IllegalBlockSizeException, InvalidAlgorithmParameterException
	{
		// INPUT FILE
		File inputFile = new File(inputFileName);
		if (!inputFile.exists())
		{
			throw new FileNotFoundException();
		}

		// OUTPUT FILE
		File outputFile = new File(outputFileName);
		if (!outputFile.exists())
		{
			outputFile.createNewFile();
		}

		// FILE STREAMS
		FileInputStream fis = new FileInputStream(inputFile);
		FileOutputStream fos = new FileOutputStream(outputFile);

		// INITIAL VALUE - generated randomly and written at the begining of the cipher-text
		SecureRandom secureRandom = new SecureRandom();
		byte[] initialValue = new byte[password.getBytes().length];
		secureRandom.nextBytes(initialValue);
		fos.write(initialValue);

		// CIPHER OBJECT
		Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");

		// SECRET KEY
		SecretKeySpec specification = new SecretKeySpec(password.getBytes(), algorithm);

		// CIPHER INITIALIZATION
		IvParameterSpec parameterSpecification = new IvParameterSpec(initialValue);
		cipher.init(Cipher.ENCRYPT_MODE, specification, parameterSpecification);

		// INPUT READING, ENCRYPTION AND OUTPUT WRITING
		byte[] buffer = new byte[cipher.getBlockSize()];
		int noBytes;
		byte[] cipherBlock;
		int cipherNoBytes;

		while ((noBytes = fis.read(buffer)) > 0)
		{
			cipherNoBytes = cipher.getOutputSize(noBytes);
			cipherBlock = new byte[cipherNoBytes];
			cipherNoBytes = cipher.update(buffer, 0, noBytes, cipherBlock);

			fos.write(cipherBlock, 0, cipherNoBytes);
		}
		cipherBlock = cipher.doFinal();
		fos.write(cipherBlock);

		// FILE CLOSING
		fis.close();
		fos.close();
	}

	public static void decryptCBC(String inputFileName, String password, String algorithm, String outputFileName) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, BadPaddingException, ShortBufferException, IllegalBlockSizeException, InvalidAlgorithmParameterException
	{
		// INPUT FILE
		File inputFile = new File(inputFileName);
		if (!inputFile.exists())
		{
			throw new FileNotFoundException();
		}

		// OUTPUT FILE
		File outputFile = new File(outputFileName);
		if (!outputFile.exists())
		{
			outputFile.createNewFile();
		}

		// FILE STREAMS
		FileInputStream fis = new FileInputStream(inputFile);
		FileOutputStream fos = new FileOutputStream(outputFile);

		// INITIAL VALUE - reading from begining of file
		byte[] initialValue = new byte[password.getBytes().length];
		fis.read(initialValue);

		// CIPHER OBJECT
		Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");

		// SECRET KEY
		SecretKeySpec specification = new SecretKeySpec(password.getBytes(), algorithm);

		// CIPHER INITIALIZATION
		IvParameterSpec parameterSpecification = new IvParameterSpec(initialValue);
		cipher.init(Cipher.DECRYPT_MODE, specification, parameterSpecification);

		// INPUT READING, ENCRYPTION AND OUTPUT WRITING
		byte[] buffer = new byte[cipher.getBlockSize()];
		int noBytes;
		byte[] cipherBlock;
		int cipherNoBytes;

		while ((noBytes = fis.read(buffer)) > 0)
		{
			cipherNoBytes = cipher.getOutputSize(noBytes);
			cipherBlock = new byte[cipherNoBytes];
			cipherNoBytes = cipher.update(buffer, 0, noBytes, cipherBlock);

			fos.write(cipherBlock, 0, cipherNoBytes);
		}
		cipherBlock = cipher.doFinal();
		fos.write(cipherBlock);

		// FILE CLOSING
		fis.close();
		fos.close();
	}

	// HMAC generation
	public static byte[] getHMAC(String inputFileName, String password, String algorithm) throws IOException, NoSuchAlgorithmException, InvalidKeyException
	{
		byte[] hmac;

		// INPUT FILE
		File inputFile = new File(inputFileName);
		if (!inputFile.exists())
		{
			throw new FileNotFoundException();
		}
		FileInputStream fis = new FileInputStream(inputFile);

		// MAC OBJECT INITIALIZATION
		Mac mac = Mac.getInstance(algorithm);
		mac.init(new SecretKeySpec(password.getBytes(), algorithm));

		// READING INPUT AND GENERATING MAC
		byte[] buffer = new byte[1];
		int noBytes;
		while ((noBytes = fis.read(buffer)) > 0)
		{
			mac.update(buffer, 0, noBytes);
		}
		hmac = mac.doFinal();

		// FILE CLOSING
		fis.close();

		return hmac;
	}
}

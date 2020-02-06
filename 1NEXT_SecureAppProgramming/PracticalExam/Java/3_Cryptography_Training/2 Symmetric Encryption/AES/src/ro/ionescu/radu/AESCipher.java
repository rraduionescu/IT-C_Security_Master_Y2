package ro.ionescu.radu;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

public class AESCipher
{
	public static void encrypt(String inputFileName, String outputFileName, String provider, String algorithm, String mode, String padding, String key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, ShortBufferException, BadPaddingException, IllegalBlockSizeException, NoSuchProviderException, InvalidAlgorithmParameterException
	{
		File inputFile = new File(inputFileName);
		if(!inputFile.exists())
		{
			throw new FileNotFoundException();
		}
		FileInputStream fileInputStream = new FileInputStream(inputFile);

		File outputFile = new File(outputFileName);
		if(!outputFile.exists())
		{
			outputFile.createNewFile();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
		SecretKey secretKey = secretKeyFactory.generateSecret(secretKeySpec);

		String cipherType = algorithm + "/" + mode + "/" + padding;
		Cipher cipher = Cipher.getInstance(cipherType, provider);
		byte[] IV = new byte[cipher.getBlockSize()];
		for(byte b : IV)
		{
			b = 0x0f;
		}
		IvParameterSpec parameterSpec = new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

		int inputNumberOfBytes;
		int outputNumberOfBytes;
		byte[] inputBlock = new byte[cipher.getBlockSize()];
		byte[] cipherBlock;

		while((inputNumberOfBytes = fileInputStream.read(inputBlock)) > 0)
		{
			outputNumberOfBytes = cipher.getOutputSize(inputNumberOfBytes);
			cipherBlock = new byte[outputNumberOfBytes];
			outputNumberOfBytes = cipher.update(inputBlock, 0, inputNumberOfBytes, cipherBlock);
			fileOutputStream.write(cipherBlock, 0, outputNumberOfBytes);
		}

		cipherBlock = cipher.doFinal();
		fileOutputStream.write(cipherBlock);

		fileInputStream.close();
		fileOutputStream.close();
	}
}

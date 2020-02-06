package ro.ionescu.radu;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class DESCipher
{
	public static void encrypt(String inputFileName, String algorithm, String provider, String key, String outputFileName, String mode, String padding) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, ShortBufferException, BadPaddingException, IllegalBlockSizeException
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
		Cipher cipher = Cipher.getInstance(cipherType);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] inputBlock = new byte[cipher.getBlockSize()];
		int inputNumberOfBytes;
		byte[] cipherBlock;
		int outputNumberOfBytes;

		while((inputNumberOfBytes = fileInputStream.read(inputBlock)) > 0)
		{
			outputNumberOfBytes = cipher.getOutputSize(inputNumberOfBytes);
			cipherBlock = new byte[outputNumberOfBytes];
			outputNumberOfBytes = cipher.update(inputBlock, 0, inputNumberOfBytes, cipherBlock);
			fileOutputStream.write(cipherBlock, 0, outputNumberOfBytes);
		}

		cipherBlock = cipher.doFinal();
		fileOutputStream.write(cipherBlock);
	}
}
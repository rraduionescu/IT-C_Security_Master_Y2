package ro.ionescu.radu.sap;

import com.sun.org.apache.bcel.internal.classfile.Utility;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;

public class IonescuRaduStefan
{
	public static void main(String[] args)
	{
		try
		{
			// 1. Generate and display the SHA-1 hash of the "Client.key" file.
			RandomAccessFile encryptedAesKeyFile = new RandomAccessFile("ClientISM.key", "r");
			byte[] encryptedAesKeyContent = new byte[(int)encryptedAesKeyFile.length()];
			encryptedAesKeyFile.readFully(encryptedAesKeyContent);
			MessageDigest messageDigestSha1 = MessageDigest.getInstance("SHA-1");
			byte[] sha1Hash = messageDigestSha1.digest(encryptedAesKeyContent);
			System.out.println("1. SHA-1 Hash     : " + Utility.toHexString(sha1Hash).toUpperCase());

			// 2. Using the client private key from "sapkeystore.ks" Java keystore, decrypt the key
			// file and display the key in plaintext.
			KeyStore javaKeystore = KeyStore.getInstance("jks");
			FileInputStream keystoreInputStream = new FileInputStream("sapkeystore.ks");
			javaKeystore.load(keystoreInputStream, "passks".toCharArray());
			PrivateKey privateKey = (PrivateKey)javaKeystore.getKey("sapkey1", "sapex2016".toCharArray());
			Cipher rsaCipher = Cipher.getInstance("RSA/ECB/NoPadding");
			rsaCipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = rsaCipher.doFinal(encryptedAesKeyContent);
			System.out.println("2. Decrypted Key  : " + new String(output));

			// 3. Using the decrypted symmetric key, decrypt the "Comm.enc" file, encrypted with
			// the AES key in ECB mode with PKCS5 padding.
			FileInputStream encryptedInputStream = new FileInputStream("Comm.enc");
			SecretKeySpec secretKeySpec = new SecretKeySpec("ThisIsItPassword".getBytes(), "AES");
			Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			aesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			System.out.print("3. Decrypted Text : ");
			int inputNo, outputNo = 0;
			byte[] outputBlock, inputBlock = new byte[aesCipher.getBlockSize()];
			while((inputNo = encryptedInputStream.read(inputBlock)) > 0)
			{
				outputNo = aesCipher.getOutputSize(inputNo);
				outputBlock = new byte[outputNo];
				outputNo = aesCipher.update(inputBlock, 0, inputNo, outputBlock);
				System.out.print(new String(outputBlock));
			}
			outputBlock = new byte[outputNo];
			outputNo = aesCipher.doFinal(outputBlock, 0);
			String finalOutput = new String(outputBlock);
			System.out.print(finalOutput.substring(0, outputNo));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
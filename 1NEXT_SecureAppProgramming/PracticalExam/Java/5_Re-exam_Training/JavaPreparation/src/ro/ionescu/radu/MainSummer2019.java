package ro.ionescu.radu;

import com.sun.org.apache.bcel.internal.classfile.Utility;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;

class MainSummer2019
{
	public static void main(String[] args)
	{
		try
		{
			System.out.println("\n1. Generate and display in hex format the SHA-1 hash of \"ClientISM.key\" file");
			RandomAccessFile encryptedAESKey = new RandomAccessFile("ClientISM.key", "r");
			byte[] encryptedAESKeyBytes = new byte[(int) encryptedAESKey.length()];
			encryptedAESKey.readFully(encryptedAESKeyBytes);
			MessageDigest SHA1Hash = MessageDigest.getInstance("SHA-1");
			byte[] SHA1HashBytes = SHA1Hash.digest(encryptedAESKeyBytes);
			System.out.println("   SHA-1 hash of encrypted AES key:\n\n\t\t" + Utility.toHexString(SHA1HashBytes).toUpperCase());
			System.out.println("\n2. Using the client private key, stored in the Java keystore sapkeystore.ks, \n   decrypt the key file and extract the key plaintext value (and display it as String)");
			KeyStore sapKeyStore = KeyStore.getInstance("jks");
			FileInputStream keyStoreInputStream = new FileInputStream("sapkeystore.ks");
			sapKeyStore.load(keyStoreInputStream, "passks".toCharArray());
			PrivateKey clientPrivateKey =  (PrivateKey)sapKeyStore.getKey("sapkey1","sapex2016".toCharArray());
			Cipher RSACipher = Cipher.getInstance("RSA/ECB/NoPadding");
			RSACipher.init(Cipher.DECRYPT_MODE, clientPrivateKey);
			byte[] decryptedPassword = RSACipher.doFinal(encryptedAESKeyBytes);
			String AESKey = new String(decryptedPassword).substring(240,256);
			System.out.println("   Plaintext AES encryption password:\n\n\t\t" + AESKey);
			System.out.println("\n3. Once the client receives the symmetric password, decrypt the Comm.enc file,\n   encrypted with the AES key in ECB mode with PKCS5 padding");
			RandomAccessFile dataFile = new RandomAccessFile("Comm.enc", "r");
			byte[] data = new byte[(int)dataFile.length()];
			dataFile.readFully(data);
			Cipher AESCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec secretKeySpec = new SecretKeySpec(AESKey.getBytes(), "AES");
			AESCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] decryptedData = AESCipher.doFinal(data);
			System.out.println("   Decrypted data:\n\n\t\t" + new String(decryptedData));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
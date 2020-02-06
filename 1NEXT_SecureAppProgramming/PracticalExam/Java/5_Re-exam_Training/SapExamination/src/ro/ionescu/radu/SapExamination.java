package ro.ionescu.radu;

//	@author Ionescu Radu Stefan	 //

import com.sun.org.apache.bcel.internal.classfile.Utility;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SapExamination
{
	public static void main(String[] args)
	{
		try
		{
			// New provider
			if(Security.getProvider("BC") == null)
			{
				System.out.println("Bouncy Castle security provider not installed!");
				System.out.println("Proceeding to installation...");
				Security.addProvider(new BouncyCastleProvider());
				System.out.println("Done!\n");
			}

			// Generate RSA private key used in the application and store it in Java KeyStore
			// 		keytool -genkey -keyalg RSA -alias sapkey1 -keypass sapex2016 -storepass passks -keystore sapkeystore.ks
			// 		-dname "cn=Catalin Boja, ou=ISM, o=IT&C Security Master, c=RO"
			// => sapkeystore.ks

			// Generate RSA public key corresponding to keystore private key and export to X509 certificate
			//		keytool -export -alias sapkey1 -file SAPCertificateX509.cer -keystore sapkeystore.ks -storepass passks
			// => SAPCertificateX509.cer

			// AES key for encrypting the sent data
			String AESKeyString = "parolaputernica#";
			byte[] AESKeyBytes = AESKeyString.getBytes();
			System.out.println("AES key to encrypt TXT : " + AESKeyString);
			System.out.println("AES key to encrypt HEX : " + Utility.toHexString(AESKeyBytes).toUpperCase() + "\n");

			// RSA public key to encrypt the AES key to securely send it to client
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
			FileInputStream certificateInputStream = new FileInputStream("SAPCertificateX509.cer");
			X509Certificate certificate = (X509Certificate)certificateFactory.generateCertificate(certificateInputStream);
			PublicKey RSAPublicKey = certificate.getPublicKey();
			System.out.println("RSA public key HEX     : " + Utility.toHexString(RSAPublicKey.getEncoded()).toUpperCase().substring(99, 866));

			// AES key encryption with RSA public key
			Cipher RSACipher = Cipher.getInstance("RSA");
			RSACipher.init(Cipher.ENCRYPT_MODE, RSAPublicKey);
			byte[] encryptedAESKey = RSACipher.doFinal(AESKeyBytes);
			System.out.println("AES encrypted key HEX  : " + Utility.toHexString(encryptedAESKey).toUpperCase() + "\n");
			RandomAccessFile keyOut = new RandomAccessFile("ClientISM.key", "rw");
			keyOut.write(encryptedAESKey);
			keyOut.close();

			// AES key decryption with RSA private key
			RandomAccessFile keyIn = new RandomAccessFile("ClientISM.key", "r");
			byte[] AESKeyEncryptedContent = new byte[(int)keyIn.length()];
			keyIn.readFully(AESKeyEncryptedContent);
			FileInputStream keyStoreInputStream = new FileInputStream("sapkeystore.ks");
			KeyStore keyStore = KeyStore.getInstance("jks");
			keyStore.load(keyStoreInputStream, "passks".toCharArray());
			PrivateKey RSAPrivateKey = (PrivateKey)keyStore.getKey("sapkey1","passks".toCharArray());
			RSACipher.init(Cipher.DECRYPT_MODE, RSAPrivateKey);
			byte[] AESDecryptedKeyContent = RSACipher.doFinal(AESKeyEncryptedContent);
			System.out.println("AES decrypted key TXT  : " + new String(AESDecryptedKeyContent));
			System.out.println("AES decrypted key HEX  : " + Utility.toHexString(AESDecryptedKeyContent).toUpperCase() + "\n");
			keyIn.close();

			// Encrypted AES key SHA-1 hash to ensure integrity of the sent AES key
			MessageDigest SHA1 = MessageDigest.getInstance("SHA-1");
			byte[] SHA1Hash = SHA1.digest(AESKeyEncryptedContent);
			System.out.println("SHA-1 Hash             : " + Utility.toHexString(SHA1Hash).toUpperCase());

			// Encrypt a message with the AES key using ECB mode and PKCS5 padding
			RandomAccessFile dataFile = new RandomAccessFile("Message.txt","r");
			RandomAccessFile outputFile = new RandomAccessFile("Message.enc","rw");
			byte[] data = new byte[(int)dataFile.length()];
			dataFile.readFully(data);
			Cipher AESCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec secretKey = new SecretKeySpec(AESDecryptedKeyContent, "AES");
			AESCipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedData = AESCipher.doFinal(data);
			outputFile.write(encryptedData);
			dataFile.close();
			outputFile.close();

			// Decrypt the message and print it
			RandomAccessFile encryptedFile = new RandomAccessFile("Message.enc","r");
			RandomAccessFile decryptedFile = new RandomAccessFile("Decrypted.txt","rw");
			byte[] encryptedContent = new byte[(int)encryptedFile.length()];
			encryptedFile.readFully(encryptedContent);
			AESCipher.init(Cipher.DECRYPT_MODE, secretKey);
			byte[] decryptedData = AESCipher.doFinal(encryptedContent);
			System.out.println("Decrypted message      : " + new String(decryptedData));
			decryptedFile.write(decryptedData);
			encryptedFile.close();
			decryptedFile.close();

			// Sign the message
			Signature messageSignature = Signature.getInstance("NONEwithRSA");
			messageSignature.initSign(RSAPrivateKey);
			messageSignature.update(data);
			byte[] signature = messageSignature.sign();

			// Verify message signature
			Signature verificationSignature = Signature.getInstance("NONEwithRSA");
			verificationSignature.initVerify(RSAPublicKey);
			verificationSignature.update(data);
			boolean isVerified = verificationSignature.verify(signature);
			if(isVerified)
			{
				System.out.println("\nMessage signature      : Verification complete!");
			}
			else
			{
				System.out.println("Message signature verification failed!");
			}

			// Generate RSA Keys, encrypt, decrypt, sign and verify a message
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			KeyPair RSAKeys = generator.generateKeyPair();
			PublicKey RSAPublicKeyG = RSAKeys.getPublic();
			PrivateKey RSAPrivateKeyG = RSAKeys.getPrivate();

			Signature RSASignature = Signature.getInstance("NONEwithRSA");
			RSASignature.initSign(RSAPrivateKeyG);
			RSASignature.update(data);
			byte[] RSASign = RSASignature.sign();

			Signature verification = Signature.getInstance("NONEwithRSA");
			verification.initVerify(RSAPublicKeyG);
			verification.update(data);
			boolean isAuthentic = verification.verify(RSASign);
			if(isAuthentic)
			{
				System.out.println("RSA signature          : Authentic!\n");
			}
			else
			{
				System.out.println("RSA signature is not authentic");
			}

			Cipher RSACipherData = Cipher.getInstance("RSA");
			RSACipherData.init(Cipher.ENCRYPT_MODE, RSAPublicKeyG);
			RSACipherData.update(data);
			byte[] encryptedDataRSA = RSACipherData.doFinal();

			RSACipherData.init(Cipher.DECRYPT_MODE, RSAPrivateKeyG);
			RSACipherData.update(encryptedDataRSA);
			byte[] decryptedDataRSA = RSACipherData.doFinal();
			System.out.println("RSA decrypted          : " + new String(decryptedDataRSA));

			// Decrypt EncryptedMessage.cipher AES/CBC/NoPadding IV:0101...0101
			byte AES_KEY[] = {
								0x2A, 0x4D, 0x61, 0x73, 0x74, 0x65, 0x72, 0x20,
								0x49, 0x53, 0x4D, 0x20, 0x32, 0x30, 0x31, 0x37
							};
			byte IVBytes[] = {
								0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01,
								0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01
							};
			RandomAccessFile encFile = new RandomAccessFile("EncryptedMessage.cipher", "r");
			byte[] encContent = new byte[(int)encFile.length()];
			encFile.readFully(encContent);
			SecretKeySpec AESSecretKey = new SecretKeySpec(AES_KEY, "AES");
			IvParameterSpec IV = new IvParameterSpec(IVBytes);
			Cipher AES_CBC_Cipher = Cipher.getInstance("AES/CBC/NoPadding");
			AES_CBC_Cipher.init(Cipher.DECRYPT_MODE, AESSecretKey, IV);
			byte[] decryptedCBCMessage = AES_CBC_Cipher.doFinal(encContent);
			System.out.println("AES CBC decrypted      : " + new String(decryptedCBCMessage));

			// Encrypt a message with DES using CBC mode and PKCS5 padding
			String message = "This is the message that will be encrypted by DES in CBC mode with PKCS5 Padding.";
			SecretKeySpec DES_Key = new SecretKeySpec("paroldes".getBytes(), "DES");
			IvParameterSpec IVDES = new IvParameterSpec(new byte[]{0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01});
			Cipher DES_CBC_Cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DES_CBC_Cipher.init(Cipher.ENCRYPT_MODE, DES_Key, IVDES);
			byte[] DES_Encrypted = DES_CBC_Cipher.doFinal(message.getBytes());
			System.out.println("\nDES CBC ciphertext     : " + Utility.toHexString(DES_Encrypted).toUpperCase());

			// Decrypt the message with DES using CBC mode and PKCS5 padding
			DES_CBC_Cipher.init(Cipher.DECRYPT_MODE, DES_Key, IVDES);
			byte[] DES_Decrypted = DES_CBC_Cipher.doFinal(DES_Encrypted);
			System.out.println("DES decrypted          : " + new String(DES_Decrypted));

			// Compute the HMAC of data
			Mac hmac = Mac.getInstance("HmacMD5");
			hmac.init(new SecretKeySpec("parolaputernica#".getBytes(), "AES"));
			byte[] hmacBytes = hmac.doFinal(data);
			System.out.println("\nHMAC                   : " + Utility.toHexString(hmacBytes).toUpperCase());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
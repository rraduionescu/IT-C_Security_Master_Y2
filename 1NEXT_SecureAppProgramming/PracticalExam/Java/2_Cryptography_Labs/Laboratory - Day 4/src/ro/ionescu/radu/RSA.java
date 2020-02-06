package ro.ionescu.radu;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class RSA
{
	public static void getKeystoreInfo(String keystoreFileName, String keystorePassword) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException
	{
		// INPUT FILE
		File file = new File(keystoreFileName);
		if (!file.exists())
		{
			throw new FileNotFoundException();
		}
		FileInputStream fis = new FileInputStream(file);

		// READING KEYSTORE
		KeyStore keyStore = KeyStore.getInstance("jks");
		keyStore.load(fis, keystorePassword.toCharArray());
		Enumeration<String> entries = keyStore.aliases();

		while (entries.hasMoreElements())
		{
			String alias = entries.nextElement();
			System.out.println("Entry: " + alias);
			if(keyStore.isKeyEntry(alias))
			{
				System.out.println("Key entry");
			}
			if(keyStore.isCertificateEntry(alias))
			{
				System.out.println("Certificate entry");
			}
		}
		fis.close();
	}

	// EXTRACT PUBLIC KEY FROM KeyStore
	public static PublicKey getPublicKey(String keystoreFileName, String keystorePassword, String keyAlias) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException
	{
		FileInputStream fis = new FileInputStream(new File(keystoreFileName));
		KeyStore keyStore = KeyStore.getInstance("jks");
		keyStore.load(fis, keystorePassword.toCharArray());
		PublicKey publicKey = keyStore.getCertificate(keyAlias).getPublicKey();

		return publicKey;
	}

	// EXTRACT PUBLIC KEY FROM KeyStore
	public static PrivateKey getPrivateKey(String keystoreFileName, String keystorePassword, String keyAlias, String keyPassword) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException
	{
		FileInputStream fis = new FileInputStream(new File(keystoreFileName));
		KeyStore keyStore = KeyStore.getInstance("jks");
		keyStore.load(fis, keystorePassword.toCharArray());
		PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, keyPassword.toCharArray());

		return privateKey;
	}

	// EXTRACT PUBLIC KEY FROM Certificate
	public static PublicKey getCertificateKey(String certificateFileName) throws CertificateException, IOException
	{
		FileInputStream fis = new FileInputStream(new File(certificateFileName));

		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		X509Certificate certificate = (X509Certificate)certificateFactory.generateCertificate(fis);

		fis.close();

		return certificate.getPublicKey();
	}

	// GENERATE DIGITAL SIGNATURE FOR FILE
	public static byte[] getDigitalSignature(String inputFileName, PrivateKey privateKey) throws IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
	{
		byte[] content = Files.readAllBytes(Paths.get(inputFileName));

		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(privateKey);
		signature.update(content);

		return signature.sign();
	}

	// CHECK THE SIGNATURE
	public static boolean isSignatureValid(String inputFileName, String certificateFileName, byte[] digitalSignature) throws CertificateException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException
	{
		byte[] content = Files.readAllBytes(Paths.get(inputFileName));
		PublicKey publicKey = getCertificateKey(certificateFileName);
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initVerify(publicKey);
		signature.update(content);

		return signature.verify(digitalSignature);
	}

	// RSA ENCRYPT BYTE ARRAY
	public static byte[] encryptRSA(byte[] input, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
	{
		Cipher cipher = Cipher.getInstance("RSA/NONE/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		return cipher.doFinal(input);
	}

	// RSA DECRYPT BYTE ARRAY
	public static byte[] decryptRSA(byte[] input, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
	{
		Cipher cipher = Cipher.getInstance("RSA/NONE/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key);

		return cipher.doFinal(input);
	}
}

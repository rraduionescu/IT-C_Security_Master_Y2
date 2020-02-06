package ro.ionescu.radu.sap.exam;

import com.sun.org.apache.bcel.internal.classfile.Utility;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

// January 2019
// Secure Applications Programming Exam

public class IonescuRaduStefan
{
	// PROVIDED - Method for getting the public key from a X509 certificate file
	public static PublicKey getCertificateKey(String file) throws FileNotFoundException, CertificateException
	{
		FileInputStream fis = new FileInputStream(file);
		CertificateFactory factory = CertificateFactory.getInstance("X509");
		X509Certificate certificate = (X509Certificate)factory.generateCertificate(fis);

		return certificate.getPublicKey();
	}

	// PROVIDED - Method for checking a signature
	public static boolean isVeryfied(String fileName, String signatureFileName, PublicKey key)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException
	{
		RandomAccessFile f = new RandomAccessFile(fileName, "r");
		byte[] content = new byte[(int)f.length()];
		f.readFully(content);
		f.close();

		RandomAccessFile dsFile = new RandomAccessFile(signatureFileName, "r");
		byte[] ds = new byte[(int)dsFile.length()];
		dsFile.readFully(ds);
		dsFile.close();

		Signature signature = Signature.getInstance("NONEwithRSA");
		signature.initVerify(key);
		signature.update(content);

		return signature.verify(ds);
	}

	// IMPLEMENT - 1. Method for generating the SHA-1 hash value
	public static byte[] getSHA1Hash(String inputFileName)
			throws NoSuchAlgorithmException, NoSuchProviderException, IOException
	{
		RandomAccessFile randomAccessFile = new RandomAccessFile(inputFileName, "r");
		byte[] content = new byte[(int)randomAccessFile.length()];
		randomAccessFile.readFully(content);
		randomAccessFile.close();

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		return messageDigest.digest(content);
	}

	// IMPLEMENT - 2. Method for decrypting the file using AES in CBC mode with no padding (didn't need it)
	public static void decryptAESCBC(
			String inputFileName,
			String outputFileName,
			byte[] key,
			byte[] IV)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, ShortBufferException, BadPaddingException,
			IOException, InvalidKeySpecException
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

		SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
		SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("AES");
		SecretKey secretKey = secretKeyFactory.generateSecret(secretKeySpec);

		IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

		byte[] inputBlock = new byte[cipher.getBlockSize()];
		byte[] outputBlock;
		int inputNumberOfBytes;
		int outputNumberOfBytes;

		while((inputNumberOfBytes = fileInputStream.read(inputBlock)) > 0)
		{
			outputNumberOfBytes = cipher.getOutputSize(inputNumberOfBytes);
			outputBlock = new byte[outputNumberOfBytes];
			outputNumberOfBytes = cipher.update(inputBlock, 0, inputNumberOfBytes, outputBlock);
			fileOutputStream.write(outputBlock, 0, outputNumberOfBytes);
		}

		cipher.doFinal();

		fileInputStream.close();
		fileOutputStream.close();
	}

	// IMPLEMENT - 3.a Method for getting the private key from a keystore
	public static PrivateKey getPrivateKey(
			String keyStoreFileName,
			String keyStorePass,
			String keyAlias,
			String keyPass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException
	{
		File keystoreFile = new File(keyStoreFileName);
		if(!keystoreFile.exists())
		{
			throw new FileNotFoundException();
		}
		FileInputStream fileInputStream = new FileInputStream(keystoreFile);

		KeyStore keyStore = KeyStore.getInstance("jks");
		keyStore.load(fileInputStream, keyStorePass.toCharArray());
		return (PrivateKey)keyStore.getKey(keyAlias, keyPass.toCharArray());
	}

	// IMPLEMENT - 3.b Method for computing the RSA digital signature and storing in file
	public static void generateDigitalSignature(
			String inputFileName,
			String signatureFileName,
			PrivateKey key)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException
	{
		RandomAccessFile randomAccessFile = new RandomAccessFile(inputFileName, "r");
		byte[] content = new byte[(int)randomAccessFile.length()];
		randomAccessFile.readFully(content);
		randomAccessFile.close();

		File outputFile = new File(signatureFileName);
		if(!outputFile.exists())
		{
			outputFile.createNewFile();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

		Signature signature = Signature.getInstance("RSA");
		signature.initSign(key);
		signature.update(content);
		byte[] digitalSignature = signature.sign();
		fileOutputStream.write(digitalSignature);
	}

	public static void main(String[] args)
	{
		Security.addProvider(new BouncyCastleProvider());
		try
		{
			// AES CBC - Decryption key
			byte AES_KEY[] = {
					0x2A, 0x4D, 0x61, 0x73, 0x74, 0x65, 0x72, 0x20,
					0x49, 0x53, 0x4D, 0x20, 0x32, 0x30, 0x31, 0x37
			};

			// AES CBC - IV content
			byte IV[];

			// Initialize the IV byte array with 1 for each byte
			IV = new byte[AES_KEY.length];
			for(int i=0 ; i < AES_KEY.length ; i++)
			{
				IV[i] = 0x01;
			}

			// 1. Compute the hash value and print it in Hexadecimal
			byte[] hashValue = getSHA1Hash("EncryptedMessage.cipher");
			if(hashValue != null)
			{
				System.out.println("SHA-1 Hash : " + Utility.toHexString(hashValue).toUpperCase());
			}

			// 2. Call a function to decrypt the input file EncryptedMessage.cipher
			decryptAESCBC("EncryptedMessage.cipher", "OriginalMessage.txt", AES_KEY, IV);

			// 3. compute the RSA digital signature for the EncryptedMessage.cipher file
			String keyStorePassword = "Secret1234ABCDEF";
			PrivateKey privateKey = getPrivateKey("examkeystore.ks", keyStorePassword, "ismexam1", "passkey1");

			generateDigitalSignature("OriginalMessage.txt", "FileSignature.ds", privateKey);

			// PROVIDED - Signature verification
			PublicKey pubKey = getCertificateKey("ISMExamCert.cer");

			if(isVeryfied("OriginalMessage.txt", "FileSignature.ds", pubKey))
			{
				System.out.println("The file is ok");
			}
			else
			{
				System.out.println("We have a security breach");
			}

			System.out.println("Done");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
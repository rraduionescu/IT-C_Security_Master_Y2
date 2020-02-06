package  ro.ionescu.radu;

import com.sun.org.apache.bcel.internal.classfile.Utility;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

//January 2019
//Secure Applications Programming Exam


//change the class name with your name
public class MainWinter2019
{
	// Provided method for getting the public key from a X509 certificate file
	//		DON'T CHANGE IT      //
	public static PublicKey getCertificateKey(String file) throws FileNotFoundException, CertificateException
	{
		FileInputStream fis = new FileInputStream(file);
		CertificateFactory factory = CertificateFactory.getInstance("X509");
		X509Certificate certificate = (X509Certificate) factory.generateCertificate(fis);

		return certificate.getPublicKey();
	}

	// Provided method for checking a signature
	//		DON'T CHANGE IT     //
	public static boolean isVeryfied(String fileName, String signatureFileName, PublicKey key)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException
	{
		RandomAccessFile f = new RandomAccessFile(fileName, "r");
		byte[] content = new byte[(int) f.length()];
		f.readFully(content);
		f.close();

		RandomAccessFile dsFile = new RandomAccessFile(signatureFileName, "r");
		byte[] ds = new byte[(int) dsFile.length()];
		dsFile.readFully(ds);
		dsFile.close();

		Signature signature = Signature.getInstance("NONEwithRSA");
		signature.initVerify(key);
		signature.update(content);

		return signature.verify(ds);

	}

	// Method for getting the private key from a keystore
	//		IMPLEMENT IT        //
	public static PrivateKey getPrivateKey(
			String keyStoreFileName,
			String keyStorePass,
			String keyAlias,
			String keyPass)
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException
	{
		FileInputStream keyStoreInputStream = new FileInputStream(keyStoreFileName);
		KeyStore keyStore = KeyStore.getInstance("jks");
		keyStore.load(keyStoreInputStream, keyStorePass.toCharArray());

		return (PrivateKey)keyStore.getKey(keyAlias, keyPass.toCharArray());
	}


	// Method for computing the RSA digital signature
	//		IMPLEMENT IT        //
	public static void generateDigitalSignature(
			String inputFileName,
			String signatureFileName,
			PrivateKey key)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException
	{
		// Generate and store the RSA digital signature of the inputFileName file in the signatureFileName file
		RandomAccessFile inputFile = new RandomAccessFile(inputFileName, "r");
		byte[] input = new byte[(int)inputFile.length()];
		inputFile.readFully(input);
		Signature signature = Signature.getInstance("NONEwithRSA");
		signature.initSign(key);
		signature.update(input);
		byte[] output = signature.sign();
		RandomAccessFile outputFile = new RandomAccessFile(signatureFileName, "rw");
		outputFile.write(output);
	}


	// Proposed function for generating the hash value
	//		IMPLEMENT IT         //
	public static byte[] getSHA1Hash(String fileName)
			throws NoSuchAlgorithmException, NoSuchProviderException, IOException
	{
		// Generate the SHA-1 value of the received file
		RandomAccessFile inputFile = new RandomAccessFile(fileName, "r");
		byte[] input = new byte[(int)inputFile.length()];
		inputFile.readFully(input);

		MessageDigest SHA1Hash = MessageDigest.getInstance("SHA-1");
		SHA1Hash.update(input);
		byte[] hash = SHA1Hash.digest();

		return hash;
	}

	// Proposed function for decryption
	//		IMPLEMENT IT        //
	public static void decryptAESCBC(
			String inputFile,
			String outputFile,
			byte[] key,
			byte[] initialIV)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, ShortBufferException, BadPaddingException,
			IOException
	{
		// Decrypt the input file using AES in CBC
		// The file was encrypted without using padding - didn't need it
		RandomAccessFile inputRFile = new RandomAccessFile(inputFile, "r");
		byte[] input = new byte[(int)inputRFile.length()];
		inputRFile.readFully(input);

		SecretKeySpec AESKey = new SecretKeySpec(key, "AES");
		IvParameterSpec IV = new IvParameterSpec(initialIV);
		Cipher AESCipher = Cipher.getInstance("AES/CBC/NoPadding");
		AESCipher.init(Cipher.DECRYPT_MODE, AESKey, IV);
		byte[] output = AESCipher.doFinal(input);

		RandomAccessFile outputRFile = new RandomAccessFile(outputFile, "rw");
		outputRFile.write(output);
	}



	public static void main(String[] args)
	{
		try
		{
			// AES CBC - Decryption key
			byte AES_KEY[] = { 	0x2A, 0x4D, 0x61, 0x73,
								0x74, 0x65, 0x72, 0x20,
								0x49, 0x53, 0x4D, 0x20,
								0x32, 0x30, 0x31, 0x37
						   	 };
			// AES CBC - IV content
			byte IV[] = null;

			// Init the IV byte array with 1 for each byte
			IV = new byte[16];
			for(int i=0 ; i<16 ; i++)
			{
				IV[i] = 0x01;
			}

			// 1. compute the hash value and print it in Hexadecimal
			byte[] hashValue = getSHA1Hash("EncryptedMessage.cipher");
			if(hashValue != null)
			{
				System.out.print("SHA-1 hash: ");
				System.out.println(Utility.toHexString(hashValue).toUpperCase());
			}

			// 2. Call a function to decrypt the input file EncryptedMessage.cipher
			//    There is no need for padding
			//    You should get OriginalMessage.txt file
			decryptAESCBC("EncryptedMessage.cipher", "OriginalMessage.txt", AES_KEY, IV);

			// 3. Compute the RSA digital signature for the EncryptedMessage.cipher file
			//    store it in the signature.ds file
			String keyStorePassword = "Secret1234ABCDEF";	//is in the OriginalMessage.txt file
			PrivateKey privKey = getPrivateKey("examkeystore.ks", keyStorePassword, "ismexam1", "passkey1");

			generateDigitalSignature("OriginalMessage.txt", "FileSignature.ds", privKey);


			// Provided code for checking the signature
			// DONT CHANGE //
			PublicKey pubKey = getCertificateKey("ISMExamCert.cer");

			if (isVeryfied("OriginalMessage.txt", "FileSignature.ds", pubKey))
				System.out.println("The file is ok");
			else
				System.out.println("We have a security breach");

			System.out.println("Done");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
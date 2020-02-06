package ro.ionescu.radu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.xml.bind.DatatypeConverter;

public class Main
{

	// provided method for getting the public key from a X509 certificate file
	public static PublicKey getCertificateKey(String file) throws FileNotFoundException, CertificateException
	{
		FileInputStream fis = new FileInputStream(file);

		CertificateFactory factory = CertificateFactory.getInstance("X509");

		X509Certificate certificate = (X509Certificate)factory.generateCertificate(fis);

		return certificate.getPublicKey();
	}

	// provided method for checking a signature
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

	// method for getting the private key from a keystore
	public static PrivateKey getPrivateKey(
			String keyStoreFileName,
			String keyStorePass,
			String keyAlias,
			String keyPass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
			UnrecoverableKeyException
	{

		//TO DO

		File inputFile = new File(keyStoreFileName);
		if(!inputFile.exists())
		{
			throw new FileNotFoundException();
		}
		FileInputStream fileInputStream = new FileInputStream(inputFile);

		KeyStore keyStore = KeyStore.getInstance("jks");
		keyStore.load(fileInputStream, keyStorePass.toCharArray());

		return (PrivateKey)keyStore.getKey(keyAlias, keyPass.toCharArray());
	}

	// method for computing the RSA digital signature
	public static void getDigitalSignature(
			String inputFileName,
			String signatureFileName,
			PrivateKey key)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException
	{

		//TO DO
		//generate and store the RSA digital signature of the inputFileName file
		//store it in signatureFileName file

		RandomAccessFile f = new RandomAccessFile(inputFileName, "r");
		byte[] content = new byte[(int)f.length()];
		f.readFully(content);
		f.close();

		File outputFile = new File(signatureFileName);
		if(!outputFile.exists())
		{
			outputFile.createNewFile();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);

		Signature signature = Signature.getInstance("NONEwithRSA");
		signature.initSign(key);
		signature.update(content);

		fileOutputStream.write(signature.sign());
		fileOutputStream.close();
	}

	public static byte[] getSHA1Hash(File file)
			throws NoSuchAlgorithmException, NoSuchProviderException, IOException
	{
		return null;
	}

	//proposed function for decryption
	public static void decryptAESCBC(
			File inputFile,
			File outputFile,
			byte[] key)
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, ShortBufferException, BadPaddingException,
			IOException
	{

		//TO DO
		//decrypt the input file using AES in CBC
		//the file was encrypted without using padding - didn't need it
		//the IV is at the beginning of the input file

	}

	public static void main(String[] args)
	{
		try
		{


			/*
			 *
			 * @author - Please write your name here and also rename the class
			 *  :^)  :^)  :^)
			 *  :^)  :^)  :^)
			 *  :^)  :^)  :^)
			 */
			/*
			 * Request 1
			 */

			File passFile = new File("Secrets.txt");
			byte[] hashValue = getSHA1Hash(passFile);
			System.out.println("SHA1: " + DatatypeConverter.printHexBinary(hashValue));

			//check point - you should get 268F10........


			/*
			 * Request 2
			 */

			//generate the key form previous hash
			byte[] key = Arrays.copyOfRange(hashValue, 0, 16);

			//decrypt the input file
			//there is no need for padding and the IV is written at the beginning of the file
			decryptAESCBC(new File("EncryptedData.data"), new File("OriginalData.txt"), key);

			//get the keyStorePassword from OriginalMessage.txt
			String ksPassword = "you_already_made_it";
			String keyName = "sapexamkey";
			String keyPassword = "grant_access";

			//compute the RSA digital signature for the EncryptedMessage.cipher file and store it in the
			//	signature.ds file

			PrivateKey privKey = getPrivateKey("sap_exam_keystore.ks", ksPassword, keyName, keyPassword);
			getDigitalSignature("OriginalData.txt", "DataSignature.ds", privKey);

			//don't modify the next section - used to check the digital signature
			//you can comment it
			PublicKey pubKey = getCertificateKey("SAPExamCertificate.cer");
			if(isVeryfied("OriginalData.txt", "DataSignature.ds", pubKey))
			{
				System.out.println("The file was not changed. You are ok");
			}
			else
			{
				System.out.println("We have a security breach");
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

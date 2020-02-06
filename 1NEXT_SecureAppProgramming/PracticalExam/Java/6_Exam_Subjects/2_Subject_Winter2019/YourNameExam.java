
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.plaf.synth.SynthSeparatorUI;
import javax.xml.bind.DatatypeConverter;

//January 2019
//Secure Applications Programming Exam


//change the class name with your name
public class YourNameExam {

	// provided method for getting the public key from a X509 certificate file
	/*
	*		DON'T CHANGE IT
	*/
	public static PublicKey getCertificateKey(String file) throws FileNotFoundException, CertificateException {
		FileInputStream fis = new FileInputStream(file);

		CertificateFactory factory = CertificateFactory.getInstance("X509");

		X509Certificate certificate = (X509Certificate) factory.generateCertificate(fis);

		return certificate.getPublicKey();
	}
	
	// provided method for checking a signature
	/*
	*		DON'T CHANGE IT
	*/
	public static boolean isVeryfied(String fileName, String signatureFileName, PublicKey key)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {

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
	
	

	// method for getting the private key from a keystore
	/*
	*		Implement it
	*/
	public static PrivateKey getPrivateKey(
			String keyStoreFileName, 
			String keyStorePass, 
			String keyAlias,
			String keyPass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
					UnrecoverableKeyException {

		return null;
	}

	
	// method for computing the RSA digital signature
	/*
	*		Implement it
	*/
	public static void generateDigitalSignature(
			String inputFileName, 
			String signatureFileName, 
			PrivateKey key)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		
		//generate and store the RSA digital signature of the inputFileName file
		//store it in signatureFileName file

	}


	//proposed function for generating the hash value
	/*
	*		Implement it
	*/
	public static byte[] getSHA1Hash(String fileName)
			throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
		
		//generate the SHA-1 value of the received file

		return null;
	}

	//proposed function for decryption
	/*
	*		Implement it
	*/
	public static void decryptAESCBC(
			String inputFile, 
			String outputFile, 
			byte[] key, 
			byte[] initialIV)
					throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, ShortBufferException, BadPaddingException,
			IOException {

		//decrypt the input file using AES in CBC
		//the file was encrypted without using padding - didn't need it

	}

	

	public static void main(String[] args) {
		try {

			// AES CBC - decryption key
			byte AES_KEY[] = { 0x2A, 0x4D, 0x61, 0x73, 0x74, 0x65, 0x72, 0x20, 0x49, 0x53, 0x4D, 
					0x20, 0x32, 0x30, 0x31, 0x37 };
			// AES CBC - IV content
			byte IV[] = null;
			
			//init the IV byte array with 1 for each byte
			//...

			// 1. compute the hash value and print it in Hexadecimal
			byte[] hashValue = getSHA1Hash("EncryptedMessage.cipher");
			if(hashValue!=null){
				System.out.println("SHA-1 hash:");
				DatatypeConverter.printHexBinary(hashValue);
			}

			// 2. call a function to decrypt the input file EncryptedMessage.cipher
			// there is no need for padding
			// you should get OriginalMessage.txt file

			// call decryptAESCBC(...)
			

			// 3. compute the RSA digital signature for the EncryptedMessage.cipher file
			//  store it in the signature.ds file

			String keyStorePassword = "";	//is in the OriginalMessage.txt file
			PrivateKey privKey = getPrivateKey("examkeystore.ks", keyStorePassword, "ismexam1", "passkey1");

			generateDigitalSignature("OriginalMessage.txt", "FileSignature.ds", privKey);

			
			// provided code for checking the signature
			//don't change it
			PublicKey pubKey = getCertificateKey("ISMExamCert.cer");

			if (isVeryfied("OriginalMessage.txt", "FileSignature.ds", pubKey))
				System.out.println("The file is ok");
			else
				System.out.println("We have a security breach");

			System.out.println("Done");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package asdf;

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

public class YourNameExam {

	// provided method for getting the public key from a X509 certificate file
	public static PublicKey getCertificateKey(String file) throws FileNotFoundException, CertificateException {
		FileInputStream fis = new FileInputStream(file);

		CertificateFactory factory = CertificateFactory.getInstance("X509");

		X509Certificate certificate = (X509Certificate) factory.generateCertificate(fis);

		return certificate.getPublicKey();
	}
	
	// provided method for checking a signature
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
	public static PrivateKey getPrivateKey(
			String keyStoreFileName, 
			String keyStorePass, 
			String keyAlias,
			String keyPass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
					UnrecoverableKeyException {
						
			//TO DO
		File file = new File(keyStoreFileName);
		
		KeyStore store = 
				KeyStore.getInstance("jceks");
		
		store.load(new FileInputStream(file),
				keyStorePass.toCharArray());
		
		return (PrivateKey) store.getKey(keyAlias, 
				keyPass.toCharArray());
		
	}

	
	// method for computing the RSA digital signature
	public static void getDigitalSignature(
			String inputFileName, 
			String signatureFileName, 
			PrivateKey key)
			throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
		
		//TO DO
		//generate and store the RSA digital signature of the inputFileName file
		//store it in signatureFileName file
		
		File input = 
				new File(inputFileName);
		
		File output = new File(signatureFileName);
		if(!output.exists())
			output.createNewFile();
		
		FileInputStream fis = 
				new FileInputStream(input);
		FileOutputStream fos = 
				new FileOutputStream(output);
		
		Signature ds = 
				Signature.getInstance("NONEwithRSA");	
		ds.initSign(key);	
		
		byte [] inputBuffer = new byte[1];
		int noBytes =0;
		while((noBytes=fis.read(inputBuffer))!=-1){
			ds.update(inputBuffer);
		}
		
		fos.write(ds.sign());
	}


	public static byte[] getSHA1Hash(File file)
			throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
		
	byte[] hashValue = null;
		
		FileInputStream fis = 
				new FileInputStream(file);
		
		byte[] inputBuffer;
		
		MessageDigest md = 
				MessageDigest.getInstance(
						"SHA-1");
		
		
		inputBuffer = new byte[1];
		int noBytes = 0;
		
		while((noBytes=fis.read(inputBuffer))!=-1){
			md.update(inputBuffer,0,noBytes);
		}
		
		hashValue = md.digest();
		
	
		return hashValue;
	}

	//proposed function for decryption
	public static void decryptAESCBC(
			File inputFile, 
			File outputFile, 
			byte[] key)
					throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, ShortBufferException, BadPaddingException,
			IOException {

		//TO DO
		//decrypt the input file using AES in CBC
		//the file was encrypted without using padding - didn't need it
		//the IV is at the beginning of the input file

		
		
		if(!outputFile.exists())
			outputFile.createNewFile();
		
		FileInputStream fis = 
				new FileInputStream(inputFile);
		FileOutputStream fos = 
				new FileOutputStream(outputFile);
		
		//create the cipher object
		String cipherProp = 
				"AES/CBC/NoPadding";
		Cipher cipher = 
				Cipher.getInstance(
						cipherProp);
		
		SecretKey secretKey = new SecretKeySpec(key, 0, key.length, "AES");
		
		//retrieve the IV value from the file
		byte[] IV = 
				new byte[key.length];
		fis.read(IV);
		
		IvParameterSpec IVParam = 
				new IvParameterSpec(IV);
		
		//init the cipher
		cipher.init(Cipher.DECRYPT_MODE, 
				secretKey,IVParam);
	
		
		byte[] buffer = 
				new byte[cipher.getBlockSize()];
		int noBytes;
		noBytes = fis.read(buffer);
		while(noBytes!=-1){
			byte[] cipherBlock = 
					new byte[cipher.getOutputSize(noBytes)];
			int exitBytes = 
					cipher.update(buffer, 0, noBytes, 
							cipherBlock, 0);
			fos.write(cipherBlock,0,exitBytes);
			noBytes = fis.read(buffer);
		}
		//processing the last block
		byte[] lastCipherBlock = 
				new byte[cipher.getBlockSize()];
		int lastBlockSize = cipher.doFinal(
				lastCipherBlock, 0);
		fos.write(lastCipherBlock,0,lastBlockSize);
		
		fis.close();
		fos.close();
		
	
	}

	

	public static void main(String[] args) {
		try {

			
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
			System.out.println("SHA1: "+DatatypeConverter.printHexBinary(hashValue));
			
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
			
			PrivateKey privKey = getPrivateKey("sap_exam_keystore.ks",ksPassword,keyName,keyPassword);
			getDigitalSignature("OriginalData.txt", "DataSignature.ds", privKey);
			
			
			
			//don't modify the next section - used to check the digital signature
			//you can comment it
			PublicKey pubKey = getCertificateKey("SAPExamCertificate.cer");
			if(isVeryfied("OriginalData.txt", "DataSignature.ds", pubKey))
				System.out.println("The file was not changed. You are ok");
			else
				System.out.println("We have a security breach");
				
				

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

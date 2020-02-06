package ro.ase.ism;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.plaf.FileChooserUI;

public class CypherFunctions {
	public static void encryptECB(
			File input,
			File output,
			String algorithm,
			String transformation,
			String provider,
			String key) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IOException, ShortBufferException, IllegalBlockSizeException, BadPaddingException{
		
		FileInputStream fis = 
				new FileInputStream(input);
		FileOutputStream fos = 
				new FileOutputStream(output);
		
		//define the cipher
		Cipher cipher = 
				Cipher.getInstance(
						transformation,provider);
		
		//define the key
		SecretKey keyValue = 
				new SecretKeySpec(
						key.getBytes(),
						algorithm);
		//init the cipher
		cipher.init(Cipher.ENCRYPT_MODE,
				keyValue);
		
		//read input file and encrypt
		byte[] buffer = 
				new byte[cipher.getBlockSize()];	
		int noBytesRead = 0;
		byte[] cipherText = null;
		
		while((noBytesRead=fis.read(buffer))!=-1){
			cipherText = 
					new byte[cipher.getOutputSize(noBytesRead)];
			int outputSize = 
					cipher.update(buffer,0,noBytesRead,
							cipherText,0);
			
			//write the ciphertext into the file
			fos.write(cipherText, 0, outputSize);
		}
		
		//get the last block
		int outputSize = cipher.doFinal(
				cipherText, 0);
		fos.write(cipherText,0,outputSize);
		
		fos.close();
		fis.close();
		
	}
	
	public static void decryptECB(
			File input,
			File output,
			String algorithm,
			String transformation,
			String provider,
			String key) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IOException, ShortBufferException, IllegalBlockSizeException, BadPaddingException{
		
		FileInputStream fis = 
				new FileInputStream(input);
		FileOutputStream fos = 
				new FileOutputStream(output);
		
		//define the cipher
		Cipher cipher = 
				Cipher.getInstance(
						transformation,provider);
		
		//define the key
		SecretKey keyValue = 
				new SecretKeySpec(
						key.getBytes(),
						algorithm);
		//init the cipher
		cipher.init(Cipher.DECRYPT_MODE,
				keyValue);
		
		//read input file and encrypt
		byte[] buffer = 
				new byte[cipher.getBlockSize()];	
		int noBytesRead = 0;
		byte[] cipherText = null;
		
		while((noBytesRead=fis.read(buffer))!=-1){
			cipherText = 
					new byte[cipher.getOutputSize(noBytesRead)];
			int outputSize = 
					cipher.update(buffer,0,noBytesRead,
							cipherText,0);
			
			//write the ciphertext into the file
			fos.write(cipherText, 0, outputSize);
		}
		
		//get the last block
		int outputSize = cipher.doFinal(
				cipherText, 0);
		fos.write(cipherText,0,outputSize);
		
		fos.close();
		fis.close();
		
	}
	
	public static void encryptCBC(
			File input,
			File output,
			String algorithm,
			String transformation,
			String provider,
			String key,
 byte[] IV) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
					InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
					ShortBufferException, BadPaddingException, IOException{
		
		//open the files
		FileInputStream fis = 
				new FileInputStream(input);
		FileOutputStream fos = 
				new FileOutputStream(output);
		
		//create the Cipher
		Cipher cipher = 
				Cipher.getInstance(transformation, 
						provider);
		
		//the key
		SecretKey keyValue = 
				new SecretKeySpec(key.getBytes(),
						algorithm);
		
		//init the cipher		
		IvParameterSpec iv = 
				new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE,
				keyValue,iv);
		
		//write IV for decryption
		fos.write(IV,0,IV.length);
		
		//read input file and encrypt
				byte[] buffer = 
						new byte[cipher.getBlockSize()];	
				int noBytesRead = 0;
				byte[] cipherText = null;
				
				while((noBytesRead=fis.read(buffer))!=-1){
					cipherText = 
							new byte[cipher.getOutputSize(noBytesRead)];
					int outputSize = 
							cipher.update(buffer,0,noBytesRead,
									cipherText,0);
					
					//write the ciphertext into the file
					fos.write(cipherText, 0, outputSize);
				}
				
				//get the last block
				int outputSize = cipher.doFinal(
						cipherText, 0);
				fos.write(cipherText,0,outputSize);
				
				fos.close();
				fis.close();
		
	}
	
	public static void decryptCBC(
			File input,
			File output,
			String algorithm,
			String transformation,
			String provider,
			String key) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
					InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException,
					ShortBufferException, BadPaddingException, IOException{
		
		//open the files
		FileInputStream fis = 
				new FileInputStream(input);
		FileOutputStream fos = 
				new FileOutputStream(output);
		
		//create the Cipher
		Cipher cipher = 
				Cipher.getInstance(transformation, 
						provider);
		
		//the key
		SecretKey keyValue = 
				new SecretKeySpec(key.getBytes(),
						algorithm);
		
		byte[] initialIV = 
				new byte[key.getBytes().length];
		//read the IV from the file
		fis.read(initialIV);
		
		
		//init the cipher		
		IvParameterSpec iv = 
				new IvParameterSpec(initialIV);
		cipher.init(Cipher.DECRYPT_MODE,
				keyValue,iv);
		
		//read input file and encrypt
				byte[] buffer = 
						new byte[cipher.getBlockSize()];	
				int noBytesRead = 0;
				byte[] cipherText = null;
				
				while((noBytesRead=fis.read(buffer))!=-1){
					cipherText = 
							new byte[cipher.getOutputSize(noBytesRead)];
					int outputSize = 
							cipher.update(buffer,0,noBytesRead,
									cipherText,0);
					
					//write the ciphertext into the file
					fos.write(cipherText, 0, outputSize);
				}
				
				//get the last block
				int outputSize = cipher.doFinal(
						cipherText, 0);
				fos.write(cipherText,0,outputSize);
				
				fos.close();
				fis.close();
		
	}
}

package ro.ase.ism;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HashFunction {
	public static byte[] getFileHash(
			File file,
			String functionName,
			String provider) 
					throws NoSuchAlgorithmException, NoSuchProviderException, IOException{
		byte[] result = null;
		
		//open the file
		FileInputStream fis = 
				new FileInputStream(file);
		
		//byte block used for processing
		byte[] buffer;
		
		//Hash object
		MessageDigest md = 
				MessageDigest.getInstance(
						functionName,provider);
		
		buffer = new byte[1];
		int noBytesRead = 0;
		
		//reading the file content
		while((noBytesRead = fis.read(buffer))!=-1){
			md.update(buffer);
		}
		
		result = md.digest();
		
		fis.close();
		
		return result;
	}
	
	
	public static byte[] getFileHashMAC(
			File file, String algorithm,
			String provider, String key) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, InvalidKeyException{
		
		byte[] result = null;
		
		//the file stream
		FileInputStream fis = 
				new FileInputStream(file);
		
		//the hashMAC object
		Mac mac = Mac.getInstance(
				algorithm,provider);
		
		//generate the key
		byte[] keyBytes = key.getBytes();
		Key hMacKey = 
				new SecretKeySpec(
						keyBytes, algorithm);
		
		mac.init(hMacKey);
		
		//read the file content
		byte[] buffer = new byte[1];
		while(fis.read(buffer)!=-1){
			mac.update(buffer);
		}
		
		result = mac.doFinal();
		
		fis.close();
		
		return result;
	}
	
	
}

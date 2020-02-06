package ro.ase.ism.sap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Hash {
	//metode pentru calcul valoare hash
	public static byte[] getHash(String fileName, String provider) 
			throws NoSuchAlgorithmException, 
			NoSuchProviderException, 
			IOException{
		
		byte[] hash = null;
		
		File fisier = new File(fileName);
		if(fisier.exists()){
			
			FileInputStream fis = 
					new FileInputStream(fisier);
			
			MessageDigest md = 
					MessageDigest.getInstance(
							"SHA1", provider);
			
			//definire bloc procesat
			byte[] buffer = new byte[8];
			
			int noReadBytes = 0;
			while((noReadBytes=fis.read(buffer))!=-1){
				md.update(buffer,0,noReadBytes);
			}
			hash = md.digest();
			
			fis.close();
		}
		
		return hash;
	}
}

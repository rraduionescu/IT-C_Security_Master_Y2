package nume.prenume;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public class HashFunction {
	public static byte[] getFileHash(
			File file,
			String functionName) 
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
						functionName);
		
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
	
	
	
}

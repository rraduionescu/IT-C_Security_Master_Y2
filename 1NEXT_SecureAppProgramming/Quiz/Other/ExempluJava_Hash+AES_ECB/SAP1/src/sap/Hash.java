package sap;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class Hash {
	public static byte[] genereazaHash(String filename, String provider) throws Exception{
		byte[] hash = null;
		File f = new File(filename);
		if (f.exists()){
			FileInputStream fis = new FileInputStream(f);
			MessageDigest md = MessageDigest.getInstance("MD5",provider);
			byte[] buffer = new byte[16];
			int noReadBytes = 0;
			while ((noReadBytes=fis.read(buffer))!=-1){
				md.update(buffer, 0, noReadBytes);
			}
			hash = md.digest();
			fis.close();
		}
		return hash;
	}
}

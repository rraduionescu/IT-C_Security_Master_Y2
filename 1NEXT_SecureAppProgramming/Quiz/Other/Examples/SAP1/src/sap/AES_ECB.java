package sap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES_ECB {
	//criptare
	public static void encrypt(String in, String out, byte[] key, String provider, int mode) throws Exception{
		File inF = new File(in);
		File outF = new File(out);
		
		if (!inF.exists()) {
			throw new FileNotFoundException();
		} else {
			FileInputStream fis = new FileInputStream(inF);
			FileOutputStream fos = new FileOutputStream(outF);
			//initializare cifru
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", provider);
			//definire cheie + initializare cifru
			SecretKey cheie = new SecretKeySpec(key, "AES");
			cipher.init(mode, cheie);
			//citire blocuri + criptare
			byte[] buffer = new byte[cipher.getBlockSize()];
			int noReadBytes = 0;
			byte[] encryptedBuffer = null;
			
			while((noReadBytes=fis.read(buffer))!=-1){
				encryptedBuffer = new byte[cipher.getOutputSize(noReadBytes)];
				int noBytes = cipher.update(buffer, 0, noReadBytes, encryptedBuffer,0);
				fos.write(encryptedBuffer,0,noBytes);
			}
			//ultimul pas pentru ultimul bloc
			int noBytes = cipher.doFinal(encryptedBuffer, 0);
			fos.write(encryptedBuffer, 0, noBytes);
			fis.close();
			fos.close();
			
		}
	}
}

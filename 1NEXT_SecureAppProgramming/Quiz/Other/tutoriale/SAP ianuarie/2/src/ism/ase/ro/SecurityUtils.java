package ism.ase.ro;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.xml.bind.DatatypeConverter;

public class SecurityUtils {
	
	public static String gethash(String inFile) throws NoSuchAlgorithmException, IOException{
		
		FileInputStream fis = new FileInputStream(inFile);
	
	//	BufferedInputStream bis = new BufferedInputStream(fis);
		
		MessageDigest md = MessageDigest.getInstance("MD5"); //change for hash
		
		byte[] buffer = new byte[8];
		int count=0;
		while((count=fis.read(buffer)) != -1)
		{
			md.update(buffer, 0, count);
		}
		byte[] hash = md.digest();			
		fis.close();
		return new String(hash);				
	}
	
	public static PrivateKey getpriv(String keystore,char[] storepass,String keyAlias,char[] keyPass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		
		FileInputStream fis = new FileInputStream(keystore);
		
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(fis, storepass);
		
		PrivateKey privKey = (PrivateKey) ks.getKey(keyAlias, keyPass);
		fis.close();
		return privKey;		
	}
	
	
	
	
}

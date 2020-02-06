package ism.ase.ro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
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
	
	public static void RSA_Decrypt(String inFile,String outFile) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnrecoverableKeyException, KeyStoreException, CertificateException, IOException, IllegalBlockSizeException, BadPaddingException{
		
		File fisier = new File(inFile);
		FileInputStream fis = new FileInputStream(fisier);
		FileOutputStream fos = new FileOutputStream(outFile);
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, getpriv("sapkeystore.ks", "passks".toCharArray(), "sapkey1", "sapex2016".toCharArray()));
		
		byte[] buffer = new byte[(int) fisier.length()];
		fis.read(buffer, 0, buffer.length);
		byte[] decrypted = cipher.doFinal(buffer);
		
		System.out.println(new String(decrypted));
		fos.write(decrypted);
		
		fis.close();
		fos.close();		
	}
	
	public static void AES_ECB_decrypt(String inFile,String outFile) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, ShortBufferException, IllegalBlockSizeException, BadPaddingException
	{
		String key = "passwordpassword";
		FileInputStream fis = new FileInputStream(inFile);
		FileOutputStream fos = new FileOutputStream(outFile);
		
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		SecretKey aesKey = new SecretKeySpec(key.getBytes(),"AES");
		cipher.init(Cipher.DECRYPT_MODE, aesKey);
		
		byte[] buffer = new byte[cipher.getBlockSize()];
		int count=0;
		byte[] decrypted = null;
		while((count=fis.read(buffer)) != -1)
		{
			decrypted = new byte[cipher.getOutputSize(count)];
			int noBytes = cipher.update(buffer, 0, count, decrypted);
			fos.write(decrypted, 0, noBytes);		
		}
		count=cipher.doFinal(decrypted, 0);
		fos.write(decrypted, 0, count);
		fos.close();
		fis.close();		
	}
	
	public static void readLines(String inFile) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(inFile));
		String line = null;
		while((line=br.readLine()) != null){
			System.out.println("Textul decriptat este: "+line);
		}
		
		
	}
	
	
}

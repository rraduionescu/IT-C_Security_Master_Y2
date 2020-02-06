package ro.ase.ism.sap;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Test {

	public static void main(String[] args) {
		//Verificare provider
		String bouncyCastleProvider = "BC";
		String SunProvider = "SunJCE"; //pot scrie SUN
		
		Security.addProvider(new BouncyCastleProvider()); //ctrl+space dupa Bouncy pentru verificare
		
		if(Security.getProvider(bouncyCastleProvider)!=null)
			System.out.println("BC available");
		else
			System.out.println("BC not installed");
		
		if(Security.getProvider(SunProvider)!=null)
			System.out.println("Sun available");
		else
			System.out.println("Sun not installed");
		
		//test hash
		try {
			byte[] hash = Hash.getHash("Mesaj.txt", bouncyCastleProvider);
			if(hash!=null){
				System.out.println("Hash fisier");
				System.out.println(DatatypeConverter.printHexBinary(hash));
			}
			else System.out.println("Not available");
			
			//test criptare AES - ECB
			String password = "passwordpassword";
			byte[] pass = password.getBytes();
			System.out.println("Key length "+pass.length);
			AesEcb.encrypt("Mesaj.txt","Mesaj.enc", pass, bouncyCastleProvider);
			System.out.println("Encryption done");
			AesEcb.decrypt("Mesaj.enc","Mesaj2.txt", pass, bouncyCastleProvider);
			System.out.println("Decryption done");
			
				
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShortBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

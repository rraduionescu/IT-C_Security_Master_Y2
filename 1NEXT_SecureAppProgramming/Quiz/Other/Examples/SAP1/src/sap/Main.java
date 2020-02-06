package sap;

import java.security.Security;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Main {

	public static void main(String[] args) throws Exception {
		//verificare provider
		String bouncyCastleProvider = "BC";
		String sunProvider = "SunJCE";
		
		Security.addProvider(new BouncyCastleProvider());
		for (int i = 0; i < Security.getProviders().length; i++){
			System.out.println(Security.getProviders()[i]);
		}
		if(Security.getProvider(bouncyCastleProvider)!=null){
			System.out.println(bouncyCastleProvider);
		} else {
			System.out.println(sunProvider);
		}
		byte[] hash = Hash.genereazaHash("MuchMagic.txt",bouncyCastleProvider);
		if (hash!=null){
			System.out.println("Hash:\n "+ DatatypeConverter.printHexBinary(hash));
		}
		
		String password = "passwordpassword";
		byte[] pass = password.getBytes();
		
		AES_ECB.encrypt("MuchMagic.txt", "MagicMuch.txt", pass, bouncyCastleProvider, Cipher.ENCRYPT_MODE);
		AES_ECB.encrypt("Mesaj.out", "muieMarales.txt", pass, sunProvider, Cipher.DECRYPT_MODE);
		
	}

}

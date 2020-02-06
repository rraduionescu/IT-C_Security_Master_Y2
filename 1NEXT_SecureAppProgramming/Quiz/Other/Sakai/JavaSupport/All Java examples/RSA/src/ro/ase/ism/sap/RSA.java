package ro.ase.ism.sap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {
	//method for accessing a keystore
	static void checkKeystore(File keystore,
			String pass,
			String provider) throws KeyStoreException, NoSuchProviderException, NoSuchAlgorithmException, CertificateException, IOException{
		
		FileInputStream fis = 
				new FileInputStream(keystore);
		
		KeyStore store = 
				KeyStore.getInstance(
						"JKS", provider);
		store.load(fis, pass.toCharArray());
		
		//iterate through the key pairs
		Enumeration<String> keyPairs = 
				store.aliases();
		
		while(keyPairs.hasMoreElements()){
			String alias = keyPairs.nextElement();
			System.out.println("Key pair:"+alias);
			
			//chek the entry type
			if(store.isCertificateEntry(alias))
				System.out.println("It's a certificate");
			else
				if(store.isKeyEntry(alias))
					System.out.println("It's a pair");
				else
					System.out.println("Something else");
		}
		
	}
	
	//method for getting the public key from a keystore
	public static PublicKey getPublicKey(
			File storeFile,
			String keyStorePass,
			String alias) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException{
		
		FileInputStream fis = 
				new FileInputStream(storeFile);
		
		KeyStore keystore = 
				KeyStore.getInstance("JKS");
		keystore.load(fis, keyStorePass.toCharArray());
		
		PublicKey pubKey = 
				keystore.getCertificate(alias).getPublicKey();
		
		return pubKey;
	}
	
	//method for getting the private key from a keystore
	public static PrivateKey getPrivateKey(
			File storeFile,
			String keyStorePass,
			String alias,
			String keyPass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		
		FileInputStream fis = 
				new FileInputStream(storeFile);
		
		KeyStore keystore = 
				KeyStore.getInstance("JKS");
		keystore.load(fis, keyStorePass.toCharArray());
		
		PrivateKey privKey = (PrivateKey) 
				keystore.getKey(alias, 
						keyPass.toCharArray());
		
		return privKey;
	}
	
	//method for getting the public key from a X509 certificate file
	public static PublicKey getCertificateKey(
			File file) throws FileNotFoundException, CertificateException{
		FileInputStream fis = 
				new FileInputStream(file);
		
		CertificateFactory factory = 
				CertificateFactory.getInstance("X509");
		
		X509Certificate certificate = (X509Certificate) 
				factory.generateCertificate(fis);
		
		return certificate.getPublicKey();
		
		
	}
	
	
	//method for RSA encryption
	public static byte[] encrypt(
			byte[] input,
			Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		
		Cipher cipher = 
				Cipher.getInstance(
						"RSA/None/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		
		return cipher.doFinal(input);
		
	}
	
	//method for RSA encryption
	public static byte[] decrypt(
			byte[] input,
			Key key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		
		Cipher cipher = 
				Cipher.getInstance(
						"RSA/None/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, key);
		
		return cipher.doFinal(input);
		
	}
	
	
	//method for a digital signature
	public static byte[] getDS(
			byte[] input,PrivateKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
		Signature signature = 
				Signature.getInstance("NONEwithRSA");
		
		signature.initSign(key);
		signature.update(input);
		return signature.sign();
		
	}
	
	//method for checking a signature
	public static boolean isVeryfied(
			byte[] input, 
			byte[] signatureValue,
			PublicKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
		
		Signature signature = 
				Signature.getInstance("NONEwithRSA");
		signature.initVerify(key);
		signature.update(input);
		
		return signature.verify(signatureValue);
		
	}
	
}

package ro.ase.ism.sap;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import javax.crypto.KeyGenerator;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Test {

	public static void main(String[] args) {
		try {
			// add BouncyCastle provider
			Security.addProvider(new BouncyCastleProvider());

			String BCProvider = "BC";
			String SunProvider = "SUN";

			File file = new File("ismkeystore.ks");
			
			RSA.checkKeystore(file,"passks",SunProvider);
			
			//test public and private key access
			PublicKey pub1 = 
					RSA.getPublicKey(file, 
							"passks", "ismkey1");
			System.out.println("ISM 1 public key:");
			System.out.println(
					DatatypeConverter.printHexBinary(
					pub1.getEncoded()));
			
			PrivateKey priv1 = 
					RSA.getPrivateKey(
							file,
							"passks",
							"ismkey1", "passism1");
			System.out.println("ISM 1 private key:");
			System.out.println(DatatypeConverter.printHexBinary(
					priv1.getEncoded()));
			
			File certificate = 
					new File("ISMCertificateX509.cer");
			PublicKey pub2 = 
					RSA.getCertificateKey(certificate);
			System.out.println("ISM 1 public key:");
			System.out.println(DatatypeConverter.printHexBinary(
					pub2.getEncoded()));
			
			//test RSA encryption
			String message = "Hello World !";
			byte[] cipherText = 
					RSA.encrypt(message.getBytes(), 
							pub1);
			byte[] message2 = 
					RSA.decrypt(cipherText, priv1);
			String testMsg = new String(message2);
			System.out.println("The message was "+
					testMsg);
			
			KeyGenerator generator = 
					KeyGenerator.getInstance("AES");
			generator.init(128);
			
			byte[] AESkey = 
					generator.generateKey().getEncoded();
			
			
			//test digital signature
			byte[] ds = 
					RSA.getDS(message.getBytes(), 
							priv1);
			
			boolean isOk = 
					RSA.isVeryfied(message.getBytes(), 
							ds, pub1);
			if(isOk)
				System.out.println("The message is OK");
			else
				System.out.println("There is a problem");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

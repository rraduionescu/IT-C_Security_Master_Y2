package ro.ase.ism;

import java.io.File;
import java.security.Security;

import javax.crypto.KeyGenerator;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

public class Test {

	public static void main(String[] args) {

		try {
			
			//add the BouncyCastle provider
			Security.addProvider(
					new BouncyCastleProvider());
			
			String SUNProvider = "SunJCE";
			String BCProvider = "BC";
			
			if(Security.getProvider(BCProvider)!=null)
				System.out.println("BC is installed");
			else
				System.out.println("BC is not available");
			
			// test hash functions
			File file = new File("Message.txt");
			
			byte[] hashValue = 
					HashFunction.getFileHash(
							file, "MD5",BCProvider);
			System.out.println("Hash: "
					+Utility.getHexDisplay(hashValue));
			
			
			byte[] hashMACValue = 
					HashFunction.getFileHashMAC(
							file, 
							"HmacSHA1", 
							SUNProvider,
							"key");
			System.out.println("hMAC: "
					+Utility.getHexDisplay(
							hashMACValue));
			
			//testing encryption - ECB
			File output = 
					new File("Message.enc");
			if(!output.exists())
				output.createNewFile();
			
			CypherFunctions.encryptECB(
					file, output, "DES", 
					"DES/ECB/PKCS5Padding",
					BCProvider, "password");
			
			File newPlaintext = 
					new File("Message2.txt");
			if(!newPlaintext.exists())
				newPlaintext.createNewFile();
			
			CypherFunctions.decryptECB(
					output, newPlaintext,
					"DES",
					"DES/ECB/PKCS5Padding",
					BCProvider, "password");
			
			
			//generate a random value
			KeyGenerator generator = 
					KeyGenerator.getInstance("AES",
							BCProvider);
			generator.init(128);
			byte[] IV = 
					generator.generateKey().getEncoded();
			
			
			IV = "passwordpassword".getBytes();
			IV = new byte[16];
			for(int i = 0 ;i<16;i++)
				IV[i] = (byte)0xff;
			
			CypherFunctions.encryptCBC(
					file, output, "AES",
					"AES/CBC/PKCS5Padding", 
					BCProvider, 
					"passwordpassword", 
					IV);
			
			CypherFunctions.decryptCBC(
					output, newPlaintext, 
					"AES", 
					"AES/CBC/PKCS5Padding", 
					BCProvider,
					"passwordpassword");
			
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

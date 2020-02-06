package ro.ionescu.radu;

import com.sun.org.apache.bcel.internal.classfile.Utility;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.RandomAccessFile;
import java.security.MessageDigest;

// @author Ionescu Radu Stefan //

public class MainPreparation
{
	public static void main(String[] args)
	{
		try
		{
			// 1. Hash Functions ( MD2 / MD5 / SHA-1 / SHA-256 / SHA-384 / SHA-512 )
			// Generate and display the hashes of the content of the "SAPExercises.txt" text file

			RandomAccessFile textFile = new RandomAccessFile("SAPExercises.txt", "r");
			byte[] textFileContent = new byte[(int)textFile.length()];
			textFile.readFully(textFileContent);

			System.out.println("\n1.Hash Functions - MD2 / MD5 / SHA-1 / SHA-256 / SHA-384 / SHA-512 ");
			System.out.println("  Hashes of \"" + new String(textFileContent) + "\" : ");
			MessageDigest hash;

			hash = MessageDigest.getInstance("MD2");
			hash.update(textFileContent);
			System.out.println("\tMD2 Hash     : " + Utility.toHexString(hash.digest()).toUpperCase() + " ( length: " + hash.digest().length + "bytes/" + hash.digest().length*8 + "bits )");

			hash = MessageDigest.getInstance("MD5");
			hash.update(textFileContent);
			System.out.println("\tMD5 Hash     : " + Utility.toHexString(hash.digest()).toUpperCase() + " ( length: " + hash.digest().length + "bytes/" + hash.digest().length*8 + "bits )");

			hash = MessageDigest.getInstance("SHA-1");
			hash.update(textFileContent);
			System.out.println("\tSHA-1 Hash   : " + Utility.toHexString(hash.digest()).toUpperCase() + " ( length: " + hash.digest().length + "bytes/" + hash.digest().length*8 + "bits )");

			hash = MessageDigest.getInstance("SHA-256");
			hash.update(textFileContent);
			System.out.println("\tSHA-256 Hash : " + Utility.toHexString(hash.digest()).toUpperCase() + " ( length: " + hash.digest().length + "bytes/" + hash.digest().length*8 + "bits )");

			hash = MessageDigest.getInstance("SHA-384");
			hash.update(textFileContent);
			System.out.println("\tSHA-384 Hash : " + Utility.toHexString(hash.digest()).toUpperCase() + " ( length: " + hash.digest().length + "bytes/" + hash.digest().length*8 + "bits )");

			hash = MessageDigest.getInstance("SHA-512");
			hash.update(textFileContent);
			System.out.println("\tSHA-512 Hash : " + Utility.toHexString(hash.digest()).toUpperCase() + " ( length: " + hash.digest().length + "bytes/" + hash.digest().length*8 + "bits )");

			// 2. Symmetric Encryption (DES/AES in ECB/CBC modes)
			// Encrypt and decrypt the content of the "SAPExercises.txt"

			System.out.println("\n1.Symmetric Encryption - DES/AES in ECB/CBC modes");
			System.out.println("  DES in ECB mode cipher text of \"" + new String(textFileContent) + "\" : ");

//			int lBlock = textFileContent.length % 8;
//			if(lBlock != 0)
//			{
//				byte[] old = textFileContent;
//				textFileContent = new byte[old.length + 8 - lBlock];
//				for(int i=0; i<old.length ; i++)
//				{
//					textFileContent[i] = old[i];
//				}
//			}
			SecretKeySpec DESKeySpec = new SecretKeySpec("password".getBytes(), "DES");
			Cipher DES_ECBCipher = Cipher.getInstance("DES/ECB/ISO10126Padding");
			DES_ECBCipher.init(Cipher.ENCRYPT_MODE, DESKeySpec);
			byte[] output = DES_ECBCipher.doFinal(textFileContent);

			System.out.println("\tEncryption key : password");
			System.out.println("\t" + Utility.toHexString(output).toUpperCase());

			textFile.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
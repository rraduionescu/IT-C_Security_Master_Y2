package ro.ionescu.radu;

import com.sun.org.apache.bcel.internal.classfile.Utility;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class Main
{
	public static void main(String[] args)
	{
		Security.addProvider(new BouncyCastleProvider());

		try
		{
			RSA.getKeystoreInfo("ismkeystore.ks", "passks");

			PublicKey publicKey1 = RSA.getPublicKey("ismkeystore.ks",
														"passks", "ismKey1");
			PrivateKey privateKey1 = RSA.getPrivateKey("ismkeystore.ks", "passks",
														"ismKey1", "passism1");

			System.out.println("Public key 1: " + Utility.toHexString(publicKey1.getEncoded()).toUpperCase());
			System.out.println("Private key 1: " + Utility.toHexString(privateKey1.getEncoded()).toUpperCase());

			// DIGITAL SIGNATURE TEST
			byte[] signature = RSA.getDigitalSignature("message.txt", privateKey1);

			if(RSA.isSignatureValid("message.txt", "ISMCertificateX509.cer", signature))
			{
				System.out.println("The file is valid");
			}
			else
			{
				System.out.println("The file was changed or it was sent by someone else");
			}

			// RSA ENCRYPTION TEST
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			generator.init(128);
			byte[] AESKey = generator.generateKey().getEncoded();

			System.out.println("AES Key: " + Utility.toHexString(AESKey).toUpperCase());
			byte[] encryptedAESKey = RSA.encryptRSA(AESKey, publicKey1);
			byte[] decryptedAESKey = RSA.decryptRSA(encryptedAESKey, privateKey1);
			System.out.println("AES Decrypted Key: " + Utility.toHexString(decryptedAESKey).toUpperCase());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(KeyStoreException e)
		{
			e.printStackTrace();
		}
		catch(CertificateException e)
		{
			e.printStackTrace();
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch(UnrecoverableKeyException e)
		{
			e.printStackTrace();
		}
		catch(InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch(SignatureException e)
		{
			e.printStackTrace();
		}
		catch(NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch(IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch(BadPaddingException e)
		{
			e.printStackTrace();
		}
	}
}

package ro.ionescu.radu;

import com.sun.org.apache.bcel.internal.classfile.Utility;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

public class Main
{
	public static void main(String[] args)
	{
		Security.addProvider(new BouncyCastleProvider());
		try
		{
			// Algorithm -> Advanced Encryption Standard
			// Cipher Mode -> Electronic Code Book (All File Data in a Single Run)
			// Padding Mode -> PKCS5 Padding

			// EXAMPLE 1
			CryptographyFactory.encryptECB("ECB_message.txt", "passwordpassword", "AES", "ECB_message.enc");
			CryptographyFactory.decryptECB("ECB_message.enc", "passwordpassword", "AES", "ECB_message.dec");

			// EXAMPLE 2
			CryptographyFactory.encryptECB("ECB_fixed_message.txt", "passwordpassword", "AES", "ECB_fixed_message.enc");
			CryptographyFactory.decryptECB("ECB_fixed_message.enc", "passwordpassword", "AES", "ECB_fixed_message.dec");

			// Algorithm -> Advanced Encryption Standard
			// Cipher Mode -> Cipher Block Chaining
			// Padding Mode -> PKCS5 Padding

			// EXAMPLE 1
			CryptographyFactory.encryptCBC("CBC_message.txt", "passwordpassword", "AES", "CBC_message.enc");
			CryptographyFactory.decryptCBC("CBC_message.enc", "passwordpassword", "AES", "CBC_message.dec");

			// HMAC Generation
			byte[] hmac = CryptographyFactory.getHMAC("CBC_message.txt", "password", "HmacMD5");
			System.out.println("HMAC: " + Utility.toHexString(hmac).toUpperCase());

			System.out.println("End of program!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		catch (ShortBufferException e)
		{
			e.printStackTrace();
		}
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch (InvalidAlgorithmParameterException e)
		{
			e.printStackTrace();
		}
	}
}

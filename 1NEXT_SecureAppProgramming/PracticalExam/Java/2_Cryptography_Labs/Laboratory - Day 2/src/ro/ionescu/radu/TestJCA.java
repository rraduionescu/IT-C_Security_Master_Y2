package ro.ionescu.radu;

import com.sun.org.apache.bcel.internal.classfile.Utility;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;

public class TestJCA
{
	public static void main(String[] args)
	{
		if(Security.getProvider("BC") == null)
		{
			Security.addProvider(new BouncyCastleProvider());
		}

		System.out.println("Providers:\n\t Name\t\tVersion");
		for(Provider p : Security.getProviders())
		{
			System.out.println("\t-" + p.getName() + (p.getName().length() < 7 ? "\t\t" : "\t") + (p.getName().length() < 3 ? "\t" : "") + p.getVersion());
		}
		System.out.println();

		try
		{
			byte[] hashValue = CryptographyFactory.getHashValue("message.txt", "MD5", "BC");

			System.out.println("MD5 Hash: \n\t" + Utility.toHexString(hashValue).toUpperCase());

			hashValue = CryptographyFactory.getStringHashValue("This is a secret message!", "SHA-1");

			System.out.println("\nString:\n\tThis is a secret message!\nSHA-1 Hash: \n\t" + Utility.toHexString(hashValue).toUpperCase());

			CryptographyFactory.encryptECB("message.txt", "DES", "password", "BC", "message.enc");
			CryptographyFactory.decryptECB("message.enc", "DES", "password", "BC", "message.dec");

		}
		catch(NoSuchProviderException e)
		{
			e.printStackTrace();
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch(ShortBufferException e)
		{
			e.printStackTrace();
		}
		catch(NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch(BadPaddingException e)
		{
			e.printStackTrace();
		}
		catch(InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		catch(IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}

	}
}

package ro.ionescu.radu;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

public class Main
{
	public static void main(String[] args)
	{
		if(Security.getProvider("BC") == null)
		{
			Security.addProvider(new BouncyCastleProvider());
		}

		String inputFileName = "Input.dec";
		String outputFileName = "Output.enc";
		String algorithm = "DES";
		String mode = "ECB";
		String padding = "ISO10126Padding";
		String provider = "BC";
		String key = "password";

		try
		{
			DESCipher.encrypt(inputFileName, algorithm, provider, key, outputFileName, mode, padding);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch(InvalidKeySpecException e)
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
		catch(BadPaddingException e)
		{
			e.printStackTrace();
		}
		catch(IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
	}
}

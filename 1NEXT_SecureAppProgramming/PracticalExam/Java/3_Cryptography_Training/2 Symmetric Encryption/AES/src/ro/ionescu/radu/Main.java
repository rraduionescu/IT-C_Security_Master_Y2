package ro.ionescu.radu;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import java.io.IOException;
import java.security.*;
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
		String provider = "BC";
		String algorithm = "AES";
		String mode = "CBC";
		String padding = "NoPadding";
		String key = "parola_puternica";

		try
		{
			AESCipher.encrypt(inputFileName, outputFileName, provider, algorithm, mode, padding, key);
		}
		catch(IOException e)
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
		catch(NoSuchPaddingException e)
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
		catch(NoSuchProviderException e)
		{
			e.printStackTrace();
		}
		catch(InvalidAlgorithmParameterException e)
		{
			e.printStackTrace();
		}
	}
}

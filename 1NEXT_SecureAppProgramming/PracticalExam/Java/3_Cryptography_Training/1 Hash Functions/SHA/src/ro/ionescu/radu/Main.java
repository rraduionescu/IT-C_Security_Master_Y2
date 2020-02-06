package ro.ionescu.radu;

import com.sun.org.apache.bcel.internal.classfile.Utility;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class Main
{
	public static void main(String[] args)
	{
		if(Security.getProvider("BC") == null)
		{
			Security.addProvider(new BouncyCastleProvider());
		}

		String inputFileName = "Input.dec";
		String algorithm = "SHA-512";
		String provider ="BC";

		try
		{
			byte[] hashValue = SHAFactory.getHash(inputFileName, algorithm,provider);
			System.out.println(algorithm + " Hash : " + Utility.toHexString(hashValue).toUpperCase());
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
	}
}

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

		try
		{
			byte[] MD5Hash = MD5Factory.getHash("input.txt", "MD5", "BC");
			System.out.println("MD5 Hash : " + Utility.toHexString(MD5Hash).toUpperCase());
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

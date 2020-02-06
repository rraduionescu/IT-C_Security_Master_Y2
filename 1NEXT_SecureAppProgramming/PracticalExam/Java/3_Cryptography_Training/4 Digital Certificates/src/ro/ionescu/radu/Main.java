package ro.ionescu.radu;

import com.sun.org.apache.bcel.internal.classfile.Utility;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			String keyStoreName = "Radu-Store.ks";
			String keyStorePassword = "raduradu";

			File inputFile = new File(keyStoreName);
			if(!inputFile.exists())
			{
				throw new FileNotFoundException();
			}
			FileInputStream fileInputStream = new FileInputStream(inputFile);

			KeyStore keyStore = KeyStore.getInstance("jks");
			keyStore.load(fileInputStream, keyStorePassword.toCharArray());

			Enumeration<String> aliases = keyStore.aliases();
			while(aliases.hasMoreElements())
			{
				String alias = aliases.nextElement();
				System.out.println(alias);
				System.out.println();

				if(keyStore.isKeyEntry(alias))
				{
					Certificate certificate = keyStore.getCertificate(alias);
					CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
					InputStream in = new ByteArrayInputStream(certificate.getEncoded());
					X509Certificate myCertificate = (X509Certificate)certificateFactory.generateCertificate(in);

					byte[] publicKey = certificate.getPublicKey().getEncoded();
					System.out.println(Utility.toHexString(publicKey));

					File outputFile = new File("certificat.cer");
					if(!outputFile.exists())
					{
						outputFile.createNewFile();
					}
					FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
					fileOutputStream.write(myCertificate.getEncoded());

					fileInputStream.close();
					fileOutputStream.close();
				}
			}
		}
		catch(KeyStoreException e)
		{
			e.printStackTrace();
		}
		catch(FileNotFoundException e)
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
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

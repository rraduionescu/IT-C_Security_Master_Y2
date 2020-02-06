package ism.ase.ro;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.xml.bind.DatatypeConverter;

public class Main {

	public static void main(String[] args) 
	{
		
		try {
			System.out.println("Hashul este: "+DatatypeConverter.printHexBinary(SecurityUtils.gethash("ClientISM.key").getBytes()));
			System.out.println("Private key is: "+DatatypeConverter.printHexBinary(SecurityUtils.getpriv("sapkeystore.ks", "passks".toCharArray(), "sapkey1", "sapex2016".toCharArray()).getEncoded()));
			
		}
		catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

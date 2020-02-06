package ism.ase.ro;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class Main {

	public static void main(String[] args) 
	{
		
		try {
			System.out.println("Hashul este: "+DatatypeConverter.printHexBinary(SecurityUtils.gethash("ClientISM.key").getBytes()));
			}
		catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}

	}

}

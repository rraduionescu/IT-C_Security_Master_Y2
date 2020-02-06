package ro.ase.ism.sap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class Hash {
	//metoda pentru calcul valoare hash
	public static byte[] getHash(String fileName, String provider) throws NoSuchAlgorithmException, 
															NoSuchProviderException, 
															IOException{
		byte[] hash = null;
		File fisier = new File(fileName);
		//verificam daca fisierul exista, ca sa pot citi din el
		if(fisier.exists()){//functiile hash proceseaza fisieruele la nivel de bloc
			FileInputStream fis = new FileInputStream(fisier);
			MessageDigest md = MessageDigest.getInstance("SHA-1",provider);	 //aici pot scrie MD5 //calcul functie Hash la nivelul java cryptographic architecture
			//trebuie sa citim din fisier bloc
			//intrebare: care e dimensiunea blocului procesat cu MD5?
			//definire bloc procesat
			byte[] buffer = new byte[8];
			int nrReadBytes = 0;
			while((nrReadBytes = fis.read(buffer))!=-1){
				//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				md.update(buffer,0,nrReadBytes); //vrem sa procedam cat am citit la sfarsit
				//greseala ar fi sa procesam tot bufferul
				//nu procesam toti bitii
				//s-ar putea ca cititnd in blocuri, sa mai ramana bytes la final
				}
			//regula: update, procesam fiecare bloc, apoi la final cerem valoarea finala
			hash = md.digest();	
			fis.close();
		}		
		return hash;
	}
}

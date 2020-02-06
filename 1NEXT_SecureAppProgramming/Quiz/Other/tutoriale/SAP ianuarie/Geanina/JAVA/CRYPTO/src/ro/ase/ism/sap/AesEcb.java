package ro.ase.ism.sap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

public class AesEcb {
	//metoda criptare
	public static void encrypt(String inputFile, String outputFile, byte[] key, String provider ) throws NoSuchAlgorithmException, 
					NoSuchProviderException, 
					NoSuchPaddingException, 
					InvalidKeyException, 
					IOException,
					ShortBufferException,
					IllegalBlockSizeException,
					BadPaddingException{ //fisier din care citim, cifru, parola
		File fisierIntrare = new File(inputFile);//deschidem fisierele
		if(!fisierIntrare.exists())
			throw new FileNotFoundException();
		FileInputStream fis = new FileInputStream(fisierIntrare);
		FileOutputStream fos = new FileOutputStream(outputFile);
		//1.Initializare cifru / algoritm
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", provider); //Electronic Code Book
		//2.Definire cheie + initializare 
		SecretKey secretKey = new SecretKeySpec(key,"AES");//clasa care incapsuleaza cheia
		cipher.init(Cipher.ENCRYPT_MODE,secretKey);
		//3.Citire blocuri + criptare
		byte[] buffer = new byte[cipher.getBlockSize()]; //atentie la dimensiunea cheii la apel //daca nu mai stim dimensiunea cheii
		int nrReadBytes = 0; //de fiecare data obtinem nr de bytes cititi
		byte[] encryptedBuffer = null; //ultimul bloc poate avea o dimensiune mai mica
		
		while((nrReadBytes = fis.read(buffer))!=-1){
			encryptedBuffer = new byte[cipher.getOutputSize(nrReadBytes)]; //important in special la ultimul bloc
			//acum doar imi creez bufferul in care voi obtine rezultatul, intreband bufferul cati bytes...
			int nrBytes = cipher.update(buffer,0,nrReadBytes,encryptedBuffer, 0); //ii proceseaza pe cei pe care tocmai i-am citit, la final 0 = offsetul de la care scriu
			//in encrypted buffer avem octetii criptati
			//nrBytes = dimensiunea lor
			fos.write(encryptedBuffer,0,nrBytes);
			}		
			///GRESEALA POSIBILA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			///UITAM SA CEREM ULTIMUL BLOC
			//ultimul bloc mai necesita un ciclu de procesare
			//ultim pas necesar, asa am obtine la decriptare un mesaj partial
		//4.Obtinem ultimul bloc criptat
		int nrBytes = cipher.doFinal(encryptedBuffer,0);
		fos.write(encryptedBuffer,0,nrBytes);
		//5.Inchidem fisierul
		fis.close();
		fos.close();
		
		
		//Prima verificare: fisier binar, dimensiunea fisierlui = multiplu de dimensiunea blocului
	}	
	
	
	public static void decrypt(String inputFile, String outputFile, byte[] key, String provider ) throws NoSuchAlgorithmException, 
	NoSuchProviderException, 
	NoSuchPaddingException, 
	InvalidKeyException, 
	IOException,
	ShortBufferException,
	IllegalBlockSizeException,
	BadPaddingException{ //fisier din care citim, cifru, parola
File fisierIntrare = new File(inputFile);//deschidem fisierele
if(!fisierIntrare.exists())
throw new FileNotFoundException();
FileInputStream fis = new FileInputStream(fisierIntrare);
FileOutputStream fos = new FileOutputStream(outputFile);
//1.Initializare cifru / algoritm
Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", provider); //Electronic Code Book
//2.Definire cheie + initializare 
SecretKey secretKey = new SecretKeySpec(key,"AES");//clasa care incapsuleaza cheia
cipher.init(Cipher.DECRYPT_MODE,secretKey);
//3.Citire blocuri + criptare
byte[] buffer = new byte[cipher.getBlockSize()]; //atentie la dimensiunea cheii la apel //daca nu mai stim dimensiunea cheii
int nrReadBytes = 0; //de fiecare data obtinem nr de bytes cititi
byte[] encryptedBuffer = null; //ultimul bloc poate avea o dimensiune mai mica

while((nrReadBytes = fis.read(buffer))!=-1){
encryptedBuffer = new byte[cipher.getOutputSize(nrReadBytes)]; //important in special la ultimul bloc
//acum doar imi creez bufferul in care voi obtine rezultatul, intreband bufferul cati bytes...
int nrBytes = cipher.update(buffer,0,nrReadBytes,encryptedBuffer, 0); //ii proceseaza pe cei pe care tocmai i-am citit, la final 0 = offsetul de la care scriu
//in encrypted buffer avem octetii criptati
//nrBytes = dimensiunea lor
fos.write(encryptedBuffer,0,nrBytes);
}		
///GRESEALA POSIBILA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
///UITAM SA CEREM ULTIMUL BLOC
//ultimul bloc mai necesita un ciclu de procesare
//ultim pas necesar, asa am obtine la decriptare un mesaj partial
//4.Obtinem ultimul bloc criptat
int nrBytes = cipher.doFinal(encryptedBuffer,0);
fos.write(encryptedBuffer,0,nrBytes);
//5.Inchidem fisierul
fis.close();
fos.close();


//Prima verificare: fisier binar, dimensiunea fisierlui = multiplu de dimensiunea blocului
}	
}

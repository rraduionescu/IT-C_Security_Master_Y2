package ro.ase.ism.sap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCbc {
	// metoda criptare in mod CBC
	// IV se genereaza random - se transmite in fisier
	public static void encrypt(byte[] key, String plainTextFile, String cipherTextFile) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {

		// open files
		FileInputStream input = new FileInputStream(plainTextFile);
		FileOutputStream output = new FileOutputStream(cipherTextFile);

		// generate random IV
		KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");
		generator.init(key.length * 8);

		byte[] IV = generator.generateKey().getEncoded();

		// write IV at the beginning of cipher text
		output.write(IV, 0, IV.length);

		// encrypt the plain text
		// 1. Create the Cipher

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

		// 2. Initializare cheie
		SecretKey secret = new SecretKeySpec(key, "AES");

		// 3. Initializarea Cipher
		IvParameterSpec IVSpec = new IvParameterSpec(IV);
		cipher.init(Cipher.ENCRYPT_MODE, secret, IVSpec);

		// 4. Read, encrypt and write
		byte[] buffer = new byte[cipher.getBlockSize()];
		int noReadBytes;
		byte[] cipherBlock = null;
		;

		while ((noReadBytes = input.read(buffer)) != -1) {
			cipherBlock = new byte[cipher.getOutputSize(noReadBytes)];
			int noBytes = cipher.update(buffer, 0, noReadBytes, cipherBlock);
			output.write(cipherBlock, 0, noBytes);
		}

		int noLastBlockBytes = cipher.doFinal(cipherBlock, 0);

		output.write(cipherBlock, 0, noLastBlockBytes);

		// 5. close the files
		input.close();
		output.close();

	}

	// metoda decriptare in mod CBC
	// IV se gaseste la inceputul mesajului criptat
	public static void decrypt(byte[] key, String cipherTextFile, String plainTextFile) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {

		// open files
		FileInputStream input = new FileInputStream(cipherTextFile);
		FileOutputStream output = new FileOutputStream(plainTextFile);

		// decrypt the cipher text
		// 1. Create the Cipher

		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

		// 2. Initializare cheie
		SecretKey secret = new SecretKeySpec(key, "AES");

		// 3. Initializarea Cipher

		byte[] IV = new byte[key.length];
		// read the IV form the cipher file
		input.read(IV, 0, IV.length);

		IvParameterSpec IVSpec = new IvParameterSpec(IV);
		cipher.init(Cipher.DECRYPT_MODE, secret, IVSpec);

		// 4. Read, decrypt and write
		byte[] buffer = new byte[cipher.getBlockSize()];
		int noReadBytes;
		byte[] cipherBlock = null;
		;

		while ((noReadBytes = input.read(buffer)) != -1) {
			cipherBlock = new byte[cipher.getOutputSize(noReadBytes)];
			int noBytes = cipher.update(buffer, 0, noReadBytes, cipherBlock);
			output.write(cipherBlock, 0, noBytes);
		}

		int noLastBlockBytes = cipher.doFinal(cipherBlock, 0);

		output.write(cipherBlock, 0, noLastBlockBytes);

		// 5. close the files
		input.close();
		output.close();

	}
}

package ro.ase.ism;

import javax.xml.bind.DatatypeConverter;

public class Utility {
	//method for printing a byte array in hexadecimal
	public static String getHexDisplay(byte[] value){
		return DatatypeConverter.printHexBinary(value);
	}
}

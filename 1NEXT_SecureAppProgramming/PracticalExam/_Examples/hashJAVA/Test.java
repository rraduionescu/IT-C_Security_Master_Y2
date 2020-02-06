package nume.prenume;

import java.io.File;

public class Test {

	public static void main(String[] args) {

		try {
						
			String SUNProvider = "SunJCE";
		
			// test hash functions
			File file = new File("mesaj.txt");
			
			byte[] hashValue = 
					HashFunction.getFileHash(
							file, "sha-256");
			System.out.println("Hash: "
					+Utility.getHexDisplay(hashValue));
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

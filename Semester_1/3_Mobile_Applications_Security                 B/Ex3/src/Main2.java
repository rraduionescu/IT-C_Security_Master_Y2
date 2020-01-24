import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

public class Main2
{

	public static void main(String[] args)
	{
		//Random random = new Random();
		SecureRandom random = new SecureRandom();
		int value = random.nextInt(99_999);

		String fileName = String.format("tmp%s.bin", value);
		File file = new File(fileName);

		FileWriter writer;
		try
		{
			String strToEncrypt = "secret info";
			byte[] keyData = {	(byte)0xfe,(byte)0x4c,(byte)0x01,(byte)0x67,
								(byte)0x4d,(byte)0x5c,(byte)0x4a,(byte)0xf7,
								(byte)0x0d,(byte)0x0b,(byte)0x85,(byte)0xfd,
								(byte)0x5a,(byte)0x40,(byte)0xd0,(byte)0xca};
			SecretKeySpec spec = new SecretKeySpec(keyData, "AES");
			Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, spec);
			c.doFinal(strToEncrypt.getBytes());
			writer = new FileWriter(file);
			writer.write(c.doFinal(strToEncrypt.getBytes()).toString());
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		//The file is in use
		try
		{
			Thread.sleep(10000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		//
		file.delete();
	}
}
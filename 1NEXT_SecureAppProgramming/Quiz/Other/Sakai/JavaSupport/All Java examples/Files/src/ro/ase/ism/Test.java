package ro.ase.ism;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class Test {

	public static void main(String[] args) {
		try {
			int intValue = 0xffffffff;
			double doubleValue = 23.6;
			String text = "Hello World !";

			File output = new File("Mydata.bin");

			if (!output.exists()) {
				output.createNewFile();
			}
			
			//writing data
			FileOutputStream fos = 
					new FileOutputStream(output);
			DataOutputStream dos = 
					new DataOutputStream(fos);
			dos.writeInt(intValue);
			dos.writeDouble(doubleValue);
			dos.writeUTF(text);
			
			dos.close();
			
			System.out.println("We have a file !");
			
			//reading data
			FileInputStream fis = 
					new FileInputStream(output);
			DataInputStream dis = 
					new DataInputStream(fis);
			
			//int vb = 1;
			//double db = 10;
			int vb = dis.readInt();
			double db = dis.readDouble();
			String txt = dis.readUTF();
			
			dis.close();
			
			System.out.println(vb);
			System.out.println(db);
			System.out.println(txt);
			
			File msg = new File("message.txt");
			if(!msg.exists())
				msg.createNewFile();
			
			PrintWriter pw =
					new PrintWriter(msg);
			pw.println("Hello");
			pw.println(vb);
			pw.println(db);
			pw.println(txt);
			
			pw.close();
			
			System.out.println("Message written");
			

		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

}

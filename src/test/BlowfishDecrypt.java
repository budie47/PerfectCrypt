package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class BlowfishDecrypt {

	  public static void blowfishEncrypt(String f1, String f2) throws Exception {
		    SecretKey key = null;
		    
		    String keytext = "mykey";
		    SecretKey secret_key = new SecretKeySpec(keytext.getBytes(), "Blowfish");
		    Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
		    
//		    ObjectInputStream keyFile = new ObjectInputStream(new FileInputStream("BlowfishKey.ser"));
//		    key = (SecretKey) keyFile.readObject();
//		    keyFile.close();
//		    Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
		    cipher.init(Cipher.DECRYPT_MODE, secret_key);
		    CipherInputStream in = new CipherInputStream(new BufferedInputStream(new FileInputStream(f1)),
		        cipher);
		    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(f2));
		    int i;
		    do {
		      i = in.read();
		      if (i != -1)
		        out.write(i);
		    } while (i > 0);
		    in.close();
		    out.close();
		  }
	  
	  public static void main(String[] args) throws Exception {
		    blowfishEncrypt("test/picE.jpg", "test/picD.jpg");
		  }
}

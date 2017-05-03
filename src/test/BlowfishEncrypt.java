package test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class BlowfishEncrypt {

	  public static void blowfishEncrypt(String f1, String f2) throws Exception {
//		    SecretKey key = null;
//
//		    ObjectInputStream keyFile = new ObjectInputStream(new FileInputStream("BlowfishKey.ser"));
//		    key = (SecretKey) keyFile.readObject();
//		    keyFile.close();
//
//		    KeyGenerator keygen = KeyGenerator.getInstance("Blowfish");
//		    key = keygen.generateKey();
//		    ObjectOutputStream keyFileOut = new ObjectOutputStream(new FileOutputStream("BlowfishKey.ser"));
//		    keyFileOut.writeObject(key);
//		    keyFileOut.close();
//		    Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
//		    cipher.init(Cipher.ENCRYPT_MODE, key);
		    String keytext = "mykey";
		    SecretKey secret_key = new SecretKeySpec(keytext.getBytes(), "Blowfish");
		    Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
		    cipher.init(Cipher.ENCRYPT_MODE, secret_key);
		  

		    BufferedInputStream in = new BufferedInputStream(new FileInputStream(f1));
		    CipherOutputStream out = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(f2)), cipher);
		    int i;
		    do {
		      i = in.read();
		      if (i != -1)
		        out.write(i);
		    } while (i != -1);
		    in.close();
		    out.close();
		  }
	  public static void main(String[] args) throws Exception {
		    blowfishEncrypt("pic.jpg", "test/picE.jpg");
		  }
}

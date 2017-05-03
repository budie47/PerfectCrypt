package controller;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import com.hazelcast.util.Base64;

import controller.TripleDESEncryption;

public class TripleDESEncryption {
	

	   public static void main(String []args) throws Exception {
	      byte[] toEncrypt = "The shorter you live, the longer you're dead!".getBytes();
	      
	      
		   TripleDESEncryption tDes = new TripleDESEncryption();
		   String random = tDes.getSaltString();
		   SecretKey sk = tDes.generateTripleDESKey(random);
	      
	      System.out.println("Encrypting...");
	      byte[] encrypted = encrypt(toEncrypt, sk);
	  
	      System.out.println("Decrypting...");
	      byte[] decrypted = decrypt(encrypted, sk);
	     
	      System.out.println("Decrypted text: " + decrypted);
	      System.out.println("Random String : " + random);
	   }
	   
	   public  String getSaltString() {
	        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 1024) { // length of the random string.
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = salt.toString();
	        return saltStr;

	    }
	   
	   public SecretKey generateTripleDESKey(String key)throws Exception{
		  // SecretKey secKey = null;
	      SecureRandom sr = new SecureRandom(key.getBytes());
	      KeyGenerator kg = KeyGenerator.getInstance("DESede");
	      kg.init(sr);
	      SecretKey sk = kg.generateKey();
		  return sk; 
	   }
	  
	   public static byte[] encrypt(byte[] toEncrypt, SecretKey skey) throws Exception {
	      // create a binary key from the argument key (seed)

	      Cipher cipher = Cipher.getInstance("DESede");
	      cipher.init(Cipher.ENCRYPT_MODE, skey);
	  
	      byte[] encrypted = cipher.doFinal(toEncrypt);
	  
	      return encrypted;
	   }
	  
	   public static byte[] decrypt(byte[] toDecrypt,  SecretKey skey) throws Exception {
	      // create a binary key from the argument key (seed)
	  
	      // do the decryption with that key
	      Cipher cipher = Cipher.getInstance("DESede");
	      cipher.init(Cipher.DECRYPT_MODE, skey);
	      byte[] decrypted = cipher.doFinal(toDecrypt);
	  
	      return decrypted;
	   }
	    

}

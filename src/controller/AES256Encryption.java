package controller;


import com.hazelcast.util.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.*;
import javax.crypto.spec.*;

public class AES256Encryption {
	
	public static SecretKey generateAES256Key() throws NoSuchAlgorithmException{
		int keyStrength = 256;
		 KeyGenerator kgen = KeyGenerator.getInstance("AES");
         kgen.init(keyStrength);
         SecretKey skey = kgen.generateKey();
         return skey;
	}
	public static SecretKey generateAESPasswordKey(String password, String salt) throws NoSuchAlgorithmException{
		SecretKey secret = null;
		byte[] salt2 = salt.getBytes();
		char[] passwordChar = password.toCharArray();
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		KeySpec spec = new PBEKeySpec(passwordChar, salt2, 65536, 128);
		SecretKey tmp;
		try {
			tmp = factory.generateSecret(spec);
			secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return secret;
	}
	
	public static SecretKey generateAES192Key() throws NoSuchAlgorithmException{
		int keyStrength = 256;
		 KeyGenerator kgen = KeyGenerator.getInstance("AES");
         kgen.init(keyStrength);
         SecretKey skey = kgen.generateKey();
         return skey;
	}

    public static String encrypt (String strKey, String strIv, String str) {
        String secret = "";
        try{
            byte[] key = Base64.decode(strKey.getBytes());
            byte[] iv  = Base64.decode(strIv.getBytes());

            SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            secret = new String(Base64.encode(cipher.doFinal(str.getBytes())));

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return secret;
    }
    
    public static byte[] encryptAES256(String strKey, String strIv, byte[] plainByte) {
        byte[] secret = null;
        try{
            byte[] key = Base64.decode(strKey.getBytes());
            byte[] iv  = Base64.decode(strIv.getBytes());

            SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            secret = Base64.encode(cipher.doFinal(plainByte));

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return secret;
    }
    
    public static String decrypt (String strKey, String strIv, String str) {
        String secret = "";
        try{

            byte[] key = Base64.decode(strKey.getBytes());
            byte[] iv  = Base64.decode(strIv.getBytes());

            SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, keyspec,ivspec);
            secret = new String(cipher.doFinal(Base64.decode(str.getBytes())));
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return secret;
    }
    
    public static byte[] decryptAES256 (String strKey, String strIv, byte[] cipherByte) {
        byte[] secret = null;
        try{

            byte[] key = Base64.decode(strKey.getBytes());
            byte[] iv  = Base64.decode(strIv.getBytes());


            SecretKeySpec keyspec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, keyspec,ivspec);
            secret = cipher.doFinal(Base64.decode(cipherByte));

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return secret;
    }
    
	public static byte[] convertToByte(String filePath){
		byte[] data = null;
		try{
			Path path = Paths.get(filePath);
			data = Files.readAllBytes(path);
	
		}catch (Exception err){
			err.printStackTrace();
		}
		return data;
	}
    
    public static void main(String[] argv) {
        String strIv = "18A5Z/IsHs6g8/65sBxkCQ==";
        String strKey = "";
        try {

            SecretKey skey = generateAES256Key();
            
            byte[] raw = skey.getEncoded();
            strKey = new String(Base64.encode(raw));
            
            File f = new File("pic.jpg");
            byte [] plainByte = convertToByte("pic.jpg");
            
            byte[] encryptedByte = AES256Encryption.encryptAES256(strKey, strIv, plainByte);
            File fE = new File("picE.jpg");
            FileOutputStream fos = new FileOutputStream(fE);
			fos.write(encryptedByte);
			fos.close();
			
            byte[] decryptByteText = decryptAES256 (strKey, strIv, encryptedByte);
            File fD = new File("picD.jpg");
            FileOutputStream fos1 = new FileOutputStream(fD);
            fos1.write(decryptByteText);
            fos1.close();
			
            System.out.println("Secret key is: " + strKey);
            System.out.println("Encrypted Byte is: " + new String(encryptedByte));
            System.out.println("Decrypted Byte is: " + new String(decryptByteText));
            
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        String message = "My, it's a lovely day today!!!";
        String encrypted = AES256Encryption.encrypt(strKey, strIv, message);
        
        
        System.out.println("Encrypted string is: " + encrypted);
        System.out.println("Decrypted string is: " + AES256Encryption.decrypt(strKey, strIv, encrypted));


    }
}

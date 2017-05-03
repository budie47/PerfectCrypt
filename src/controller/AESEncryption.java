package controller;

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;

public class AESEncryption {
	 
	
	static int  pswdIterations = 65536  ;
	public static SecretKey getSecretEncryptionKey(int bit) throws Exception{
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(bit);
		SecretKey secKey = generator.generateKey();
		return secKey;
	}
	
	public static byte[] encrypt(byte[] plainText, SecretKey secKey)throws Exception{
		byte[] byteCipherText;
		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
		byteCipherText = aesCipher.doFinal(plainText);
		return byteCipherText;
	}
	
	public static byte[] decrypt(byte[] byteCipherText, SecretKey secKey)throws Exception{
		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.DECRYPT_MODE, secKey);
		byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
		return bytePlainText;
		
	}
	//public static 
    public static String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
    
    public static SecretKey generateSecretKeyAES256(String password,  byte[] saltBytes) throws Exception{
       //String password1 = "test";
       //String salt;
	    int keySize = 256;
	          
	    //byte[] saltBytes = salt.getBytes("UTF-8");

    	SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    	PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),saltBytes,pswdIterations,keySize);
    	
    	SecretKey secretKey = factory.generateSecret(spec);
    	return secretKey;
    }
    
    public byte[] encryptAES256(byte[] plainByte, SecretKey secretKey) throws Exception{
    	SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
    	 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
         cipher.init(Cipher.ENCRYPT_MODE, secret);
         AlgorithmParameters params = cipher.getParameters();
         byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
         byte[] encryptedBytes = cipher.doFinal(plainByte);
         return encryptedBytes;
    }
    
    public byte[] decryptAES245(byte[] encryptedByte,SecretKey secretKey)throws Exception{
        byte[] decryptedBytes = null;
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        // Decrypt the message
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        AlgorithmParameters params = cipher.getParameters();
        byte[] ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
        try {
        	decryptedBytes = cipher.doFinal(encryptedByte);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return decryptedBytes;
    }
    
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String s = new String(bytes);
        return s;
    }
    
	public SecretKey getSecretKey256()throws Exception{
		String passwords = "test";
		SecretKey secretKey = null;
        String salt = generateSalt();      
        byte[] saltBytes = salt.getBytes("UTF-8");
        int pswdIterations = 65536  ;
        int keySize = 256;
        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(passwords.toCharArray(),saltBytes,pswdIterations,keySize);
        secretKey = factory.generateSecret(spec);
        return secretKey;
		//return secretKey;
	}
	
	public SecretKey generateSecretKey256() throws NoSuchAlgorithmException{
		SecretKey secKey = null;
		int keyStrength = 256;
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(keyStrength);
        secKey = kgen.generateKey();
		return secKey;
	}
	


}

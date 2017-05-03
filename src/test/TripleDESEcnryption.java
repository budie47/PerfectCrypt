package test;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;


public class TripleDESEcnryption {
	  private static final String UNICODE_FORMAT = "UTF8";
	    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	    private KeySpec myKeySpec;
	    private SecretKeyFactory mySecretKeyFactory;
	    private Cipher cipher;
	    byte[] keyAsBytes;
	    private String myEncryptionKey;
	    private String myEncryptionScheme;
	    SecretKey key;
	 
	    public TripleDESEcnryption() throws Exception
	    {
	        myEncryptionKey = "ThisIsSecretEncryptionKey";
	        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
	        keyAsBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
	        myKeySpec = new DESedeKeySpec(keyAsBytes);
	        mySecretKeyFactory = SecretKeyFactory.getInstance(myEncryptionScheme);
	        cipher = Cipher.getInstance(myEncryptionScheme);
	        key = mySecretKeyFactory.generateSecret(myKeySpec);
	    }
	 
	    /**
	     * Method To Encrypt The String
	     */
	    public String encrypt(String unencryptedString) {
	        String encryptedString = null;
	        try {
	            cipher.init(Cipher.ENCRYPT_MODE, key);
	            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
	            byte[] encryptedText = cipher.doFinal(plainText);
	            //BASE64Encoder base64encoder = new BASE64Encoder();
	            //encryptedString = base64encoder.encode(encryptedText);
	            encryptedString = Base64.encodeBase64String(encryptedText);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return encryptedString;
	    }
	    /**
	     * Method To Decrypt An Ecrypted String
	     */
	    public String decrypt(String encryptedString) {
	        String decryptedText=null;
	        try {
	            cipher.init(Cipher.DECRYPT_MODE, key);
	            //BASE64Decoder base64decoder = new BASE64Decoder();
	            //byte[] encryptedText = base64decoder.decodeBuffer(encryptedString);
	            byte[] encryptedText = Base64.decodeBase64(encryptedString);
	            byte[] plainText = cipher.doFinal(encryptedText);
	            decryptedText= bytes2String(plainText);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return decryptedText;
	    }
	    /**
	     * Returns String From An Array Of Bytes
	     */
	    private static String bytes2String(byte[] bytes) {
	        StringBuffer stringBuffer = new StringBuffer();
	        for (int i = 0; i < bytes.length; i++) {
	            stringBuffer.append((char) bytes[i]);
	        }
	        return stringBuffer.toString();
	    }
	 
	    /**
	     * Testing The DESede Encryption And Decryption Technique
	     */
	    public static void main(String args []) throws Exception
	    {
	    	TripleDESEcnryption myEncryptor= new TripleDESEcnryption();
	 
	        String stringToEncrypt="Encrypt this String";
	        String encrypted = myEncryptor.encrypt(stringToEncrypt);
	        String decrypted = myEncryptor.decrypt(encrypted);
	 
	        System.out.println("String To Encrypt: "+stringToEncrypt);
	        System.out.println("Encrypted Value :" + encrypted);
	        System.out.println("Decrypted Value :"+decrypted);
	 
	    }
}

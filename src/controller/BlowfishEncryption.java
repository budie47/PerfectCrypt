package controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.bouncycastle.util.encoders.Base64Encoder;

import com.hazelcast.util.Base64;

/**
 *
 * 
 */
public class BlowfishEncryption {

    KeyGenerator keyGenerator = null;
    SecretKey secretKey = null;
    Cipher cipher = null;

    public BlowfishEncryption() {

    }
    
    public SecretKey getBlowfishKey()throws Exception{
    	 keyGenerator = KeyGenerator.getInstance("Blowfish");
         secretKey = keyGenerator.generateKey();
         return secretKey;
    }

    public static void main(String[] args) throws Exception{
        String fileToEncrypt = "Naruto.mp4";
        String encryptedFile = "test/NarutoE.mp4";
        String decryptedFile = "test/NarutoD.mp4";
        BlowfishEncryption encryptFile = new BlowfishEncryption();
        
        SecretKey skey = encryptFile.getBlowfishKey();
        
        String plainText = "Masih Belum Sempurna";
        String cipherText = encryptFile.encryptText(plainText, skey);
        String decryptText = encryptFile.decryptText(cipherText, skey);
        
        System.out.println("PLAIN TEXT ");
        System.out.println(plainText);
        System.out.println("CIPHER TEXT ");
        System.out.println(cipherText);
        System.out.println("PLAIN TEXT ");
        System.out.println(decryptText);
        
//        System.out.println("Starting Encryption...");
//        encryptFile.encrypt(fileToEncrypt,encryptedFile,skey);
//        System.out.println("Encryption completed...");
//        System.out.println("Starting Decryption...");
//        encryptFile.decrypt( encryptedFile, decryptedFile,skey);
//        System.out.println("Decryption completed...");
    }

    /**
     * 
     * @param srcPath
     * @param destPath
     *
     * Encrypts the file in srcPath and creates a file in destPath
     */
    public void encrypt(String srcPath, String destPath, SecretKey skey) {
        File rawFile = new File(srcPath);
        File encryptedFile = new File(destPath);
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            /**
             * Initialize the cipher for encryption
             */
        	cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            /**
             * Initialize input and output streams
             */
            inStream = new FileInputStream(rawFile);
            outStream = new FileOutputStream(encryptedFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) > 0) {
                outStream.write(cipher.update(buffer, 0, len));
                outStream.flush();
            }
            outStream.write(cipher.doFinal());
            inStream.close();
            outStream.close();
        } catch (IllegalBlockSizeException ex) {
            System.out.println(ex);
        } catch (BadPaddingException ex) {
            System.out.println(ex);
        } catch (InvalidKeyException ex) {
            System.out.println(ex);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public String encryptText(String plainText, SecretKey skey) {

        InputStream inStream = null;
        OutputStream outStream = null;
       
        byte[] cipherByte;
        String cipherText = null;
        byte[] plainByte = plainText.getBytes();
        try {
            /**
             * Initialize the cipher for encryption
             */
        	cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            cipherByte = cipher.doFinal(plainByte);
            cipherText = new String(Base64.encode(cipherByte));
            

        } catch (IllegalBlockSizeException ex) {
            System.out.println(ex);
        } catch (BadPaddingException ex) {
            System.out.println(ex);
        } catch (InvalidKeyException ex) {
            System.out.println(ex);
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return cipherText;
    }

    /**
     * 
     * @param srcPath
     * @param destPath
     *
     * Decrypts the file in srcPath and creates a file in destPath
     */
    public void decrypt(String srcPath, String destPath, SecretKey skey) {
        File encryptedFile = new File(srcPath);
        File decryptedFile = new File(destPath);
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            /**
             * Initialize the cipher for decryption
             */
        	cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            /**
             * Initialize input and output streams
             */
            inStream = new FileInputStream(encryptedFile);
            outStream = new FileOutputStream(decryptedFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inStream.read(buffer)) > 0) {
                outStream.write(cipher.update(buffer, 0, len));
                outStream.flush();
            }
            outStream.write(cipher.doFinal());
            inStream.close();
            outStream.close();
        } catch (IllegalBlockSizeException ex) {
            System.out.println(ex);
        } catch (BadPaddingException ex) {
            System.out.println(ex);
        } catch (InvalidKeyException ex) {
            System.out.println(ex);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public String decryptText(String cipherText, SecretKey skey) {
    	String plainText = null;
        try {
            /**
             * Initialize the cipher for decryption
             */
        	byte[] plainByte;
        	byte[] cipherByte;
        	cipherByte = Base64.decode(cipherText.getBytes());
        	cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            plainByte = cipher.doFinal(cipherByte);
            plainText = new String(plainByte);
            
            /**
             * Initialize input and output streams
             */
           
        } catch (IllegalBlockSizeException ex) {
            System.out.println(ex);
        } catch (BadPaddingException ex) {
            System.out.println(ex);
        } catch (InvalidKeyException ex) {
            System.out.println(ex);
        }  catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return plainText;
    }
}
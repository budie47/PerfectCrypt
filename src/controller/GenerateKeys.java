package controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class GenerateKeys {
	private KeyPairGenerator keyGen;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	
	public  GenerateKeys(){
		super();
	}

	public GenerateKeys(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
		this.keyGen = KeyPairGenerator.getInstance("RSA");
		this.keyGen.initialize(keylength);
	}

	public void createKeys() {
		this.pair = this.keyGen.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}
	
	public void setPublicKey(PublicKey key){
		this.publicKey = key;
	}
	public String getStringPublicKey(PublicKey key){
		String sKey = Base64.getEncoder().encodeToString(key.getEncoded());
		return sKey;
	}
	public byte[] encryptAESSecretKey (PublicKey pubKey, SecretKey skey)
	{
	    Cipher cipher = null;
	    byte[] key = null;
	    try{
	        cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, pubKey );
	        key = cipher.doFinal(skey.getEncoded());
	    }
	    catch(Exception e ){
	        System.out.println ( "exception encoding key: " + e.getMessage() );
	        e.printStackTrace();
	    }
	    return key;
	}
	public SecretKey decryptAESSecretKey(byte[] data,PrivateKey priKey,String method){
        SecretKey key = null;
        PrivateKey privKey = null;
        Cipher cipher = null;
        try
        {
            privKey = priKey;
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privKey );
            key = new SecretKeySpec ( cipher.doFinal(data), method );
        }
        catch(Exception e)
        {
            System.out.println ( "exception decrypting the aes key: "+ e.getMessage() );
            return null;
        }
        return key;
	}
	public PublicKey getPublicKeyFromString(String sKey){
		PublicKey pubKey = null;
		try{
			byte[] byteKey = Base64.getDecoder().decode(sKey);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(byteKey);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			pubKey = keyFactory.generatePublic(keySpec);
			//pubKey = byteKey;
		}catch(Exception e){
			
		}
		
		return pubKey;
	}

	public void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}
	public void createDirectory(String path) throws IOException{
		File f = new File(path);
		f.getParentFile().mkdirs();
	}

	public static void main(String[] args) {
		GenerateKeys gk;
		String sKey;
		PublicKey pubKey;
		try {
			gk = new GenerateKeys(1024);
			gk.createKeys();
			gk.writeToFile("file/key/publicKey/", gk.getPublicKey().getEncoded());
			gk.writeToFile("file/key/privateKey/", gk.getPrivateKey().getEncoded());
			sKey = gk.getStringPublicKey(gk.getPublicKey());
			System.out.println("String public Key : "+sKey);
			
			pubKey = gk.getPublicKeyFromString(sKey);
			System.out.println("Public Key From String : "+pubKey);
			
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}
}

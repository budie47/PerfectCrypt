package controller;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import com.hazelcast.util.Base64;

public class DigitalSignature {

	
	public byte[] sign(byte[] plainByte, PrivateKey privateKey)throws Exception{
		byte[] signature = null;
		Signature ds = Signature.getInstance("SHA1withRSA");
		ds.initSign(privateKey);
		ds.update(plainByte);
		signature = ds.sign();
		return signature;
	}
	public boolean verifySignature(byte[] data, byte[] signature, PublicKey publicKey) throws Exception {
		Signature sig = Signature.getInstance("SHA1withRSA");
		sig.initVerify(publicKey);
		sig.update(data);
		
		return sig.verify(signature);
	}
	
	//public boolean verifySignature(byte[] plainBytes,byte signature,String key)
	
	public PrivateKey getPrivate(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}
	
	public PrivateKey getPrivateKey(String decryptedPrivateKey) throws Exception{
	
		byte[] epkey = Base64.decode(decryptedPrivateKey.getBytes());
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(epkey));
		return privateKey;
	}
	
}

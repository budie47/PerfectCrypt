package controller;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class DigitalSignature {

	
	public byte[] sign(byte[] plainByte,String keyFilePath)throws Exception{
		byte[] signature = null;
		Signature ds = Signature.getInstance("SHA1withRSA");
		ds.initSign(getPrivate(keyFilePath));
		ds.update(plainByte);
		signature = ds.sign();
		return signature;
	}
	
	//public boolean verifySignature(byte[] plainBytes,byte signature,String key)
	
	public PrivateKey getPrivate(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}
	
}

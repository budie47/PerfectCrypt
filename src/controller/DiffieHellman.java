package controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.hazelcast.util.Base64;

public class DiffieHellman {
	
	int bitLength=512;	
	int certainty=20;
	 private static final SecureRandom rnd = new SecureRandom();
	 private static BigInteger primeValue =  new BigInteger("12048922229043489380536370615335915778056805034970141091276193270971353829271632790900372654424588959551224014097021748055260726485484931427757386143501949");
	private BigInteger secretKey;
	private BigInteger publicKey;
	
	public BigInteger getSecretKey() {
		return secretKey;
	}



	public void setSecretKey(BigInteger secretKey) {
		this.secretKey = secretKey;
	}



	public BigInteger getPublicKey() {
		return publicKey;
	}



	public void setPublicKey(BigInteger publicKey) {
		this.publicKey = publicKey;
	}
	
	public SecretKeySpec getDHSharedKey(BigInteger publicB, BigInteger secretKey) throws Exception{
		
		BigInteger sharedKey = publicB.modPow(secretKey, primeValue);
		String getAValue=sharedKey.toString();
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(getAValue.getBytes());

	    byte byteData[] = md.digest();
	    StringBuffer sb = new StringBuffer();

	    for(int i=0;i<byteData.length;i++)
	    {
	        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));// ??
	    }

	    String getHexValue = sb.toString();
	    System.out.println("hex format in SHA-256 is "+getHexValue);

	    byte [] key = getAValue.getBytes("UTF-8");
	    
	    MessageDigest sha = MessageDigest.getInstance("SHA-256");
	    key =  sha.digest(key);
	    key = Arrays.copyOf(key, 16);
	    SecretKeySpec secretKeySpec =  new SecretKeySpec(key,"AES");
	    
	    return secretKeySpec;
		
	}



	public DiffieHellman() throws Exception{
		Random randomGenerator = new Random();
		BigInteger generatorValue,publicA,secretA,sharedKeyA;

		generatorValue = findPrimeRoot(primeValue);
		secretKey = new BigInteger(bitLength-2,randomGenerator);
		publicKey = generatorValue.modPow(secretKey, primeValue);

	}
	
	
	
	private BigInteger findPrime(){
		
		Random rnd=new Random();
		BigInteger p=BigInteger.ZERO;
		// while(!isPrime(p))
		p= new BigInteger(bitLength, certainty, rnd);// sufficiently NSA SAFE?!!
		return p;
			

	}
	
	private BigInteger findPrimeRoot(BigInteger p){
		int start= 2001;// first best probably precalculated by NSA?
		// preferably  3, 17 and 65537
		//if(start==2)compareWolfram(p);

		for(int i=start;i<100000000;i++)
			if(isPrimeRoot(BigInteger.valueOf(i),p))
				return BigInteger.valueOf(i);
				// if(isPrimeRoot(i,p))return BigInteger.valueOf(i);
		return BigInteger.valueOf(0);
	}
	
	boolean isPrimeRoot(BigInteger g, BigInteger p)
	{
	 BigInteger totient = p.subtract(BigInteger.ONE); //p-1 for primes;// factor.phi(p);
	 List<BigInteger> factors = primeFactors(totient);
	 int i = 0;
	 int j = factors.size();
	 for(;i < j; i++)
	 {
	     BigInteger factor = factors.get(i);//elementAt
	     BigInteger t = totient.divide( factor);
			if(g.modPow(t, p).equals(BigInteger.ONE))return false;
	 }
	 return true;
	}
	public List<BigInteger> primeFactors(BigInteger number) {
		 BigInteger n = number;
			BigInteger i=BigInteger.valueOf(2);
			BigInteger limit=BigInteger.valueOf(10000);// speed hack! -> consequences ???
			List<BigInteger> factors = new ArrayList<BigInteger>();
			while (!n.equals(BigInteger.ONE)){
				while (n.mod(i).equals(BigInteger.ZERO)){
		     factors.add(i);
				n=n.divide(i);
				// System.out.println(i);
				// System.out.println(n);
				if(isPrime(n)){
					factors.add(n);// yes?
					return factors;
				}
		  	}
				i=i.add(BigInteger.ONE);
				if(i.equals(limit))return factors;// hack! -> consequences ???
				// System.out.print(i+"    \r");
			}
				System.out.println(factors);
		return factors;
		}
	
	boolean isPrime(BigInteger r){
		return miller_rabin(r);
		// return BN_is_prime_fasttest_ex(r,bitLength)==1;
	}
	
	public static boolean miller_rabin(BigInteger n) {
	    for (int repeat = 0; repeat < 20; repeat++) {
	        BigInteger a;
	        do {
	            a = new BigInteger(n.bitLength(), rnd);
	        } while (a.equals(BigInteger.ZERO));
	        if (!miller_rabin_pass(a, n)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	private static boolean miller_rabin_pass(BigInteger a, BigInteger n) {
	    BigInteger n_minus_one = n.subtract(BigInteger.ONE);
	    BigInteger d = n_minus_one;
		int s = d.getLowestSetBit();
		d = d.shiftRight(s);
	    BigInteger a_to_power = a.modPow(d, n);
	    if (a_to_power.equals(BigInteger.ONE)) return true;
	    for (int i = 0; i < s-1; i++) {
	        if (a_to_power.equals(n_minus_one)) return true;
	        a_to_power = a_to_power.multiply(a_to_power).mod(n);
	    }
	    if (a_to_power.equals(n_minus_one)) return true;
	    return false;
	}
	
	public String encryptDH(String plainText, SecretKey skey) throws Exception{
		byte[] plainByte = plainText.getBytes();
		byte[] cipherByte;
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skey);
		
		cipherByte = cipher.doFinal(plainByte);
		String cipherText = new String(Base64.encode(cipherByte));
		return cipherText;
	}
	
	public String decryptDH(String cipherText,SecretKey skey)throws Exception{
		byte[] plainByte;
    	byte[] cipherByte;
    	cipherByte = Base64.decode(cipherText.getBytes());
    	Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skey);
        plainByte = cipher.doFinal(cipherByte);
        String plainText = new String(plainByte);
        return plainText;
	}


	
	public static void main(String [] args) throws Exception
	{
		DiffieHellman dh = new DiffieHellman();//sender
		System.out.println("Secret Key : " + dh.getSecretKey());
		System.out.println("Public Key : " + dh.getPublicKey());
		
		DiffieHellman dh1 = new DiffieHellman();//receiver
		System.out.println("Secret Key : " + dh1.getSecretKey());
		System.out.println("Public Key : " + dh1.getPublicKey());
		
		System.out.println("Shared Key : " + dh.getDHSharedKey(dh1.getPublicKey(), dh.getSecretKey()));
		System.out.println("Shared Key : " + dh1.getDHSharedKey(dh.getPublicKey(), dh1.getSecretKey()));
		System.out.println("Shared Key : " + dh1.getDHSharedKey(dh1.getPublicKey(), dh1.getSecretKey()));
		
		String plainText = "THE INTERNATIONAL";
		String cipherText;
		
		cipherText = dh.encryptDH(plainText, dh.getDHSharedKey(dh1.getPublicKey(), dh.getSecretKey()));
		
		System.out.println("CipherText : " + cipherText);
		
		String decryptText = dh.decryptDH(cipherText, dh1.getDHSharedKey(dh.getPublicKey(), dh1.getSecretKey()));
		
		System.out.println("Decrypted Text : " + decryptText);
		
		
	}
	
	
}

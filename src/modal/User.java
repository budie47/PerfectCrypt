package modal;

import java.io.Serializable;
import java.security.PublicKey;

public class User implements Serializable {
	private String fname;
	private String username;
	private String password;
	
	private String icNo;
	private String phoneNo;
	private String email;
	private String address1;
	private String address2;
	private String address3;
	private String publicKey;
	private String encryptedPrivateKey;
	public String getEncryptedPrivateKey() {
		return encryptedPrivateKey;
	}

	public void setEncryptedPrivateKey(String encryptedPrivateKey) {
		this.encryptedPrivateKey = encryptedPrivateKey;
	}

	private String type;
	private String MAC;
	
	
	public String getMAC() {
		return MAC;
	}

	public void setMAC(String mAC) {
		MAC = mAC;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User(){
		super();
	}
	
	public User(String fname, String username, String password, String icNo, String phoneNo, String email, String address1,
			String address2, String address3){
		super();
		this.fname = fname;
		this.username = username;
		this.password = password;
		this.icNo = icNo;
		this.phoneNo = phoneNo;
		this.email = email;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIcNo() {
		return icNo;
	}

	public void setIcNo(String icNo) {
		this.icNo = icNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

}

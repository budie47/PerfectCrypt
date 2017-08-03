package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hazelcast.util.Base64;

import controller.AES256Encryption;
import controller.AESEncryption;
import controller.BlowfishEncryption;
import controller.ConfigServer;
import controller.DESEcryption;
import controller.DigitalSignature;
import controller.FileConvert;
import controller.GenerateKeys;
import controller.StaticRI;
import controller.TripleDESEncryption;
import controller.UserController;

import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class GuiDecryptFile {

	public JFrame frame;
	public JLabel lFileName;
	public JLabel lFIleDir;
	public JLabel lMethodE;
	public String destPath;
	private JPasswordField passwordField;
	ConfigServer cs = new ConfigServer();
	String host = cs.host;
	String regName = cs.regName;
	
	String username = null;
	String decryptPrivateKey;
	String encryptedPrivateKey = null;
	byte[] digitalSignature;
	PublicKey senderPK;
	FileConvert fConvert = new FileConvert();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiDecryptFile window = new GuiDecryptFile();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiDecryptFile() {
		initialize();
	}
	public byte[] getFileByte(File file) {
		byte[] cipherText =  new byte[(int)file.length()];
		
		try{
			FileInputStream fis = new FileInputStream(file);
			fis.read(cipherText);
			fis.close();
			
		}catch(Exception er){
			er.printStackTrace();
		}
		
		return cipherText;
	}
	
	public void createDirPlainFolder(String path){
		String[] plainPath = path.split("/");
		String filePath = plainPath[0]+"/plain"+"/"+plainPath[2]+"/"+plainPath[3];
		destPath = filePath+"/"+plainPath[4];
		File fp = new File(filePath);
		fp.mkdirs();
	}
	
	public byte[] getSecretKey(File file) throws IOException{
		//File file = new File("/temp/abc.txt");
		//init array with file length
		byte[] bytesArray = new byte[(int) file.length()];

		FileInputStream fis = new FileInputStream(file);
		fis.read(bytesArray); //read file into bytes[]
		fis.close();
		
		return bytesArray;
	}

	public PrivateKey getPrivateKey(String decryptedPrivateKey) throws Exception{
		
		byte[] epkey = Base64.decode(decryptedPrivateKey.getBytes());

		//byte[] pkey = decryptedPrivateKey.getBytes();
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(epkey));
		return privateKey;
	}
	
	public String getKeyPath(String filePath){
		
		String[] dkArry = filePath.split("/");
		String keyPath = dkArry[0] +"/" +dkArry[1] +"/"+dkArry[2]+"/"+dkArry[3]+"/key";
		return keyPath;
		
		
	}
	public String getSaltPath(String filePath){
		
		String[] dkArry = filePath.split("/");
		String keyPath = dkArry[0] +"/" +dkArry[1] +"/"+dkArry[2]+"/"+dkArry[3]+"/salt";
		return keyPath;
		
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 355, 488);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 347, 70);
		frame.getContentPane().add(panel);
		
		JLabel lblSendingFile = new JLabel("Decrypting The File");
		lblSendingFile.setFont(new Font("Candara", Font.BOLD, 28));
		lblSendingFile.setBounds(54, 11, 244, 55);
		panel.add(lblSendingFile);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 81, 320, 357);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblFileName = new JLabel("File Name");
		lblFileName.setFont(new Font("Candara", Font.PLAIN, 15));
		lblFileName.setBounds(10, 30, 140, 25);
		panel_1.add(lblFileName);
		
		JLabel lblNewLabel = new JLabel("File Information");
		lblNewLabel.setFont(new Font("Candara", Font.BOLD, 18));
		lblNewLabel.setBounds(10, 11, 205, 19);
		panel_1.add(lblNewLabel);
		
		lFileName = new JLabel("-");
		lFileName.setFont(new Font("Candara", Font.PLAIN, 15));
		lFileName.setBounds(10, 57, 279, 25);
		panel_1.add(lFileName);
		
		JLabel lblFileDirectory = new JLabel("File Directory");
		lblFileDirectory.setFont(new Font("Candara", Font.PLAIN, 15));
		lblFileDirectory.setBounds(10, 93, 173, 25);
		panel_1.add(lblFileDirectory);
		
		lFIleDir = new JLabel("-");
		lFIleDir.setFont(new Font("Candara", Font.PLAIN, 15));
		lFIleDir.setBounds(10, 117, 279, 25);
		panel_1.add(lFIleDir);
		
		JLabel lblMethodEncryption = new JLabel("Method Encryption");
		lblMethodEncryption.setFont(new Font("Candara", Font.PLAIN, 15));
		lblMethodEncryption.setBounds(10, 173, 173, 25);
		panel_1.add(lblMethodEncryption);
		
		lMethodE = new JLabel("-");
		lMethodE.setFont(new Font("Candara", Font.PLAIN, 15));
		lMethodE.setBounds(10, 198, 279, 25);
		panel_1.add(lMethodE);
		
		JLabel lblLoadYourPrivate = new JLabel("Load Your Private Key");
		lblLoadYourPrivate.setFont(new Font("Candara", Font.PLAIN, 15));
		lblLoadYourPrivate.setBounds(10, 222, 173, 25);
		panel_1.add(lblLoadYourPrivate);
		
		JButton btnDecryptCancel = new JButton("Cancel");
		btnDecryptCancel.setBounds(10, 313, 140, 33);
		panel_1.add(btnDecryptCancel);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.setEnabled(false);
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String method = lMethodE.getText();
				//String privateKeyPath = lblPrivateKey.getText();
				SecretKey secKey;
				boolean verifyDs = false;
				DigitalSignature ds = new DigitalSignature();
				byte[] cipherKeyText = null;
				byte[] plainByte = null;
				String fileDirectory = lFIleDir.getText();
				String keyPath = getKeyPath(fileDirectory);
				cipherKeyText = getFileByte(new File(keyPath));
				createDirPlainFolder(fileDirectory);
				
				GenerateKeys gk = new GenerateKeys();
				File encryptedFile = new File(fileDirectory);
				byte[] cipherByte = getFileByte(encryptedFile);
				System.out.println(cipherByte);
				try {
					PrivateKey privateKey  = getPrivateKey(decryptPrivateKey);
					
					if(method.equals("AES-128")){
						
						secKey =  gk.decryptAESSecretKey(cipherKeyText, privateKey,"AES");
						AESEncryption aes = new AESEncryption();
						plainByte = aes.decrypt(cipherByte, secKey);
						verifyDs = ds.verifySignature(plainByte, digitalSignature, senderPK);
						if(verifyDs){
							JOptionPane.showMessageDialog(null,"Digital Signature for this file verified", "Digital Signature", JOptionPane.INFORMATION_MESSAGE);
						}
						gk.writeToFile(destPath, plainByte);
						
					} else if(method.equals("AES-192")){
						
						secKey =  gk.decryptAESSecretKey(cipherKeyText, privateKey,"AES");
						String strIv = "18A5Z/IsHs6g8/65sBxkCQ==";
			            byte[] raw = secKey.getEncoded();
			            String strKey = new String(Base64.encode(raw));
						AES256Encryption aes256 = new AES256Encryption();
						plainByte = aes256.decryptAES256(strKey, strIv, cipherByte);
						gk.writeToFile(destPath, plainByte);
						
					}else if(method.equals("AES-256")){
						
						secKey =  gk.decryptAESSecretKey(cipherKeyText, privateKey,"AES");
						String strIv = "18A5Z/IsHs6g8/65sBxkCQ==";
			            byte[] raw = secKey.getEncoded();
			            String strKey = new String(Base64.encode(raw));
						AES256Encryption aes256 = new AES256Encryption();
						plainByte = aes256.decryptAES256(strKey, strIv, cipherByte);
						verifyDs = ds.verifySignature(plainByte, digitalSignature, senderPK);
						if(verifyDs){
							JOptionPane.showMessageDialog(null,"Digital Signature for this file verified", "Digital Signature", JOptionPane.INFORMATION_MESSAGE);
						}
						gk.writeToFile(destPath, plainByte);
						
					}else if(method.equals("DES")){
						
						System.out.println(method);
						secKey =  gk.decryptAESSecretKey(cipherKeyText, privateKey,"DES");
						String encodedKey = new String(Base64.encode(secKey.getEncoded()));
						System.out.println("My DES key : "+encodedKey);
						
						DESEcryption des = new DESEcryption();
						plainByte = des.decryptDES(cipherByte, secKey);
						verifyDs = ds.verifySignature(plainByte, digitalSignature, senderPK);
						if(verifyDs){
							JOptionPane.showMessageDialog(null,"Digital Signature for this file verified", "Digital Signature", JOptionPane.INFORMATION_MESSAGE);
						}
						gk.writeToFile(destPath, plainByte);
						
					}else if(method.equals("Triple DES")){
						
						System.out.println(method);
						secKey =  gk.decryptAESSecretKey(cipherKeyText, privateKey,"TripleDES");
						TripleDESEncryption tdes = new TripleDESEncryption();
						plainByte = tdes.decrypt(cipherByte, secKey);
						verifyDs = ds.verifySignature(plainByte, digitalSignature, senderPK);
						if(verifyDs){
							JOptionPane.showMessageDialog(null,"Digital Signature for this file verified", "Digital Signature", JOptionPane.INFORMATION_MESSAGE);
						}
						gk.writeToFile(destPath, plainByte);
						
					}else if(method.equals("Blowfish")){
						
						System.out.println(method);
						secKey =  gk.decryptAESSecretKey(cipherKeyText, privateKey,"Blowfish");
						BlowfishEncryption bf = new BlowfishEncryption();
						bf.decrypt(fileDirectory, destPath, secKey);
						plainByte = fConvert.convertToByte(destPath);
						
						verifyDs = ds.verifySignature(plainByte, digitalSignature, senderPK);
						if(verifyDs){
							JOptionPane.showMessageDialog(null,"Digital Signature for this file verified", "Digital Signature", JOptionPane.INFORMATION_MESSAGE);
						}
						
						
					}
					Desktop desktop = Desktop.getDesktop();
					desktop.open(new File(destPath));
					frame.dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnDecrypt.setBounds(170, 313, 140, 33);
		panel_1.add(btnDecrypt);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(10, 269, 205, 33);
		panel_1.add(passwordField);
		
		JButton btnNewButton = new JButton("Load");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					System.out.println(username);
					
					AES256Encryption aes = new AES256Encryption();
					UserController uc = new UserController();
					String stringPass = "null";
					
					char[] pass = passwordField.getPassword();
					
					for (int i = 0;i < pass.length; i++){			
		
						stringPass += Character.toString(pass[i]);
					}
					
					 Registry creg = LocateRegistry.getRegistry(host,1099);
			            StaticRI cstub = (StaticRI)creg.lookup(regName);
			            encryptedPrivateKey = cstub.getUserEncryptedPrivateKey(username);

			            decryptPrivateKey = uc.decryptPrivateKey(encryptedPrivateKey,stringPass);
			            btnDecrypt.setEnabled(true);
			            
				}catch(Exception e1){
					e1.printStackTrace();
				}
		       }
		});
		btnNewButton.setBounds(221, 269, 89, 33);
		panel_1.add(btnNewButton);
		
		JLabel lblTypeYourPassword = new JLabel("Type your password");
		lblTypeYourPassword.setFont(new Font("Candara", Font.PLAIN, 12));
		lblTypeYourPassword.setBounds(10, 251, 173, 25);
		panel_1.add(lblTypeYourPassword);
	}
}

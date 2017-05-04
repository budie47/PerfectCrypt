package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.PublicKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.crypto.SecretKey;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.hazelcast.util.Base64;
import com.healthmarketscience.rmiio.GZIPRemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamServer;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;

import controller.AES256Encryption;
import controller.AESEncryption;
import controller.BlowfishEncryption;
import controller.ConfigServer;
import controller.DESEcryption;
import controller.FileConvert;
import controller.FileSendingCS;
import controller.GenerateKeys;
import controller.RmiTransferClient;
import rmitransfer.Server;
import controller.StaticRI;
import controller.TripleDESEncryption;
import rmitransfer.TestClient;
import test.TripleDESEcnryption;
import modal.ComboItem;
import modal.FileModal;
import modal.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JList;

public class GuSendFiles {
	ConfigServer cs = new ConfigServer();
	String host = cs.host;
	String regName = cs.regName;

	public JFrame frame;
	private JTextField tFileName;
	private JTextField textField;
	JButton btnSend;
	JButton btnEncrypt;
	JComboBox comboBox;
	JList listFriends;
	JLabel lFile_name;
	public JLabel lMain_Username;
	JLabel lFriend_Name;
	Vector<User> listFriend = null;
	JLabel lblAsy;
	JLabel lblEess;
	JComboBox comboBoxPub;
	String fileName;
	String publicKeyUserString;
	PublicKey publicKeyUser;
	String pathKey;
	String saltPath;
	String method;
	String receiveName;
	String receiverPath;
	private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	GenerateKeys gk = new GenerateKeys();
	FileModal fm = new FileModal();
	Date date = new Date();
	String dateS = sdf.format(date).toString();
	FileConvert fConvert = new FileConvert();
	byte[] plainByte = null;
	byte[] cipherByte = null;
	String pathFile;
	AESEncryption aes = new AESEncryption();
	DESEcryption des = new DESEcryption();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuSendFiles window = new GuSendFiles();
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
	public GuSendFiles() {
		initialize();
	}
	
	public void getFriendList(String name){
		try{
			name = lMain_Username.getText();
			Registry creg = LocateRegistry.getRegistry(host);
			StaticRI cstub = (StaticRI)creg.lookup(regName);
			listFriend = cstub.getCurrentFriend(name);
			DefaultListModel friendListModel = new DefaultListModel();
			for(User friend : listFriend){
				friendListModel.addElement(friend.getUsername());
			}
			listFriends.setModel(friendListModel);
			listFriends.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					lFriend_Name.setText(listFriends.getSelectedValue().toString());
			}
			});
			
		}catch (Exception err){
			err.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 695, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		FileModal fm = new FileModal();
		 Date date = new Date();
		 String dateS = sdf.format(date).toString();
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 676, 70);
		frame.getContentPane().add(panel);
		
		JLabel label = new JLabel("Username :");
		label.setFont(new Font("Candara", Font.PLAIN, 16));
		label.setBounds(408, 43, 79, 27);
		panel.add(label);
		
		JLabel lblSendingFile = new JLabel("Sending File");
		lblSendingFile.setFont(new Font("Candara", Font.BOLD, 45));
		lblSendingFile.setBounds(22, 4, 287, 41);
		panel.add(lblSendingFile);
		
		lMain_Username = new JLabel("");
		lMain_Username.setFont(new Font("Candara", Font.PLAIN, 16));
		lMain_Username.setBounds(492, 43, 178, 27);
		panel.add(lMain_Username);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		btnCancel.setBounds(580, 4, 90, 28);
		panel.add(btnCancel);
		
		JLabel label_2 = new JLabel("Perfect Crypt");
		label_2.setFont(new Font("Candara", Font.PLAIN, 25));
		label_2.setBounds(22, 43, 287, 27);
		panel.add(label_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 81, 138, 319);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblSelectYourFriend = new JLabel("Select Your Friend");
		lblSelectYourFriend.setFont(new Font("Candara", Font.BOLD, 16));
		lblSelectYourFriend.setBounds(10, 0, 160, 26);
		panel_1.add(lblSelectYourFriend);
		
		listFriends = new JList();
		listFriends.setBounds(10, 27, 118, 281);
		panel_1.add(listFriends);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(443, 81, 226, 266);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblSendTo = new JLabel("Send To :");
		lblSendTo.setFont(new Font("Candara", Font.PLAIN, 16));
		lblSendTo.setBounds(10, 11, 91, 26);
		panel_2.add(lblSendTo);
		
		JLabel lblFileName = new JLabel("File Name :");
		lblFileName.setFont(new Font("Candara", Font.PLAIN, 16));
		lblFileName.setBounds(10, 81, 91, 26);
		panel_2.add(lblFileName);
		
		lFriend_Name = new JLabel("-");
		lFriend_Name.setFont(new Font("Candara", Font.BOLD, 16));
		lFriend_Name.setBounds(10, 44, 91, 26);
		panel_2.add(lFriend_Name);
		
		lFile_name = new JLabel("-");
		lFile_name.setFont(new Font("Candara", Font.BOLD, 16));
		lFile_name.setBounds(10, 118, 206, 26);
		panel_2.add(lFile_name);
		
		JLabel lblMethodEncryption_1 = new JLabel("Method Encryption");
		lblMethodEncryption_1.setFont(new Font("Candara", Font.PLAIN, 16));
		lblMethodEncryption_1.setBounds(10, 155, 192, 26);
		panel_2.add(lblMethodEncryption_1);
		
		lblAsy = new JLabel("-");
		lblAsy.setFont(new Font("Candara", Font.BOLD, 16));
		lblAsy.setBounds(10, 192, 192, 26);
		panel_2.add(lblAsy);
		
		lblEess = new JLabel("-");
		lblEess.setFont(new Font("Candara", Font.BOLD, 16));
		lblEess.setBounds(10, 229, 192, 26);
		panel_2.add(lblEess);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBackground(Color.LIGHT_GRAY);
		panel_3.setBounds(152, 81, 287, 62);
		frame.getContentPane().add(panel_3);
		
		JLabel lblChooseFile = new JLabel("Choose File");
		lblChooseFile.setFont(new Font("Candara", Font.BOLD, 16));
		lblChooseFile.setBounds(10, 0, 160, 26);
		panel_3.add(lblChooseFile);
		
		tFileName = new JTextField();
		tFileName.setBounds(10, 24, 170, 26);
		panel_3.add(tFileName);
		tFileName.setColumns(10);
		
		JButton btnNewButton = new JButton("Browse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		          File selectedFile = fileChooser.getSelectedFile();
		          tFileName.setText(selectedFile.getPath());
		          lFile_name.setText(selectedFile.getName());
		          //System.out.println(selectedFile.getName());
		        }
			}
		});
		btnNewButton.setBounds(195, 26, 82, 23);
		panel_3.add(btnNewButton);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBackground(Color.LIGHT_GRAY);
		panel_4.setBounds(152, 147, 287, 200);
		frame.getContentPane().add(panel_4);
		
		JLabel lblMethodEncryption = new JLabel("Method Encryption");
		lblMethodEncryption.setFont(new Font("Candara", Font.BOLD, 16));
		lblMethodEncryption.setBounds(10, 0, 160, 26);
		panel_4.add(lblMethodEncryption);
		
		JRadioButton rdbtnAsymetric = new JRadioButton("Secret-Key encryption");
		rdbtnAsymetric.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblEess.setText("");
				lblAsy.setText("Secret-Key encryption");
				comboBox.setVisible(true);
				comboBoxPub.setVisible(false);
				//comboBox.removeAllItems();

			
			

			}
		});
		rdbtnAsymetric.setBounds(10, 33, 256, 23);
		panel_4.add(rdbtnAsymetric);
		
		JRadioButton rdbtnSymetric = new JRadioButton("Public-Key encryption");
		rdbtnSymetric.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblEess.setText("");
				lblAsy.setText("Public-Key encryption");
				comboBoxPub.setVisible(true);
				comboBox.setVisible(false);
				//comboBox.removeAllItems();


			}
		});
		rdbtnSymetric.setBounds(10, 66, 256, 23);
		panel_4.add(rdbtnSymetric);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnAsymetric);
		group.add(rdbtnSymetric);
		
		comboBox = new JComboBox();


		comboBox.setBounds(10, 96, 256, 28);
		panel_4.add(comboBox);
		comboBox.setVisible(false);
		comboBox.addItem(new ComboItem("Select Here", ""));
		comboBox.addItem(new ComboItem("AES 128-bit", "AES-128"));
		comboBox.addItem(new ComboItem("AES 192-bit", "AES-192"));
		comboBox.addItem(new ComboItem("AES 256-bit", "AES-256"));
		comboBox.addItem(new ComboItem("DES", "DES"));
		comboBox.addItem(new ComboItem("Triple DES", "Triple DES"));
		comboBox.addItem(new ComboItem("Blowfish", "Blowfish"));
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//String s = (String)comboBox.getSelectedItem();
				lblEess.setText(comboBox.getSelectedItem().toString());
			}
		});
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(10, 163, 170, 26);
		panel_4.add(textField);
		textField.setVisible(false);
		
		JButton button_1 = new JButton("Browse");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button_1.setBounds(195, 165, 82, 23);
		panel_4.add(button_1);
		button_1.setVisible(false);
		
		JLabel lblLoadYourPrivate = new JLabel("Load The Key");
		lblLoadYourPrivate.setFont(new Font("Candara", Font.BOLD, 16));
		lblLoadYourPrivate.setBounds(10, 125, 160, 26);
		panel_4.add(lblLoadYourPrivate);
		lblLoadYourPrivate.setVisible(false);
		
		comboBoxPub = new JComboBox();
		comboBoxPub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblEess.setText(comboBoxPub.getSelectedItem().toString());
			}
		});
		comboBoxPub.setBounds(10, 96, 256, 28);
		comboBoxPub.setVisible(false);
		comboBoxPub.addItem(new ComboItem("Select Here", ""));
		comboBoxPub.addItem(new ComboItem("EEC", "EEC"));
		comboBoxPub.addItem(new ComboItem("RSA", "RSA"));
		panel_4.add(comboBoxPub);
	
		
		btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				pathKey = "file/"+lMain_Username.getText()+"/"+dateS+"/key";
				saltPath = "file/"+lMain_Username.getText()+"/"+dateS+"/salt";
				
				File file = new File(pathKey);
				file.getParentFile().mkdirs();
				pathFile = tFileName.getText();
				fileName = lFile_name.getText();
				receiveName = lFriend_Name.getText();

				Object item = comboBox.getSelectedItem();
				method = ((ComboItem)item).getValue();
				int fileSize = pathFile.length();
				boolean status = false;
				
				if(receiveName.equals("-") && fileName.equals("-") && lblAsy.getText().equals("-") && lblEess.getText().equals("-")){
					JOptionPane.showMessageDialog(null,"Please Select your recipent, file, and method of encryption that you want using", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(receiveName.equals("-") && fileName.equals("-") && lblAsy.getText().equals("-")){
					JOptionPane.showMessageDialog(null,"Please Select your recipent, method of encryption that you want using", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(receiveName.equals("-") && fileName.equals("-") && lblEess.getText().equals("-")){
					JOptionPane.showMessageDialog(null,"Please Select method of encryption that you want using", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(receiveName.equals("-") && fileName.equals("-")){
					JOptionPane.showMessageDialog(null,"Please Select method of encryption that you want using", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(receiveName.equals("-")){
					JOptionPane.showMessageDialog(null,"Please Select your file, and method of encryption that you want using", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(receiveName.equals("-") && fileName.equals("-")){
					JOptionPane.showMessageDialog(null,"Please Select your recipent and file", "Error", JOptionPane.ERROR_MESSAGE);
				} else if(fileName.equals("-")){
					JOptionPane.showMessageDialog(null,"Please Select your file", "Error", JOptionPane.ERROR_MESSAGE);
				} else if( lblAsy.getText().equals("-") && lblEess.getText().equals("-")){
					JOptionPane.showMessageDialog(null,"Please Select your recipent and file", "Error", JOptionPane.ERROR_MESSAGE);
				} else{
					getPublicKey();
					if(method.equals("AES-128")){
						SecretKey secKey;
						try {
							secKey = aes.getSecretEncryptionKey(128);
							encryptFile(secKey);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else if(method.equals("AES-192")){
						try{
							encryptAES192();
						}catch (Exception e2){
							e2.printStackTrace();
						}
					} else if(method.equals("AES-256")){
						//SecretKey secKey256;
						try {
							encryptAES256();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}else if(method.equals("DES")){
						//SecretKey secKey256;
						try {
							
							//SecretKey desSecKey = des.generateDESKey();
							encryptDES();
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}else if(method.equals("Triple DES")){
						//SecretKey secKey256;
						try {
							
							//SecretKey desSecKey = des.generateDESKey();
							encryptTripleDES();
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}else if(method.equals("Blowfish")){
						//SecretKey secKey256;
						try {
							
							//SecretKey desSecKey = des.generateDESKey();
							encryptBlowFish();
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
					
					JOptionPane.showMessageDialog(null,"File has been encrypted. Please click send button to send", "Encryption", JOptionPane.WARNING_MESSAGE);
					btnEncrypt.setEnabled(false);
					btnSend.setEnabled(true);

				}
		
			}
		});
		btnEncrypt.setBounds(152, 358, 287, 42);
		frame.getContentPane().add(btnEncrypt);
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					String senderPath = "file/"+lMain_Username.getText()+"/"+dateS+"/"+ fileName;
					System.out.print(senderPath);
					receiverPath = "file/"+lFriend_Name.getText()+"/"+dateS+"/"+ fileName;
					String receiverKey = "file/"+lFriend_Name.getText()+"/"+dateS+"/key";
					String checkPath = "file/"+lFriend_Name.getText()+"/"+dateS;
					Registry creg = LocateRegistry.getRegistry(host);
					StaticRI cstub = (StaticRI)creg.lookup(regName);
					File check = new File(receiverPath);
					cstub.checkPath(checkPath);
					fm.setPath(receiverPath);
					System.out.println(fm);
					cstub.saveData(method,receiveName,receiverPath);
					
		        	String url = "rmi://localhost:1100/server";
					Server server = (Server) Naming.lookup(url);
					RmiTransferClient.upload(server, new File(senderPath), new File(receiverPath));
					RmiTransferClient.upload(server, new File(pathKey), new File(receiverKey));
					//RmiTransferClient.upload(server, new File(saltPath), new File(checkPath+"/salt"));
					JOptionPane.showMessageDialog(null,"File has been Send.", "Sending", JOptionPane.WARNING_MESSAGE);
					
					
				}catch(Exception eee){
					eee.printStackTrace();
				}			
			}
		});
		btnSend.setBounds(443, 358, 226, 42);
		btnSend.setEnabled(false);
		frame.getContentPane().add(btnSend);

	}
	TestClient tc = new TestClient();
	
	public boolean uploadFile(String filePath,String newFileName){
       boolean state = false;
        try {
        	String url = "rmi://localhost/server";
        	System.out.println("2");
			Server server = (Server) Naming.lookup(url);
			tc.upload(server, new File(filePath),new File(newFileName));
			
			state = true;
			//upload(server, new File("C:Users\\-D-\\Desktop\\pic.jpg"),new File("pic.jpg"));
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return state;
	}
	
	public void getPublicKey(){
		try {
			Registry creg;
			creg = LocateRegistry.getRegistry(host);
			StaticRI cstub = (StaticRI)creg.lookup(regName);
			publicKeyUserString = cstub.getPublicKey(receiveName);
			publicKeyUser = gk.getPublicKeyFromString(publicKeyUserString);
			//System.out.println(publicKeyUser);
		} catch (RemoteException | NotBoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	public void encryptDES(){
		try{
			
			SecretKey myDesKey = des.generateDESKey();
			String encodedKey = new String (Base64.encode(myDesKey.getEncoded()));
			System.out.println("My DES key : "+encodedKey);
			
			byte[] encryptSecKey = gk.encryptAESSecretKey(publicKeyUser, myDesKey);
			gk.writeToFile(pathKey, myDesKey.getEncoded());
			fConvert.setFile(fileName);
			
			plainByte = fConvert.convertToByte(pathFile);
			cipherByte = des.encryptDES(plainByte, myDesKey);
			fm.setReceiveName(receiveName);
			File f = new File("file/"+lMain_Username.getText()+"/"+dateS+"/"+fileName);
			f.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(cipherByte);
			fos.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void encryptFile(SecretKey secKey){
		
		JOptionPane.showMessageDialog(null,method, "Method", JOptionPane.WARNING_MESSAGE);
		try {
			
			byte[] encryptSecKey = gk.encryptAESSecretKey(publicKeyUser, secKey);
			gk.writeToFile(pathKey, encryptSecKey);
			fConvert.setFile(fileName);
			plainByte = fConvert.convertToByte(pathFile);
			cipherByte = aes.encrypt(plainByte, secKey);
			fm.setReceiveName(receiveName);
			File f = new File("file/"+lMain_Username.getText()+"/"+dateS+"/"+fileName);
			f.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(cipherByte);
			fos.close();
			JOptionPane.showMessageDialog(null,"File has been encrypted. Please click send button to send", "Encryption", JOptionPane.WARNING_MESSAGE);
			btnEncrypt.setEnabled(false);
			btnSend.setEnabled(true);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void encryptAES256(){
		
		JOptionPane.showMessageDialog(null,method, "Method", JOptionPane.WARNING_MESSAGE);
		 File encryptedFile = new File("file/"+lMain_Username.getText()+"/"+dateS+"/"+fileName);
		 encryptedFile.getParentFile().mkdirs();
		try {
	        String strIv = "18A5Z/IsHs6g8/65sBxkCQ==";
	        String strKey = "";
	        
			AES256Encryption aes256 = new AES256Encryption();
			SecretKey secKey256 = aes256.generateAES256Key();
			byte[] encryptSecKey = gk.encryptAESSecretKey(publicKeyUser, secKey256);
			gk.writeToFile(pathKey, encryptSecKey);
			
			byte[] raw = secKey256.getEncoded();
	        strKey = new String(Base64.encode(raw));
	        
	        plainByte = fConvert.convertToByte(pathFile);

           
           // byte [] plainByte = fConvert.convertToByte("pic.jpg");
            
            byte[] encryptedByte = AES256Encryption.encryptAES256(strKey, strIv, plainByte);
            
            FileOutputStream fos = new FileOutputStream(encryptedFile);
			fos.write(encryptedByte);
			fos.close();
	           

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void encryptAES192(){
		
		JOptionPane.showMessageDialog(null,method, "Method", JOptionPane.WARNING_MESSAGE);
		 File encryptedFile = new File("file/"+lMain_Username.getText()+"/"+dateS+"/"+fileName);
		 encryptedFile.getParentFile().mkdirs();
		try {
	        String strIv = "18A5Z/IsHs6g8/65sBxkCQ==";
	        String strKey = "";
	        
			AES256Encryption aes192 = new AES256Encryption();
			SecretKey secKey256 = aes192.generateAES192Key();
			byte[] encryptSecKey = gk.encryptAESSecretKey(publicKeyUser, secKey256);
			gk.writeToFile(pathKey, encryptSecKey);
			
			byte[] raw = secKey256.getEncoded();
	        strKey = new String(Base64.encode(raw));
	        
	        plainByte = fConvert.convertToByte(pathFile);

           
           // byte [] plainByte = fConvert.convertToByte("pic.jpg");
            
            byte[] encryptedByte = AES256Encryption.encryptAES256(strKey, strIv, plainByte);
            
            FileOutputStream fos = new FileOutputStream(encryptedFile);
			fos.write(encryptedByte);
			fos.close();
	           

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void encryptTripleDES(){
		 File encryptedFile = new File("file/"+lMain_Username.getText()+"/"+dateS+"/"+fileName);
		 encryptedFile.getParentFile().mkdirs();
		 TripleDESEncryption tDES = new TripleDESEncryption();
		 try{
			 String random = tDES.getSaltString();
			 SecretKey sk = tDES.generateTripleDESKey(random);
			 byte[] encryptSecKey = gk.encryptAESSecretKey(publicKeyUser, sk);
			 gk.writeToFile(pathKey, encryptSecKey);
			 plainByte = fConvert.convertToByte(pathFile);
			 byte[] encryptedByte = tDES.encrypt(plainByte, sk);
            FileOutputStream fos = new FileOutputStream(encryptedFile);
			fos.write(encryptedByte);
			fos.close();
			
			 
			 
		 }catch (Exception e){
			 e.printStackTrace();
		 }
	}
	
	public void encryptBlowFish(){
		String encryptedFile = "file/"+lMain_Username.getText()+"/"+dateS+"/"+fileName;
		BlowfishEncryption bf = new BlowfishEncryption();
		try {
			SecretKey skey = bf.getBlowfishKey();
			 byte[] encryptSecKey = gk.encryptAESSecretKey(publicKeyUser, skey);
			 gk.writeToFile(pathKey, encryptSecKey);
			 bf.encrypt(pathFile, encryptedFile, skey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	

}

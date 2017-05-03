package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.xml.bind.DatatypeConverter;

import controller.ConfigServer;
import controller.GenerateKeys;
import controller.HashPassword;
import controller.StaticRI;
import controller.UserController;
import modal.User;


import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.awt.event.ActionEvent;

public class GuiRegistration {

	public JFrame frame;
	private JTextField username_textField;
	private JTextField fullName_textField;
	private JTextField ic_textField;
	private JTextField phoneNo_textField;
	private JTextField email_textField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JTextField address1_textField;
	private JTextField address2_textField;
	private JTextField address3_textField;
	ConfigServer cs = new ConfigServer();
	String host = cs.host;
	String regName = cs.regName;
	String publicKey = "";
	boolean result = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiRegistration window = new GuiRegistration();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public boolean checkPassword()
	{
		boolean isTrue = false;
		char[] pass = passwordField.getPassword();
		char[] cPass = confirmPasswordField.getPassword();
		for (int i = 0;i < pass.length; i++){			
			if(Character.toString(pass[i]).equals(Character.toString(cPass[i]))){
				isTrue = true;
			}else{
				isTrue = false;
			}
		}
		return isTrue;
	}
	
	public void cleanField(){
		username_textField.setText("");
		passwordField.setText("");
		confirmPasswordField.setText("");
		fullName_textField.setText("");
		ic_textField.setText("");
		phoneNo_textField.setText("");
		email_textField.setText("");
		address1_textField.setText("");
		address2_textField.setText("");
		address3_textField.setText("");
	}
	
	public void generateKey(String username){
		//boolean state = false;
		GenerateKeys gk;
		User user = new User();
		try {
			gk = new GenerateKeys(1024);
			gk.createKeys();
			gk.writeToFile("key/"+username+"/KeyPair/publicKey", gk.getPublicKey().getEncoded());
			gk.writeToFile("key/"+username+"/KeyPair/privateKey", gk.getPrivateKey().getEncoded());
			//publicKey = gk.getPublicKey().toString();	
			publicKey = gk.getStringPublicKey(gk.getPublicKey());
			//state = true;
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		//return state;
	}

	/**
	 * Create the application.
	 */
	public GuiRegistration() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 681, 639);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblRegistration = new JLabel("Registration");
		lblRegistration.setFont(new Font("Candara", Font.BOLD, 28));
		lblRegistration.setBounds(253, 11, 171, 44);
		frame.getContentPane().add(lblRegistration);
		
		JLabel lblName = new JLabel("Full Name : ");
		lblName.setBounds(154, 190, 104, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblUsername = new JLabel("Username : ");
		lblUsername.setBounds(176, 102, 82, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password : ");
		lblPassword.setBounds(176, 127, 82, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("Confirm Password :");
		lblConfirmPassword.setBounds(154, 152, 130, 14);
		frame.getContentPane().add(lblConfirmPassword);
		
		username_textField = new JTextField();
		username_textField.setFont(new Font("Candara", Font.PLAIN, 12));
		username_textField.setBounds(280, 93, 300, 20);
		frame.getContentPane().add(username_textField);
		username_textField.setColumns(10);
		
		fullName_textField = new JTextField();
		fullName_textField.setBounds(280, 187, 300, 20);
		frame.getContentPane().add(fullName_textField);
		fullName_textField.setColumns(10);
		
		ic_textField = new JTextField();
		ic_textField.setBounds(280, 218, 300, 20);
		frame.getContentPane().add(ic_textField);
		ic_textField.setColumns(10);
		
		phoneNo_textField = new JTextField();
		phoneNo_textField.setBounds(280, 249, 300, 20);
		frame.getContentPane().add(phoneNo_textField);
		phoneNo_textField.setColumns(10);
		
		email_textField = new JTextField();
		email_textField.setText("");
		email_textField.setBounds(280, 280, 300, 20);
		frame.getContentPane().add(email_textField);
		email_textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(280, 124, 300, 20);
		frame.getContentPane().add(passwordField);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(280, 152, 300, 20);
		frame.getContentPane().add(confirmPasswordField);
		
		JLabel lblIc = new JLabel("IC :");
		lblIc.setBounds(164, 221, 64, 14);
		frame.getContentPane().add(lblIc);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number: ");
		lblPhoneNumber.setBounds(154, 252, 104, 14);
		frame.getContentPane().add(lblPhoneNumber);
		
		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setBounds(176, 283, 70, 14);
		frame.getContentPane().add(lblEmail);
		
		address1_textField = new JTextField();
		address1_textField.setBounds(280, 322, 300, 20);
		frame.getContentPane().add(address1_textField);
		address1_textField.setColumns(10);
		
		address2_textField = new JTextField();
		address2_textField.setBounds(280, 353, 300, 20);
		frame.getContentPane().add(address2_textField);
		address2_textField.setColumns(10);
		
		JLabel lblAddress = new JLabel("Address : ");
		lblAddress.setBounds(164, 325, 94, 14);
		frame.getContentPane().add(lblAddress);
		
		address3_textField = new JTextField();
		address3_textField.setColumns(10);
		address3_textField.setBounds(280, 384, 300, 20);
		frame.getContentPane().add(address3_textField);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				User user = new User();
				HashPassword hash = new HashPassword();
				String stringPass = null;
				String hashPass = null;
				
				if(checkPassword()){
						generateKey(username_textField.getText());
						char[] pass = passwordField.getPassword();
						
						for (int i = 0;i < pass.length; i++){			
							stringPass += Character.toString(pass[i]);
						}
						
						try {
							hashPass = hash.generateStorngPasswordHash(stringPass);
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidKeySpecException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						user.setUsername(username_textField.getText());
						user.setPassword(hashPass);
						user.setFname(fullName_textField.getText());
						user.setIcNo(ic_textField.getText());
						user.setPhoneNo(phoneNo_textField.getText());
						user.setAddress1(address1_textField.getText());
						user.setAddress2(address2_textField.getText());
						user.setAddress3(address3_textField.getText());
						user.setEmail(email_textField.getText());
						user.setPublicKey(publicKey);
						try
						{
							 Registry creg = LocateRegistry.getRegistry(host);
					         StaticRI cstub = (StaticRI)creg.lookup(regName);
					         result = cstub.registerUser(user);
					         //JOptionPane.showMessageDialog(null,result, "Result", JOptionPane.WARNING_MESSAGE);
					         if(result){
					        	 //JOptionPane.showMessageDialog(null,user.getPublicKey(), "Register", JOptionPane.WARNING_MESSAGE);
					        	 JOptionPane.showMessageDialog(null,"Successful register", "Register", JOptionPane.WARNING_MESSAGE);
					        	 cleanField();
					         } else {
					        	 JOptionPane.showMessageDialog(null,"Error on register", "Error", JOptionPane.WARNING_MESSAGE);
					         }

						}catch (Exception err)
						{
							JOptionPane.showMessageDialog(null,err,"Save Error",JOptionPane.ERROR_MESSAGE);
						}
					}
					

				
			}
		});
		btnRegister.setBounds(184, 456, 89, 23);
		frame.getContentPane().add(btnRegister);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				
			}
		});
		btnCancel.setBounds(283, 456, 89, 23);
		frame.getContentPane().add(btnCancel);
	}
}

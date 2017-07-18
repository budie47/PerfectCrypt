package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JEditorPane;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controller.ConfigServer;
import controller.StaticRI;
import modal.User;

import com.jgoodies.forms.layout.FormSpecs;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.xml.bind.DatatypeConverter;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridBagConstraints;
import net.miginfocom.swing.MigLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class GuiLogin {

	public JFrame frame;
	ConfigServer cs = new ConfigServer();
	String host = cs.host;
	String regName = cs.regName;
	
	
	/**
	 * @wbp.nonvisual location=-25,49
	 */
	private final Component horizontalGlue = Box.createHorizontalGlue();
	private JTextField tUsername;
	private JPasswordField tPassword;
	private String hashPassword = null;
	boolean result = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiLogin window = new GuiLogin();
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
	public GuiLogin() {
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 455, 231);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPerfectCrypt = new JLabel("Perfect Crypt");
		lblPerfectCrypt.setFont(new Font("Candara", Font.BOLD, 32));
		lblPerfectCrypt.setBounds(118, 21, 198, 32);
		frame.getContentPane().add(lblPerfectCrypt);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Candara", Font.BOLD, 22));
		lblLogin.setBounds(184, 53, 60, 32);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setFont(new Font("Candara", Font.PLAIN, 17));
		lblUsername.setBounds(62, 96, 82, 22);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password  :");
		lblPassword.setFont(new Font("Candara", Font.PLAIN, 17));
		lblPassword.setBounds(62, 130, 82, 22);
		frame.getContentPane().add(lblPassword);
		
		tUsername = new JTextField();
		tUsername.setFont(new Font("Candara", Font.PLAIN, 17));
		tUsername.setBounds(153, 96, 136, 22);
		frame.getContentPane().add(tUsername);
		tUsername.setColumns(10);
		
		
		JButton btnNewButton = new JButton("Log In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				GuiDashboard dash = new GuiDashboard();
				//getHashPassword();
				String stringPass = null;
				
				char[] pass = tPassword.getPassword();
				
				for (int i = 0;i < pass.length; i++){			
					stringPass += Character.toString(pass[i]);
				}
				user.setUsername(tUsername.getText());
				user.setPassword(stringPass); 
				try
				{
					System.setProperty("java.rmi.server.hostname",cs.host);
		            Registry creg = LocateRegistry.getRegistry(host,1099);
		            StaticRI cstub = (StaticRI)creg.lookup(regName);
		            result = cstub.loginUser(user);
		            if(result){
		            	//JOptionPane.showMessageDialog(null,"Login Success", "Login", JOptionPane.WARNING_MESSAGE);
		            	frame.dispose();
		            	
		            	dash.frame.setVisible(true);
		            	dash.lMain_Username.setText(tUsername.getText());
		            	dash.username = tUsername.getText();
		            	dash.getFriendList(tUsername.getText());
		            	dash.getFileList(tUsername.getText());
		            	
		            } else{
		            	JOptionPane.showMessageDialog(null,"Login Fail", "Login", JOptionPane.WARNING_MESSAGE);
		            }
					
					
				}catch(Exception err){
					 JOptionPane.showMessageDialog(null,err, "Connection with Server Error :", JOptionPane.WARNING_MESSAGE);
					 JOptionPane.showMessageDialog(null,cs.host, "Connection with Server Error :", JOptionPane.WARNING_MESSAGE);
					 
				}
			}
		});
		btnNewButton.setFont(new Font("Candara", Font.PLAIN, 17));
		btnNewButton.setBounds(299, 96, 89, 56);
		frame.getContentPane().add(btnNewButton);
		
		tPassword = new JPasswordField();
		tPassword.setBounds(154, 131, 136, 22);
		frame.getContentPane().add(tPassword);
		
		JButton btnRegister = new JButton("Register Here");
		btnRegister.setEnabled(false);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiRegistration register = new GuiRegistration();
				register.frame.setVisible(true);
			}
		});
		btnRegister.setBounds(153, 158, 136, 23);
		//frame.getContentPane().add(btnRegister);
	}
}

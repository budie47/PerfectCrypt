package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.crypto.SecretKey;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.TextArea;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ScrollPaneConstants;

import com.hazelcast.util.Base64;

import controller.BlowfishEncryption;
import controller.ChatClient;
import controller.ChatInterface;
import controller.ConfigServer;
import controller.GenerateKeys;
import controller.StaticRI;
import modal.Message;
import modal.User;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class GUIChatWindow {

	public JFrame frame;
	public JTextField tMessageField;
	public User friend;
	public JLabel lblShai;
	JLabel lblOnline ;
	
	public static boolean status = true;
	
	ConfigServer cs = new ConfigServer();
	String host = cs.host;
	String regName = cs.regName;
	
	public int sender_id;
	public int receiver_id;
	
	private TextArea textArea;
	private JButton btnStop;
	Timer timer;
	Timer chatTimer;
	GenerateKeys gk = new GenerateKeys();
	PublicKey publicKeyUser;
	String publicKeyUserString;
	
	String decryptPrivateKey;
	String encryptedPrivateKey;
	
	SecretKey secKey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIChatWindow window = new GUIChatWindow();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void startChat(String clientName, String clientPassword){
		
//		ConfigServer cs = new ConfigServer();
//		String host = cs.host;
//		String regName = "ChatServer";
//		
//		Registry creg = LocateRegistry.getRegistry(host);
//	
//		ChatInterface chatinterface = (ChatInterface)Naming.lookup("rmi://"+cs.host+":1110/ChatServer");
//		new Thread(new ChatClient(chatinterface , clientName , clientPassword)).start();
	}

	/**
	 * Create the application.
	 */
	public GUIChatWindow() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 538, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
		
		JLabel lblFriendName = new JLabel("Chatting With : ");
		lblFriendName.setBounds(10, 11, 106, 29);
		lblFriendName.setFont(new Font("Candara", Font.PLAIN, 14));
		frame.getContentPane().add(lblFriendName);
		
		lblShai = new JLabel("test");
		lblShai.setBounds(104, 11, 251, 29);
		lblShai.setFont(new Font("Candara", Font.PLAIN, 14));
		frame.getContentPane().add(lblShai);
		
		tMessageField = new JTextField();
		tMessageField.setBounds(10, 221, 324, 29);
		frame.getContentPane().add(tMessageField);
		tMessageField.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Registry creg = LocateRegistry.getRegistry(host);
					StaticRI cstub = (StaticRI)creg.lookup(regName);
					
					publicKeyUserString = cstub.getPublicKeyUserId(receiver_id);
					publicKeyUser = gk.getPublicKeyFromString(publicKeyUserString);
					
				Message msg = new Message();
				BlowfishEncryption bf = new BlowfishEncryption();
		        SecretKey skey = bf.getBlowfishKey();
		        byte[] encryptSecKey = gk.encryptAESSecretKey(publicKeyUser, skey);
		        String e_secretKey = new String(Base64.encode(encryptSecKey));
		        
		        String plainText = tMessageField.getText();
		        String cipherText = bf.encryptText(plainText, skey);
		        
				msg.setMessage(cipherText);
				msg.setReceiver_id(receiver_id);
				msg.setSender_id(sender_id);
				msg.setKey(e_secretKey);
				msg.setDigital_signature("-");



					boolean statsMessage = cstub.sendMessage(msg);
					if(statsMessage){
						//reloadChat(sender_id, receiver_id);
//						textArea.append(getCurrentDateTime() + " : " + sender_id +" : \n");
//						textArea.append(tMessageField.getText() + " \n ");
						tMessageField.setText("");
						
						
					} else{
						//JOptionPane.showMessageDialog(null, "|-FAIL-|");
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setBounds(335, 221, 89, 29);
		frame.getContentPane().add(btnNewButton);
		
		lblOnline = new JLabel("-");
		lblOnline.setFont(new Font("Candara", Font.PLAIN, 14));
		lblOnline.setBounds(365, 11, 59, 29);
		frame.getContentPane().add(lblOnline);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 38, 414, 172);
		frame.getContentPane().add(scrollPane);
		
		textArea = new TextArea();
		textArea.setEditable(false);
		textArea.setBackground(Color.WHITE);
		scrollPane.setViewportView(textArea);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

//				int initialDelay = 1000;
//				int period = 1000;
//				{
//					timer = new Timer();
//					TimerTask task = new TimerTask(){
//						public void run(){
//							reloadChat(sender_id, receiver_id);
//						}
//					};
//					timer.scheduleAtFixedRate(task, initialDelay, period);
//				}

			
			}
		});
		btnNewButton_1.setBounds(434, 66, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopStatusTread();
			}
		});
		btnStop.setBounds(434, 115, 89, 23);
		frame.getContentPane().add(btnStop);
		
	}
	
	public void loadMessage(int senderID,int receiverID){
		

		Registry creg;
		try {
			creg = LocateRegistry.getRegistry(host);
			StaticRI cstub = (StaticRI)creg.lookup(regName);
			Vector<Message> messages = cstub.getMessage(senderID, receiverID);
			for(int i = 1; i<messages.size(); i++){
				textArea.append(messages.get(i).getDate_time() + " : " + messages.get(i).getSender_name() +" : \n");
				textArea.append(messages.get(i).getMessage() + " \n ");
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String getCurrentDateTime(){
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String currentDateTime = dateFormat.format(date);
		return currentDateTime;
	}
	
	public void reloadChat(int senderID,int receiverID){
		//while(status){
			textArea.setText("");
			Registry creg;
			try {


				
				creg = LocateRegistry.getRegistry(host);
				StaticRI cstub = (StaticRI)creg.lookup(regName);
				Vector<Message> messages = cstub.getMessageThread(senderID, receiverID);
				for(int i = 1; i<messages.size(); i++){
				
					textArea.append(messages.get(i).getDate_time() + " : " + messages.get(i).getSender_name() +" : \n");
					textArea.append(messages.get(i).getMessage() + " \n ");
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//}
	}
	public void retrieveNewChat(int senderID,int receiverID){
		//while(status){
			
			Registry creg;
			try {
				PrivateKey privateKey  = getPrivateKey(decryptPrivateKey);
				BlowfishEncryption bf = new BlowfishEncryption();
				
				creg = LocateRegistry.getRegistry(host);
				StaticRI cstub = (StaticRI)creg.lookup(regName);
				Vector<Message> messages = cstub.getMessageNewMessage(senderID, receiverID);
				
				for(int i = 0; i<messages.size(); i++){
					secKey =  gk.decryptAESSecretKey(messages.get(i).getKey().getBytes(), privateKey,"Blowfish");
					String message = bf.decryptText(messages.get(i).getMessage(), secKey);
					textArea.append(messages.get(i).getDate_time() + " : [ " + messages.get(i).getSender_name() +" ] : \n");
					textArea.append(message + " \n ");

				}
				
				for(int i = 0; i<messages.size(); i++){
					cstub.changeShowStatus(messages.get(i).getMessage_id(), 2);
					//System.out.println(messages.get(i).getMessage_id());
				}
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//}
	}
	

	public void runStatusTread(){
		int initialDelay = 3000;
		int period = 3000;
		{
			timer = new Timer();
			TimerTask task = new TimerTask(){
				public void run(){
					Registry creg;
					try {
						creg = LocateRegistry.getRegistry(host);
						StaticRI cstub = (StaticRI)creg.lookup(regName);
						String friendStatus = cstub.getUserStatus(friend.getUsername());
						lblOnline.setText(friendStatus);
						//reloadChat(sender_id, receiver_id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			};
			timer.scheduleAtFixedRate(task, initialDelay, period);
		}
	}
	
	public void chatThread(){
		int initialDelay = 100;
		int period = 100;
		{
			chatTimer = new Timer();
			TimerTask task =  new TimerTask() {
				
				public void run() {
					try{
						Registry creg = LocateRegistry.getRegistry(host);
						StaticRI cstub = (StaticRI)creg.lookup(regName);
						
						System.out.println(cstub.checkNewChat(sender_id, receiver_id));
						
						if(cstub.checkNewChat(sender_id, receiver_id)){
							retrieveNewChat(sender_id, receiver_id);
							
						}
					}catch(Exception e){
						
					}
					// TODO Auto-generated method stub

					
				}
			};
			chatTimer.scheduleAtFixedRate(task, initialDelay, period);
		}
	}
	
	public void stopStatusTread(){
		timer.cancel();
		timer.purge();
		chatTimer.purge();
		chatTimer.cancel();
	}
	
	public PrivateKey getPrivateKey(String decryptedPrivateKey) throws Exception{
		
		byte[] epkey = Base64.decode(decryptedPrivateKey.getBytes());

		//byte[] pkey = decryptedPrivateKey.getBytes();
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(epkey));
		return privateKey;
	}
	


}

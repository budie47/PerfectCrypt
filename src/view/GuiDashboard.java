package view;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Menu;
import java.awt.Window;

import javax.swing.JFrame;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.crypto.BadPaddingException;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.UIManager;

import controller.AES256Encryption;
import controller.ChatUI;
import controller.ConfigServer;
import controller.StaticRI;
import controller.UserController;
import modal.FileModal;
import modal.FileUser;
import modal.User;

import java.awt.Choice;
import javax.swing.JTextArea;
import java.awt.Panel;
import java.awt.BorderLayout;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class GuiDashboard {

	public static JFrame frame;
	private JPanel menu ;
	private JPanel encryptPanel;
	private JPanel receivePanel;
	private JTextField txtPleaseClickGenerate;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	public JLabel lMain_Username;
	private JList listFiles;
	public static String username;
	Vector<User> listFriend = null;
	Vector<FileUser> listFile = null;
	ConfigServer cs = new ConfigServer();
	String host = cs.host;
	String regName = cs.regName;
	String chatHost = cs.chatHost;
	JList listFriends;
	String decryptPrivateKey;
	String encryptedPrivateKey = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiDashboard window = new GuiDashboard();
					
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
	public GuiDashboard() {
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
				friendListModel.addElement(friend.getFname());
				
			}
			listFriends.setModel(friendListModel);
			listFriends.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					JPasswordField pf = new JPasswordField();
					int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

					if (okCxl == JOptionPane.OK_OPTION) {
						try{
							AES256Encryption aes = new AES256Encryption();
							UserController uc = new UserController();
							String stringPass = "null";
							
							char[] pass = pf.getPassword();
							
							for (int i = 0;i < pass.length; i++){			
				
								stringPass += Character.toString(pass[i]);
							}
							
							 Registry creg = LocateRegistry.getRegistry(host,1099);
					         StaticRI cstub = (StaticRI)creg.lookup(regName);
					         encryptedPrivateKey = cstub.getUserEncryptedPrivateKey(username);
					         try{
					        	 decryptPrivateKey = uc.decryptPrivateKey(encryptedPrivateKey,stringPass);
					        	 int index = listFriends.getSelectedIndex();
									String friendUsername = listFriend.get(index).getUsername();
									User friend  = listFriend.get(index);  
									
									String friendStatus = cstub.getUserStatus(friend.getUsername());
									
									GUIChatWindow guiChat = new GUIChatWindow();
									guiChat.frame.setVisible(true);
									guiChat.lblShai.setText(friend.getFname());
									guiChat.friend = friend;
									guiChat.lblOnline.setText(friendStatus);
									
									guiChat.encryptedPrivateKey = encryptedPrivateKey;
									guiChat.decryptPrivateKey = decryptPrivateKey;
									
									int receiver_id = cstub.getUserId(friend.getUsername());
									int sender_id = cstub.getUserId(lMain_Username.getText());
									
									guiChat.receiver_id = receiver_id;
									guiChat.sender_id = sender_id;
									guiChat.loadMessage(sender_id, receiver_id);
									guiChat.runStatusTread();
									guiChat.chatThread();
									guiChat.frame.addWindowListener(new WindowAdapter(){
										public void windowClosing(WindowEvent e){
											try {
												guiChat.stopStatusTread();
												cstub.closeChatWindows(sender_id, receiver_id);
												System.out.println("windows close");
												
											} catch (RemoteException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
										}
									});
					         }catch(BadPaddingException errPass){
					        	 JOptionPane.showMessageDialog(null, "Your Password were wrong");
					         }
					         
					         
								
					            
					            
						}catch(Exception e23){
							e23.printStackTrace();

						} 
						

					}
//					int index = listFriends.getSelectedIndex();
//					String friendUsername = listFriend.get(index).getUsername();
//					User friend  = listFriend.get(index);  
					

//					try {
//						String friendStatus = cstub.getUserStatus(friend.getUsername());
//						
//						GUIChatWindow guiChat = new GUIChatWindow();
//						guiChat.frame.setVisible(true);
//						guiChat.lblShai.setText(friend.getFname());
//						guiChat.friend = friend;
//						guiChat.lblOnline.setText(friendStatus);
//						
//						
//						
//						
//						int receiver_id = cstub.getUserId(friend.getUsername());
//						int sender_id = cstub.getUserId(lMain_Username.getText());
//						
//						guiChat.receiver_id = receiver_id;
//						guiChat.sender_id = sender_id;
//						guiChat.loadMessage(sender_id, receiver_id);
//						guiChat.runStatusTread();
//						guiChat.chatThread();
//						guiChat.frame.addWindowListener(new WindowAdapter(){
//							public void windowClosing(WindowEvent e){
//								try {
//									guiChat.stopStatusTread();
//									cstub.closeChatWindows(sender_id, receiver_id);
//									System.out.println("windows close");
//									
//								} catch (RemoteException e1) {
//									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								}
//								
//							}
//						});
//						
//						
//					} catch (RemoteException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					
				}
			});
			
		}catch (Exception err){
			err.printStackTrace();
		}
	}
	
	public void getFileList(String name){
		try{
			test();
			name = lMain_Username.getText();
			Registry creg = LocateRegistry.getRegistry(host);
			StaticRI cstub = (StaticRI)creg.lookup(regName);
			listFile = cstub.getFileUser(name);
			//System.out.println(listFile.toString());
			DefaultListModel fileListModel = new DefaultListModel();
			for(FileUser fileU : listFile){

				System.out.println(fileU.getFilePath());
				fileListModel.addElement(fileU.getFilePath());
			}
			listFiles.setModel(fileListModel);
			listFiles.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					//JOptionPane.showMessageDialog(null,listFiles.getSelectedValue() + " " + listFiles.getSelectedIndex(), "Sending", JOptionPane.WARNING_MESSAGE);
					GuiFileInfoWIndows fi = new GuiFileInfoWIndows();
					fi.username = lMain_Username.getText();
					fi.fileName = listFiles.getSelectedValue().toString();
					fi.frame.setVisible(true);
					fi.lblDownloadFileName.setText(listFiles.getSelectedValue().toString());
					try {
						String method = cstub.getMethodCrypto(listFiles.getSelectedValue().toString(),username);
						fi.lMethod.setText(method);						
						//System.out.println(method);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	
				}
			});
			
		}catch(Exception err){
			err.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 692, 494);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		menu = new JPanel();
		menu.setBackground(SystemColor.control);
		frame.getContentPane().add(menu, "name_33574431698208");
		menu.setLayout(null);
		menu.setVisible(true);
		
		JButton btnSend = new JButton("Send File");
		btnSend.setForeground(Color.BLACK);
		btnSend.setBackground(Color.LIGHT_GRAY);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuSendFiles sendFile = new GuSendFiles();
				sendFile.frame.setVisible(true);
				sendFile.lMain_Username.setText(lMain_Username.getText());
				sendFile.getFriendList(lMain_Username.getText());
				
				//menu.setVisible(false);
				//encryptPanel.setVisible(true);
				
				
			}
		});
		btnSend.setBounds(387, 366, 84, 35);
		menu.add(btnSend);
		Dimension size = btnSend.getSize();

		JButton btnReceive = new JButton("Directory");
		btnReceive.setForeground(Color.BLACK);
		btnReceive.setBackground(Color.LIGHT_GRAY);
		btnReceive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				menu.setVisible(false);
//				receivePanel.setVisible(true);
				try {
					Desktop.getDesktop().open(new File("download/plain/"+lMain_Username.getText()+"/"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnReceive.setBounds(481, 366, 86, 35);
		Dimension size1 = btnReceive.getSize();
		
		menu.add(btnReceive);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 676, 70);
		menu.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Username :");
		label.setFont(new Font("Candara", Font.PLAIN, 16));
		label.setBounds(408, 43, 79, 27);
		panel.add(label);
		
		JLabel lblPerfectCrypt = new JLabel("Perfect Crypt");
		lblPerfectCrypt.setFont(new Font("Candara", Font.BOLD, 45));
		lblPerfectCrypt.setBounds(22, 6, 287, 56);
		panel.add(lblPerfectCrypt);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiLogin login = new GuiLogin();
				Registry creg;
				try {
					creg = LocateRegistry.getRegistry(host);
					StaticRI cstub = (StaticRI)creg.lookup(regName);
					boolean state = cstub.logOutUser(lMain_Username.getText());
					if(state){
						JOptionPane.showMessageDialog(null, "Success Log Out");
						login.frame.setVisible(true);
						frame.dispose();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				
			}
		});
		btnLogOut.setBounds(580, 4, 90, 28);
		panel.add(btnLogOut);
		
		lMain_Username = new JLabel("");
		lMain_Username.setFont(new Font("Candara", Font.PLAIN, 16));
		lMain_Username.setBounds(492, 43, 178, 27);
		panel.add(lMain_Username);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBounds(10, 82, 365, 345);
		menu.add(panel_14);
		panel_14.setLayout(null);
		
		JLabel lblYourFiles = new JLabel("Your Files ");
		lblYourFiles.setBounds(6, 15, 103, 16);
		panel_14.add(lblYourFiles);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 42, 349, 292);
		panel_14.add(scrollPane);
		
		listFiles = new JList();
		scrollPane.setViewportView(listFiles);
		
		
		JPanel panel_15 = new JPanel();
		panel_15.setBounds(387, 82, 283, 279);
		menu.add(panel_15);
		panel_15.setLayout(null);
		
		JButton btnAddFriend = new JButton("Add Friend");
		btnAddFriend.setBounds(187, 0, 90, 28);
		panel_15.add(btnAddFriend);
		
		JLabel lblFriendList = new JLabel("Friend List");
		lblFriendList.setBounds(16, 12, 103, 16);
		panel_15.add(lblFriendList);
		
		listFriends = new JList();
		listFriends.setBounds(6, 66, 271, 207);
		panel_15.add(listFriends);

		JButton btnNewButton_3 = new JButton("Refresh");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					Registry creg = LocateRegistry.getRegistry(host);
					StaticRI cstub = (StaticRI)creg.lookup(regName);
					listFriend = cstub.getCurrentFriend(lMain_Username.getText());
					DefaultListModel friendListModel = new DefaultListModel();
					for(User friend : listFriend){
						friendListModel.addElement(friend.getFname());
					}
					listFriends.setModel(friendListModel);
					
				}catch (Exception err){
					err.printStackTrace();
				}
			}
		});
		btnNewButton_3.setBounds(187, 30, 90, 28);
		panel_15.add(btnNewButton_3);
		
		JButton btnEncrypted = new JButton("Encrypted");
		btnEncrypted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().open(new File("download/encrypted/"+lMain_Username.getText()+"/"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEncrypted.setForeground(Color.BLACK);
		btnEncrypted.setBackground(Color.LIGHT_GRAY);
		btnEncrypted.setBounds(577, 366, 89, 35);
		menu.add(btnEncrypted);
		
		JButton btnChat = new JButton("Chat");
		btnChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ChatUI cUI=new ChatUI();
				cUI.main(null);
//				cUI.doConnect();
//				cUI.showGUIChat();
				
//				GUIChat chat = new GUIChat();
//				User chatUser = new User();
//				chatUser.setUsername(username);
//				
//				chat.showGUIChat(chatUser);
//				chat.doConnectChatServer(username, chatHost);
			}
		});
		btnChat.setForeground(Color.BLACK);
		btnChat.setBackground(Color.LIGHT_GRAY);
		btnChat.setBounds(536, 412, 86, 35);
		menu.add(btnChat);
		
		JButton btnMail = new JButton("Mail");
		btnMail.setForeground(Color.BLACK);
		btnMail.setBackground(Color.LIGHT_GRAY);
		btnMail.setBounds(431, 412, 86, 35);
		menu.add(btnMail);
		
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiAddFriend wAddFriend = new GuiAddFriend();
				wAddFriend.lAF_username.setText(lMain_Username.getText());
				wAddFriend.frameAddFriend.setVisible(true);
				frame.setVisible(false);
			}
		});
		
		encryptPanel = new JPanel();
		encryptPanel.setBackground(SystemColor.control);
		encryptPanel.setVisible(false);
		frame.getContentPane().add(encryptPanel, "name_33577030653414");
		encryptPanel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(0, 0, 676, 70);
		encryptPanel.add(panel_1);
		
		JButton btnBackSend = new JButton("");
		btnBackSend.setForeground(Color.LIGHT_GRAY);
		btnBackSend.setBackground(Color.LIGHT_GRAY);
	

		btnBackSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				encryptPanel.setVisible(false);
				menu.setVisible(true);
			}
		});
		btnBackSend.setBounds(0, 0, 72, 70);
		size = btnBackSend.getSize();
		//btnBack.setIcon(new ImageIcon(GuiDashboard.class.getResource("/resources/back.png")).getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH));
		btnBackSend.setIcon(new ImageIcon(new ImageIcon(GuiDashboard.class.getResource("/resources/back.png")).getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH)));
		panel_1.add(btnBackSend);
		
		JLabel lblSend = new JLabel("Encrypt");
		lblSend.setFont(new Font("Candara", Font.BOLD, 22));
		lblSend.setBounds(84, 32, 91, 31);
		panel_1.add(lblSend);
		
		JLabel label_6 = new JLabel("Perfect Crypt");
		label_6.setFont(new Font("Candara", Font.BOLD | Font.ITALIC, 25));
		label_6.setBounds(82, 6, 153, 31);
		panel_1.add(label_6);
		
		JLabel label_1 = new JLabel("Username :");
		label_1.setFont(new Font("Candara", Font.PLAIN, 16));
		label_1.setBounds(467, 19, 79, 27);
		panel_1.add(label_1);
		final JFileChooser fc = new JFileChooser();
		
		JButton btnGenerateKeyStream = new JButton("Generate");
		btnGenerateKeyStream.setForeground(new Color(255, 255, 255));
		btnGenerateKeyStream.setBackground(new Color(0, 153, 102));
		btnGenerateKeyStream.setFont(new Font("Candara", Font.BOLD, 16));
		btnGenerateKeyStream.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setEnabled(false);
				GenerateKeyStream genKeyStream = new GenerateKeyStream();
				genKeyStream.setEnabled(true);
				genKeyStream.setVisible(true);
			}
		});
		btnGenerateKeyStream.setBounds(26, 160, 231, 34);
		encryptPanel.add(btnGenerateKeyStream);
		
		JLabel lblGenerate = new JLabel("Generate Key Stream");
		lblGenerate.setFont(new Font("Candara", Font.BOLD, 17));
		lblGenerate.setBounds(26, 95, 191, 23);
		encryptPanel.add(lblGenerate);
		
		txtPleaseClickGenerate = new JTextField();
		txtPleaseClickGenerate.setText("Please Click Generate");
		txtPleaseClickGenerate.setFont(new Font("Candara", Font.PLAIN, 15));
		txtPleaseClickGenerate.setBounds(26, 118, 231, 33);
		encryptPanel.add(txtPleaseClickGenerate);
		txtPleaseClickGenerate.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		panel_3.setBounds(10, 81, 262, 143);
		encryptPanel.add(panel_3);
		panel_3.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.LIGHT_GRAY);
		panel_4.setBounds(10, 235, 262, 87);
		encryptPanel.add(panel_4);
		panel_4.setLayout(null);
		
		JLabel label_3 = new JLabel("Key Exchange Method");
		label_3.setBounds(20, 11, 191, 23);
		panel_4.add(label_3);
		label_3.setFont(new Font("Candara", Font.BOLD, 17));
		
		Choice choice = new Choice();
		choice.setFont(new Font("Candara", Font.PLAIN, 15));
		choice.setBackground(Color.WHITE);
		choice.setBounds(20, 40, 221, 25);
		panel_4.add(choice);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.LIGHT_GRAY);
		panel_5.setBounds(10, 333, 262, 87);
		encryptPanel.add(panel_5);
		panel_5.setLayout(null);
		
		JLabel label_5 = new JLabel("Encryption Method");
		label_5.setBounds(22, 11, 191, 23);
		panel_5.add(label_5);
		label_5.setFont(new Font("Candara", Font.BOLD, 17));
		
		Choice choice_1 = new Choice();
		choice_1.setFont(new Font("Candara", Font.PLAIN, 15));
		choice_1.setBackground(Color.WHITE);
		choice_1.setBounds(22, 40, 219, 25);
		panel_5.add(choice_1);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.LIGHT_GRAY);
		panel_6.setBounds(294, 140, 372, 216);
		encryptPanel.add(panel_6);
		panel_6.setLayout(new CardLayout(0, 0));
		
		JPanel text = new JPanel();
		text.setBackground(Color.LIGHT_GRAY);
		panel_6.add(text, "name_106101309708725");
		text.setLayout(null);
		
		JTextArea txtrAdas = new JTextArea();
		txtrAdas.setText("Write your text here");
		txtrAdas.setBounds(10, 32, 352, 173);
		text.add(txtrAdas);
		
		JLabel lblText = new JLabel("Text");
		lblText.setFont(new Font("Candara", Font.BOLD, 17));
		lblText.setBounds(160, 11, 50, 23);
		text.add(lblText);
		
		JPanel file = new JPanel();
		panel_6.add(file, "name_106126135464582");
		file.setLayout(null);
		
		Panel panel_7 = new Panel();
		panel_7.setBackground(Color.LIGHT_GRAY);
		panel_7.setBounds(0, 0, 372, 152);
		file.add(panel_7);
		panel_7.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 64, 352, 34);
		panel_7.add(textField);
		textField.setColumns(10);
		
		JButton btnFileChooser = new JButton("Select File");
		btnFileChooser.setBounds(10, 109, 352, 32);
		panel_7.add(btnFileChooser);
		btnFileChooser.setForeground(new Color(255, 255, 255));
		btnFileChooser.setBackground(new Color(0, 153, 102));
		btnFileChooser.setFont(new Font("Candara", Font.BOLD, 16));
		
		JLabel label_8 = new JLabel("Select File from Your PC");
		label_8.setFont(new Font("Candara", Font.BOLD, 17));
		label_8.setBounds(95, 11, 191, 23);
		panel_7.add(label_8);
		
		JLabel label_9 = new JLabel("File Path");
		label_9.setFont(new Font("Candara", Font.ITALIC, 14));
		label_9.setBounds(155, 45, 71, 23);
		panel_7.add(label_9);
		btnFileChooser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 fc.setCurrentDirectory(new java.io.File("user.home"));
	                fc.setDialogTitle("This is a JFileChooser");
	                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	                if (fc.showOpenDialog(btnFileChooser) == JFileChooser.APPROVE_OPTION) {
	                    JOptionPane.showMessageDialog(null, fc.getSelectedFile().getAbsolutePath());
	                }
			}
		});
		
		Panel panel_9 = new Panel();
		panel_9.setBackground(Color.LIGHT_GRAY);
		panel_9.setBounds(294, 83, 372, 51);
		encryptPanel.add(panel_9);
		panel_9.setLayout(null);
		
		JLabel label_7 = new JLabel("Type Data");
		label_7.setBounds(38, 11, 88, 23);
		panel_9.add(label_7);
		label_7.setFont(new Font("Candara", Font.BOLD, 17));
		
		Choice choice_2 = new Choice();
		choice_2.setFont(new Font("Candara", Font.PLAIN, 15));
		choice_2.setBackground(Color.WHITE);
		choice_2.setBounds(132, 9, 219, 25);
		panel_9.add(choice_2);
		
		JButton btnNewButton = new JButton("Encrypt");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiEncrypting encrypt = new GuiEncrypting();
				frame.setVisible(false);
				encryptPanel.setEnabled(false);
				encrypt.setVisible(true);
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Candara", Font.BOLD, 32));
		btnNewButton.setBackground(new Color(0, 102, 153));
		btnNewButton.setBounds(294, 362, 372, 58);
		encryptPanel.add(btnNewButton);
		
		receivePanel = new JPanel();
		receivePanel.setBackground(SystemColor.control);
		receivePanel.setVisible(false);
		frame.getContentPane().add(receivePanel, "name_33580708199862");
		receivePanel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(0, 0, 676, 70);
		receivePanel.add(panel_2);
		
		JButton btnBackReceive = new JButton("");
		 
		btnBackReceive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.setVisible(true);
				receivePanel.setVisible(false);
			}
		});
		btnBackReceive.setForeground(Color.LIGHT_GRAY);
		btnBackReceive.setBackground(Color.LIGHT_GRAY);
		
		btnBackReceive.setIcon(new ImageIcon(new ImageIcon(GuiDashboard.class.getResource("/resources/back.png")).getImage().getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH)));
		btnBackReceive.setBounds(0, 0, 72, 70);
		size = btnBackReceive.getSize();
		panel_2.add(btnBackReceive);
		
		JLabel label_2 = new JLabel("Perfect Crypt");
		label_2.setFont(new Font("Candara", Font.BOLD | Font.ITALIC, 25));
		label_2.setBounds(82, 5, 153, 31);
		panel_2.add(label_2);
		
		JLabel lblReceive = new JLabel("Receive");
		lblReceive.setFont(new Font("Candara", Font.BOLD, 22));
		lblReceive.setBounds(84, 31, 79, 31);
		panel_2.add(lblReceive);
		
		JLabel label_4 = new JLabel("Username :");
		label_4.setFont(new Font("Candara", Font.PLAIN, 16));
		label_4.setBounds(467, 18, 79, 27);
		panel_2.add(label_4);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(Color.LIGHT_GRAY);
		panel_8.setBounds(10, 81, 656, 357);
		receivePanel.add(panel_8);
		panel_8.setLayout(null);
		
		JLabel lblConnecting = new JLabel("Listening...");
		lblConnecting.setBounds(10, 35, 110, 24);
		panel_8.add(lblConnecting);
		lblConnecting.setFont(new Font("Candara", Font.BOLD, 16));
		
		JLabel label_10 = new JLabel("Status");
		label_10.setFont(new Font("Candara", Font.BOLD, 19));
		label_10.setBounds(10, 11, 110, 24);
		panel_8.add(label_10);
		
		JTextArea txtrLog = new JTextArea();
		txtrLog.setText("log");
		txtrLog.setBounds(10, 60, 636, 286);
		panel_8.add(txtrLog);
		
		JButton btnNewButton_1 = new JButton("test");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GuiTransferProgress trWin = new GuiTransferProgress();
				menu.setEnabled(false);
				trWin.setEnabled(true);
				trWin.setVisible(true);
				
			}
		});
		btnNewButton_1.setBounds(557, 12, 89, 23);
		panel_8.add(btnNewButton_1);
		
		JPanel sendPanel = new JPanel();
		frame.getContentPane().add(sendPanel, "name_115918891618860");
		sendPanel.setLayout(null);
		
		JPanel panel_10 = new JPanel();
		panel_10.setLayout(null);
		panel_10.setBackground(Color.LIGHT_GRAY);
		panel_10.setBounds(0, 0, 676, 70);
		sendPanel.add(panel_10);
		
		JButton btnBackSendPanel = new JButton("");
		btnBackSendPanel.setForeground(Color.LIGHT_GRAY);
		btnBackSendPanel.setBackground(Color.LIGHT_GRAY);
		btnBackSendPanel.setBounds(0, 0, 72, 70);
		Dimension size3 = btnBackSendPanel.getSize();
		btnBackSendPanel.setIcon(new ImageIcon(new ImageIcon(GuiDashboard.class.getResource("/resources/back.png")).getImage().getScaledInstance(size3.width, size3.height, Image.SCALE_SMOOTH)));
		panel_10.add(btnBackSendPanel);
		
		JLabel label_11 = new JLabel("Send");
		label_11.setFont(new Font("Candara", Font.BOLD, 22));
		label_11.setBounds(84, 32, 58, 31);
		panel_10.add(label_11);
		
		JLabel label_12 = new JLabel("Perfect Crypt");
		label_12.setFont(new Font("Candara", Font.BOLD | Font.ITALIC, 25));
		label_12.setBounds(82, 6, 153, 31);
		panel_10.add(label_12);
		
		JLabel label_13 = new JLabel("Username :");
		label_13.setFont(new Font("Candara", Font.PLAIN, 16));
		label_13.setBounds(467, 19, 79, 27);
		panel_10.add(label_13);
		
		JPanel decrypt = new JPanel();
		frame.getContentPane().add(decrypt, "name_122424258762911");
		decrypt.setLayout(null);
		
		JPanel panel_11 = new JPanel();
		panel_11.setLayout(null);
		panel_11.setBackground(Color.LIGHT_GRAY);
		panel_11.setBounds(0, 0, 676, 70);
		decrypt.add(panel_11);
		
		JButton btnBackDecrypt = new JButton("");
		btnBackDecrypt.setForeground(Color.LIGHT_GRAY);
		btnBackDecrypt.setBackground(Color.LIGHT_GRAY);
		btnBackDecrypt.setBounds(0, 0, 72, 70);
		Dimension size5 = btnBackDecrypt.getSize();
		btnBackDecrypt.setIcon(new ImageIcon(new ImageIcon(GuiDashboard.class.getResource("/resources/back.png")).getImage().getScaledInstance(size5.width, size5.height, Image.SCALE_SMOOTH)));
		
		panel_11.add(btnBackDecrypt);
		
		JLabel lblDecrypt = new JLabel("Decrypt");
		lblDecrypt.setFont(new Font("Candara", Font.BOLD, 22));
		lblDecrypt.setBounds(84, 32, 91, 31);
		panel_11.add(lblDecrypt);
		
		JLabel label_15 = new JLabel("Perfect Crypt");
		label_15.setFont(new Font("Candara", Font.BOLD | Font.ITALIC, 25));
		label_15.setBounds(82, 6, 153, 31);
		panel_11.add(label_15);
		
		JLabel label_16 = new JLabel("Username :");
		label_16.setFont(new Font("Candara", Font.PLAIN, 16));
		label_16.setBounds(467, 19, 79, 27);
		panel_11.add(label_16);
		
		JPanel panel_12 = new JPanel();
		panel_12.setBackground(Color.LIGHT_GRAY);
		panel_12.setBounds(10, 81, 321, 357);
		decrypt.add(panel_12);
		panel_12.setLayout(null);
		
		JLabel lblEncryptionAlgorithm = new JLabel("From");
		lblEncryptionAlgorithm.setFont(new Font("Candara", Font.BOLD, 16));
		lblEncryptionAlgorithm.setBounds(10, 11, 160, 26);
		panel_12.add(lblEncryptionAlgorithm);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(10, 38, 302, 31);
		panel_12.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(10, 122, 302, 31);
		panel_12.add(textField_2);
		
		JLabel lblEncryptionAlgorithm_1 = new JLabel("Encryption Algorithm");
		lblEncryptionAlgorithm_1.setFont(new Font("Candara", Font.BOLD, 16));
		lblEncryptionAlgorithm_1.setBounds(10, 95, 160, 26);
		panel_12.add(lblEncryptionAlgorithm_1);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(10, 206, 302, 31);
		panel_12.add(textField_3);
		
		JLabel lblDataType = new JLabel("Data Type");
		lblDataType.setFont(new Font("Candara", Font.BOLD, 16));
		lblDataType.setBounds(10, 179, 160, 26);
		panel_12.add(lblDataType);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBounds(10, 293, 302, 31);
		panel_12.add(textField_4);
		
		JLabel lblDataPath = new JLabel("Data Path");
		lblDataPath.setFont(new Font("Candara", Font.BOLD, 16));
		lblDataPath.setBounds(10, 266, 160, 26);
		panel_12.add(lblDataPath);
		
		JPanel panel_13 = new JPanel();
		panel_13.setLayout(null);
		panel_13.setBackground(Color.LIGHT_GRAY);
		panel_13.setBounds(341, 81, 321, 276);
		decrypt.add(panel_13);
		
		JTextArea txtrExampleCipherText = new JTextArea();
		txtrExampleCipherText.setText("Example cipher text");
		txtrExampleCipherText.setBounds(10, 44, 301, 221);
		panel_13.add(txtrExampleCipherText);
		
		JLabel lblCipherText = new JLabel("Cipher Text");
		lblCipherText.setFont(new Font("Candara", Font.BOLD, 16));
		lblCipherText.setBounds(10, 11, 160, 26);
		panel_13.add(lblCipherText);
		
		JButton btnNewButton_2 = new JButton("Decrypt");
		btnNewButton_2.setFont(new Font("Candara", Font.BOLD, 39));
		btnNewButton_2.setBackground(new Color(0, 102, 153));
		btnNewButton_2.setForeground(new Color(255, 255, 255));
		btnNewButton_2.setBounds(341, 368, 321, 70);
		decrypt.add(btnNewButton_2);
		
	}
	
	public void test(){
		  Registry registry;
		try {
			registry = LocateRegistry.getRegistry(host, 1099);
			final String[] boundNames = registry.list();
	         System.out.println(
	            "Names bound to RMI registry at host " + host + " and port " + 1099 + ":");
	         for (final String name : boundNames)
	         {
	            System.out.println("\t" + name);
	         }
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	         
	}
}

package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.ConfigServer;
import controller.StaticRI;
import modal.User;
import view.GuiDashboard;

import javax.swing.JPanel;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.List;
import java.awt.Panel;

public class GuiAddFriend {

	public JFrame frameAddFriend;
	public JLabel lAF_username;
	private JTextField tNameField;
	JLabel lUsername;
	JLabel lFullname;
	ConfigServer cs = new ConfigServer();
	String host = cs.host;
	String regName = cs.regName;
	Vector<User> resultUser = null;
	boolean resultAddFriend = false;
	private User user;
	//boolean result = false;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiAddFriend window = new GuiAddFriend();
					window.frameAddFriend.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiAddFriend() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameAddFriend = new JFrame();
		frameAddFriend.setBounds(100, 100, 631, 513);
		frameAddFriend.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameAddFriend.getContentPane().setLayout(null);
		
		JLabel lblAddFriends = new JLabel("Add Friends");
		lblAddFriends.setFont(new Font("Candara", Font.PLAIN, 56));
		lblAddFriends.setBounds(10, 11, 343, 56);
		frameAddFriend.getContentPane().add(lblAddFriends);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 67, 595, 60);
		frameAddFriend.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblSearchFriendName = new JLabel("Search Friend Name :");
		lblSearchFriendName.setBounds(52, 20, 110, 14);
		panel.add(lblSearchFriendName);
		
		tNameField = new JTextField();
		tNameField.setBounds(172, 13, 246, 29);
		panel.add(tNameField);
		tNameField.setColumns(10);
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 154, 305, 309);
		frameAddFriend.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JList list = new JList();
		list.setBounds(10, 11, 285, 287);
		panel_1.add(list);
		
		JButton btnSearchFriends = new JButton("Search");
		btnSearchFriends.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					Registry creg = LocateRegistry.getRegistry(host);
					StaticRI cstub = (StaticRI)creg.lookup(regName);
					resultUser = cstub.searchFriend(tNameField.getText());
					DefaultListModel listModel = new DefaultListModel();
					for(User theUser :resultUser ){
						listModel.addElement(theUser.getFname());
					}
					list.setModel(listModel);
					list.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e){
							user = resultUser.get(list.getSelectedIndex());
							lUsername.setText(user.getUsername());
							lFullname.setText(user.getFname());
						}
					});
					
				}catch(Exception err){
					
				}
			}
		});
		btnSearchFriends.setBounds(428, 11, 89, 31);
		panel.add(btnSearchFriends);
		

		
		JLabel lblResult = new JLabel("Result List");
		lblResult.setFont(new Font("Candara", Font.BOLD, 22));
		lblResult.setBounds(10, 127, 110, 28);
		frameAddFriend.getContentPane().add(lblResult);
		
		Panel panel_2 = new Panel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(321, 154, 284, 155);
		frameAddFriend.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblName = new JLabel("Username :");
		lblName.setBounds(10, 30, 73, 20);
		panel_2.add(lblName);
		
		JLabel lblFullName = new JLabel("Full name:");
		lblFullName.setBounds(10, 55, 73, 27);
		panel_2.add(lblFullName);
		
		lUsername = new JLabel("");
		lUsername.setBounds(93, 28, 188, 25);
		panel_2.add(lUsername);
		
		lFullname = new JLabel("");
		lFullname.setBounds(93, 55, 188, 27);
		panel_2.add(lFullname);
		
		JButton btnAddFriend = new JButton("Add Friend");
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Vector<User> addF = new Vector<User>();
				User me = new User();
				User friend = new User();
				
				me.setUsername(lAF_username.getText());
				addF.addElement(me);
				
				friend.setUsername(lUsername.getText());
				addF.addElement(friend);
				
				try{
					Registry creg = LocateRegistry.getRegistry(host);
					StaticRI cstub = (StaticRI)creg.lookup(regName);
					resultAddFriend = cstub.addFriend(addF);
					if(resultAddFriend){
						JOptionPane.showMessageDialog(null,"Friend Added", "Add Friend", JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null,"Friend fail to add", "Add Friend", JOptionPane.WARNING_MESSAGE);
					}
					
					
				}catch(Exception err){
					
				}
				
			}
		});
		btnAddFriend.setBounds(10, 93, 264, 51);
		panel_2.add(btnAddFriend);
		
		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GuiDashboard.frame.setVisible(true);
				frameAddFriend.dispose();
			}
		});
		btnNewButton.setBounds(325, 410, 280, 53);
		frameAddFriend.getContentPane().add(btnNewButton);
		
		JLabel lblUsername = new JLabel("Username ");
		lblUsername.setBounds(535, 11, 70, 28);
		frameAddFriend.getContentPane().add(lblUsername);
		
		lAF_username = new JLabel("xx");
		lAF_username.setBounds(465, 41, 110, 14);
		frameAddFriend.getContentPane().add(lAF_username);
	}
}

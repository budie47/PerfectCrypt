package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JTextField;

import controller.ChatClient;
import controller.ChatClientInt;
import controller.ChatServerInt;
import modal.User;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class GUIChat {

	private JFrame frame;
	private JTextField textField;
	private JList list;
	
	  private ChatClient client;
	  private ChatServerInt server;
	  
	  public User user;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIChat window = new GUIChat();
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
	public GUIChat() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 818, 679);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 643, 537);
		frame.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(663, 42, 129, 537);
		frame.getContentPane().add(scrollPane_1);
		
		 list = new JList();
		scrollPane_1.setViewportView(list);
		
		textField = new JTextField();
		textField.setBounds(10, 590, 643, 39);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("SEND");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(663, 590, 129, 39);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Chat Room");
		lblNewLabel.setBounds(340, 0, 88, 29);
		frame.getContentPane().add(lblNewLabel);
	}
	
	public void showGUIChat(User user){
		frame.setVisible(true);
		user = user;
	}
	public void closeGUIChat(){
		frame.dispose();
	}
	
	public void writeMsg(String st){  textField.setText(textField.getText()+"\n"+st);  }
	
	public void doConnectChatServer(String name, String host){
		try{
			client = new ChatClient(name);
			//client.setGUI(this);
			server = (ChatServerInt)Naming.lookup(host);
			server.login(client);
			System.out.println(server.login(client));
			updateUsers(server.getConnected());
			
		}catch(Exception e){
			
		}
	}
	
	  public void updateUsers(Vector v){
	      DefaultListModel listModel = new DefaultListModel();
	      if(v!=null) for (int i=0;i<v.size();i++){
	    	  try{  String tmp=((ChatClientInt)v.get(i)).getName();
	    	  		listModel.addElement(tmp);
	    	  }catch(Exception e){e.printStackTrace();}
	      }
	      list.setModel(listModel);
	  }
	  
	  public void sendText(){

		      String st=textField.getText();
		      st="["+user.getUsername()+"] "+st;
		      textField.setText("");
		      //Remove if you are going to implement for remote invocation
		      try{
		    	  	server.publish(st);
		  	  	}catch(Exception e){
		  	  		e.printStackTrace();
		  	  		
		  	  	}
	}
		  

}

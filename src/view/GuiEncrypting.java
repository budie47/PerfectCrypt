package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.ConfigServer;
import controller.StaticRI;

import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.awt.event.ActionEvent;

public class GuiEncrypting extends JFrame {

	private JPanel contentPane;
	
	ConfigServer cs = new ConfigServer();
	String host = cs.host;
	String regName = cs.regName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiEncrypting frame = new GuiEncrypting();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuiEncrypting() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 470, 182);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 454, 143);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 11, 434, 76);
		panel.add(panel_1);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(50);
		progressBar.setForeground(new Color(0, 128, 0));
		progressBar.setBounds(5, 28, 419, 43);
		panel_1.add(progressBar);
		
		JLabel lblEncrypting = new JLabel("Encrypting...");
		lblEncrypting.setFont(new Font("Candara", Font.BOLD, 19));
		lblEncrypting.setBounds(10, 0, 141, 32);
		panel_1.add(lblEncrypting);
		
		JButton btnDoneEncryption = new JButton("Done");
		btnDoneEncryption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					
				}catch(Exception err){
					
				}

				
//				GuiSendCipher sendCipher = new GuiSendCipher();
//				sendCipher.setVisible(true);
//				dispose();
				
	
			}
		});
		btnDoneEncryption.setBackground(new Color(0, 153, 102));
		btnDoneEncryption.setForeground(new Color(255, 255, 255));
		btnDoneEncryption.setFont(new Font("Candara", Font.BOLD, 19));
		btnDoneEncryption.setBounds(120, 94, 194, 38);
		panel.add(btnDoneEncryption);
	}

}

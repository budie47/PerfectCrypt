package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JList;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuiSendCipher extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiSendCipher frame = new GuiSendCipher();
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
	public GuiSendCipher() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 676, 70);
		contentPane.add(panel);
		
		JButton btnSendPanel = new JButton("");
		btnSendPanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiDashboard frame = new GuiDashboard();
				frame.frame.setVisible(true);
				dispose();
			}
		});
		btnSendPanel.setForeground(Color.LIGHT_GRAY);
		btnSendPanel.setBackground(Color.LIGHT_GRAY);
		btnSendPanel.setBounds(0, 0, 72, 70);
		Dimension size3 = btnSendPanel.getSize();
		btnSendPanel.setIcon(new ImageIcon(new ImageIcon(GuiDashboard.class.getResource("/resources/back.png")).getImage().getScaledInstance(size3.width, size3.height, Image.SCALE_SMOOTH)));
		
		
		panel.add(btnSendPanel);
		
		JLabel label = new JLabel("Send");
		label.setFont(new Font("Candara", Font.BOLD, 22));
		label.setBounds(84, 32, 58, 31);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Perfect Crypt");
		label_1.setFont(new Font("Candara", Font.BOLD | Font.ITALIC, 25));
		label_1.setBounds(82, 6, 153, 31);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Username :");
		label_2.setFont(new Font("Candara", Font.PLAIN, 16));
		label_2.setBounds(467, 19, 79, 27);
		panel.add(label_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 81, 322, 308);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JList list = new JList();
		list.setBounds(10, 48, 302, 249);
		panel_1.add(list);
		
		JLabel lblUserList = new JLabel("User List");
		lblUserList.setFont(new Font("Candara", Font.BOLD, 16));
		lblUserList.setBounds(10, 11, 160, 26);
		panel_1.add(lblUserList);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(342, 81, 322, 227);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(10, 48, 302, 31);
		panel_2.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(10, 112, 302, 31);
		panel_2.add(textField_1);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Candara", Font.BOLD, 16));
		lblUsername.setBounds(10, 21, 160, 26);
		panel_2.add(lblUsername);
		
		JLabel lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setFont(new Font("Candara", Font.BOLD, 16));
		lblIpAddress.setBounds(10, 90, 160, 26);
		panel_2.add(lblIpAddress);
		
		JLabel lblTypeOfData = new JLabel("Connection Status");
		lblTypeOfData.setFont(new Font("Candara", Font.BOLD, 16));
		lblTypeOfData.setBounds(10, 154, 160, 26);
		panel_2.add(lblTypeOfData);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(10, 176, 302, 31);
		panel_2.add(textField_2);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiSending sending = new GuiSending();
				contentPane.setEnabled(true);
				sending.setVisible(true);
				
			}
		});
		btnSend.setForeground(new Color(255, 255, 255));
		btnSend.setBackground(new Color(0, 102, 153));
		btnSend.setFont(new Font("Candara", Font.BOLD, 39));
		btnSend.setBounds(342, 319, 319, 70);
		contentPane.add(btnSend);
	}
}

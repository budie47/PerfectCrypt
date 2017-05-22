package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.TextArea;
import javax.swing.ScrollPaneConstants;

public class GUIChatWindow {

	private JFrame frame;
	private JTextField textField;

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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblFriendName = new JLabel("Friend Name :");
		lblFriendName.setBounds(10, 11, 87, 29);
		lblFriendName.setFont(new Font("Candara", Font.PLAIN, 14));
		frame.getContentPane().add(lblFriendName);
		
		JLabel lblShai = new JLabel("Shai46");
		lblShai.setBounds(107, 11, 87, 29);
		lblShai.setFont(new Font("Candara", Font.PLAIN, 14));
		frame.getContentPane().add(lblShai);
		
		textField = new JTextField();
		textField.setBounds(10, 221, 324, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setBounds(335, 221, 89, 29);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblOnline = new JLabel("Online");
		lblOnline.setFont(new Font("Candara", Font.PLAIN, 14));
		lblOnline.setBounds(337, 11, 87, 29);
		frame.getContentPane().add(lblOnline);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 38, 414, 172);
		frame.getContentPane().add(scrollPane);
		
		TextArea textArea = new TextArea();
		scrollPane.setViewportView(textArea);
	}
}

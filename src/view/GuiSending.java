package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class GuiSending extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiSending frame = new GuiSending();
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
	public GuiSending() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 470, 176);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(0, 0, 454, 143);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(0, 0, 454, 143);
		panel.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(10, 11, 434, 76);
		panel_1.add(panel_2);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setValue(50);
		progressBar.setForeground(new Color(0, 128, 0));
		progressBar.setBounds(5, 28, 419, 43);
		panel_2.add(progressBar);
		
		JLabel lblSending = new JLabel("Sending...");
		lblSending.setFont(new Font("Candara", Font.BOLD, 19));
		lblSending.setBounds(10, 0, 141, 32);
		panel_2.add(lblSending);
		
		JButton button = new JButton("Done");
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Candara", Font.BOLD, 19));
		button.setBackground(new Color(0, 153, 102));
		button.setBounds(120, 94, 194, 38);
		panel_1.add(button);
	}

}

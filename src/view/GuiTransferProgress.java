package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JProgressBar;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GuiTransferProgress extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiTransferProgress frame = new GuiTransferProgress();
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
	public GuiTransferProgress() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 452, 210);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 183);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(10, 11, 414, 104);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setForeground(new Color(0, 128, 0));
		progressBar.setValue(50);
		progressBar.setBounds(5, 54, 404, 43);
		panel_1.add(progressBar);
		
		JLabel label = new JLabel("From userName");
		label.setFont(new Font("Candara", Font.PLAIN, 12));
		label.setBounds(15, 33, 141, 22);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("Transferring...");
		label_1.setFont(new Font("Candara", Font.BOLD, 19));
		label_1.setBounds(5, 0, 141, 32);
		panel_1.add(label_1);
		
		JButton btnTransferingCancel = new JButton("Cancel");
		btnTransferingCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GuiDashboard dashboard = new GuiDashboard();
				dashboard.frame.setEnabled(true);
				
				dispose();
			}
		});
		btnTransferingCancel.setForeground(new Color(255, 255, 255));
		btnTransferingCancel.setFont(new Font("Candara", Font.BOLD, 16));
		btnTransferingCancel.setBackground(new Color(204, 0, 0));
		btnTransferingCancel.setBounds(134, 125, 159, 40);
		panel.add(btnTransferingCancel);
	}

}

package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.hazelcast.util.Base64;

import controller.ConfigServer;
import controller.GenerateKeys;
import controller.RmiTransferClient;
import controller.StaticRI;
import rmitransfer.Server;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.PublicKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class GuiFileInfoWIndows {
	ConfigServer cs = new ConfigServer();
	String host = cs.host;
	String regName = cs.regName;
	String ftpHost = cs.urlHostFTP;

	public JFrame frame;
	public JLabel lblDownloadFileName;
	public String username;
	public String fileName;
	public String filePath;
	
	private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	JLabel lMethod;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiFileInfoWIndows window = new GuiFileInfoWIndows();
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
	public GuiFileInfoWIndows() {
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 449, 191);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		Date date = new Date();
		String dateS = sdf.format(date).toString();
		
		JLabel lblNewLabel = new JLabel("File Name: ");
		lblNewLabel.setBounds(60, 51, 69, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblDoYouWan = new JLabel("Do you wan download this file ?");
		lblDoYouWan.setBounds(133, 24, 229, 14);
		frame.getContentPane().add(lblDoYouWan);
		
		JButton btnDownloadFile = new JButton("Download");
		btnDownloadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String downloadFilePath = null;
				
				String downloadDir = "download/encrypted/"+username+"/"+dateS;
				File dDir = new File(downloadDir);
				dDir.mkdirs();
				
				try {
					Registry creg;
					creg = LocateRegistry.getRegistry(host);
					StaticRI cstub = (StaticRI)creg.lookup(regName);
					downloadFilePath = cstub.getFileNamePath(fileName, username);
					System.out.println(downloadFilePath);
					String method = cstub.getMethodCrypto(downloadFilePath,username);
					System.out.println(method);
					String digitalSignature = cstub.getDigitalSignature(downloadFilePath, username);
					System.out.println(digitalSignature);
					String senderPK = cstub.getSenderPublicKey(downloadFilePath, username);
					//System.out.println(filePath);
					GenerateKeys gk = new GenerateKeys();
					PublicKey senderPubKey = gk.getPublicKeyFromString(senderPK);
					
					byte[] digitalSignatureByte = Base64.decode(digitalSignature.getBytes());
					String publicKeyString = cstub.getPublicKey(username);
					
					
					String[] dkArry = downloadFilePath.split("/");
					String downloadFileKey = dkArry[0] +"/" +dkArry[1] +"/"+dkArry[2]+"/key";

					String url = ftpHost;
					Server server = (Server)Naming.lookup(url);
					File sourceFile = new File(downloadFilePath);
					File sourceKey = new File(downloadFileKey);
					
					RmiTransferClient rt = new RmiTransferClient();
					File downloadFile = new File(downloadDir+"/"+fileName);
					File downloadKey = new File(downloadDir+"/key");
					
//					if(lMethod.getText().equals("AES-256")){
//						String downloadFileSalt = dkArry[0] +"/" +dkArry[1] +"/"+dkArry[2]+"/salt";
//						File sourceSalt = new File(downloadFileSalt);
//						File downloadSalt = new File(downloadDir+"/salt");
//						rt.download(server, sourceSalt, downloadSalt);
//					}
					
					rt.download(server, sourceFile, downloadFile);
					rt.download(server, sourceKey, downloadKey);
					
					int input = JOptionPane.showOptionDialog(null, "Your File has been downloaded", "Download", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
					if(input == JOptionPane.OK_OPTION){
						GuiDecryptFile decrypt = new GuiDecryptFile();
						decrypt.frame.setVisible(true);
						decrypt.lFIleDir.setText(downloadDir+"/"+fileName);
						decrypt.lFileName.setText(fileName);
						decrypt.lMethodE.setText(lMethod.getText());
						decrypt.username = username;
						decrypt.digitalSignature = digitalSignatureByte;
						decrypt.senderPK = senderPubKey;
					}
					frame.dispose();
					
				} catch (MalformedURLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnDownloadFile.setBounds(73, 103, 108, 38);
		frame.getContentPane().add(btnDownloadFile);
		
		JButton btnCancelDownloadFIle = new JButton("Cancel");
		btnCancelDownloadFIle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCancelDownloadFIle.setBounds(240, 103, 108, 38);
		frame.getContentPane().add(btnCancelDownloadFIle);
		
		lblDownloadFileName = new JLabel("-");
		lblDownloadFileName.setBounds(128, 51, 259, 14);
		frame.getContentPane().add(lblDownloadFileName);
		
		JLabel lblEncryption = new JLabel("Encryption");
		lblEncryption.setBounds(60, 78, 69, 14);
		frame.getContentPane().add(lblEncryption);
		
		lMethod = new JLabel("-");
		lMethod.setBounds(133, 78, 267, 14);
		frame.getContentPane().add(lMethod);
	}
}

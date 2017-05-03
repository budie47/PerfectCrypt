package controller;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileSendingCS extends UnicastRemoteObject implements FileSendingCSInt{
	private String file = "";
	public FileSendingCS() throws RemoteException {
		super(); 
		// TODO Auto-generated constructor stub
	}
	
	public void setFile(String f){
		file = f;
	}

	@Override
	public boolean fileSending(FileSentCSInt c) throws RemoteException {
		boolean state = true;
		// TODO Auto-generated method stub
		try{
			File f1 = new File(file);
			FileInputStream in  = new FileInputStream(f1);
			byte [] mydata = new byte[(int)f1.length()];
			int mylen = in.read(mydata);
			for(int i = 0; i < (int)f1.length(); i++){
				c.sendData(f1.getName(), mydata, mylen);
				mylen = in.read(mydata);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return state;
	}

}

package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileSend extends UnicastRemoteObject implements FileSentInt{

	public String name;
	protected FileSend(String n) throws RemoteException {
		super();
		name = n;
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean sendData(String filename, byte[] data, int len) throws RemoteException {
		// TODO Auto-generated method stub
		try{
			File f = new File(filename);
			f.createNewFile();
			FileOutputStream out = new FileOutputStream(f,true);
			out.write(data, 0, len);
			out.flush();
			out.close();
			System.out.println("Done Writting Data...");
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return true;
	}
	
	@Override
	public String getName() throws RemoteException {
		// TODO Auto-generated method stub
		return name;
	}
	

}

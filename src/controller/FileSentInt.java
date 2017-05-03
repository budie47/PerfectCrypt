package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileSentInt extends Remote {
	public boolean sendData(String filename, byte[] data, int len)throws RemoteException;
	public String getName() throws RemoteException;
}

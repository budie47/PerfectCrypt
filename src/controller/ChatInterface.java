package controller;

import java.rmi.RemoteException;

public interface ChatInterface extends java.rmi.Remote{
	
	boolean checkClientCredintials(ChatInterface ci, String name, String pass)throws RemoteException;
	void broadcastMessage(String name,String message) throws RemoteException;
	void sendMessageToClient(String message) throws RemoteException;
	
}

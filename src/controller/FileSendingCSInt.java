package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileSendingCSInt extends Remote {
	public boolean fileSending (FileSentCSInt c) throws RemoteException;
}

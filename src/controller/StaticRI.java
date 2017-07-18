package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import modal.FileModal;
import modal.FileUser;
import modal.User;

public interface StaticRI extends Remote {
	public void sayHello()throws RemoteException;
	public boolean registerUser(User user)throws RemoteException;
	public boolean loginUser(User user) throws RemoteException;
	public Vector<User> searchFriend(String name) throws RemoteException;
	public boolean addFriend(Vector<User> friendUser) throws RemoteException;
	public Vector<User> getCurrentFriend(String name) throws RemoteException;
	public boolean sendCipher(byte[] cipherText, String filename, int sizeFile, String receiverName) throws RemoteException;
	public boolean sendData(FileModal f) throws RemoteException;
	public String getName() throws RemoteException;
	public void startFileServer()throws RemoteException;
	public void checkPath(String pathFile) throws RemoteException;
	public String getPublicKey(String userName) throws RemoteException;
	public void saveData(String method, String receiveName, String receiverPath,String digitalSignture, String sender)throws RemoteException;
	public Vector<FileUser>getFileUser(String user)throws RemoteException, Exception;
	public String getFileNamePath(String fileName, String username) throws RemoteException;
	public String getMethodCrypto(String filePath,String username) throws RemoteException;
	public String getUserEncryptedPrivateKey(String userId)throws RemoteException;
	public String getDigitalSignature(String filePath,String username) throws RemoteException;
	public String getSenderPublicKey(String filePath,String username) throws RemoteException;

}

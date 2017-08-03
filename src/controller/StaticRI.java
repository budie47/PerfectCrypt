package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import modal.FileModal;
import modal.FileUser;
import modal.Message;
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
	
	public int getUserId(String username)throws RemoteException;
	
	public boolean sendMessage(Message message)throws RemoteException;
	public Vector<Message> getMessage(int sender_id, int receiver_id)throws RemoteException;
	public Vector<Message> getMessageNewMessage(int sender_id, int receiver_id)throws RemoteException;

	public String getUserStatus(String userId)throws RemoteException;
	public boolean logOutUser(String username) throws RemoteException;
	public void openChatServer(String user1, String user2)throws RemoteException;
	
	public void closeChatWindows(int sender_id, int receiver_id) throws RemoteException;
	public Vector<Message> getMessageThread(int sender_id, int receiver_id)throws RemoteException;
	
	public boolean checkNewChat(int sender_id, int receiver_id)throws RemoteException;
	public void updateNewChat(Message message)throws RemoteException;
	public void changeShowStatus(int message_id, int status) throws RemoteException;
}

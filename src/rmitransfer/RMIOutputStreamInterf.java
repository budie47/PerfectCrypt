package rmitransfer;
import java.io.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIOutputStreamInterf extends Remote {
    
    public void write(int b) throws IOException, RemoteException;
    public void write(byte[] b, int off, int len) throws IOException, RemoteException;
    public void close() throws IOException, RemoteException;



}
package test;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import controller.FileSendingCS;

public class StartFileSending {

	public static void main(String[] args){
		try{
			LocateRegistry.createRegistry(1099);
			FileSendingCS fs = new FileSendingCS();
			fs.setFile("T-ARA.MKV");
			Naming.rebind("rmi://192.168.0.157/file", fs);
			System.out.println("File Ready to Send...");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

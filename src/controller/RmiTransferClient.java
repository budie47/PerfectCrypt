package controller;
import java.io.*;
import java.rmi.Naming;

import rmitransfer.Server;

public class RmiTransferClient {
	
	final public static int BUF_SIZE = 1024*64;
	
	public static void copy(InputStream in, OutputStream out)throws IOException{
		System.out.println("using byte[] read/write ");
		byte[] b = new byte[BUF_SIZE];
		int len;
		while((len = in.read(b)) >= 0){
			out.write(b,0,len);
			
		}
		in.close();
		out.close();
	}
	
	public static void upload(Server server, File src, File dest)throws IOException{
		copy(new FileInputStream(src),server.getOutputStream(dest));	
		System.out.println("here");
	}
	
	public static void download(Server server, File src, File dest) throws IOException{
		copy(server.getInputStream(src),new FileOutputStream(dest));
	}
	
	

}

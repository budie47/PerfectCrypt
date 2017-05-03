package rmitransfer;

import java.io.*;
import java.rmi.Naming;


public class TestClient {
    
    final public static int BUF_SIZE = 1024 * 64;
    
public static void copy(InputStream in, OutputStream out)
        throws IOException {
	System.out.println("using byte[] read/write");
    byte[] b = new byte[BUF_SIZE];
    int len;
    while ((len = in.read(b)) >= 0) {
        out.write(b, 0, len);
    }
    in.close();
    out.close();
}
    public static void upload(Server server, File src, File dest) throws IOException {
        copy (new FileInputStream(src), 
        server.getOutputStream(dest));
    }

    public static void download(Server server, File src,  File dest) throws IOException {
        copy (server.getInputStream(src), 
        new FileOutputStream(dest));
    }

    public static void main(String[] args) throws Exception {
        
        String url = "rmi://localhost/server";
        Server server = (Server) Naming.lookup(url);
        
        System.out.println("Server says: " + server.sayHello());
        
        File testFile = new File("C:Users\\-D-\\Desktop\\pic.jpg");
        System.out.println("1" );
        long len = testFile.length();
        
        long t;
        
        t = System.currentTimeMillis();
        download(server, testFile, new File("picDownload.jpg"));
        
//        t = (System.currentTimeMillis() - t) / 1000;
//        System.out.println("download: " + (len / t / 1000000d) + 
//            " MB/s");
//        System.out.println("2" );
        
        t = System.currentTimeMillis();
        upload(server, new File("C:Users\\-D-\\Desktop\\pic.jpg"),new File("pic.jpg"));
        
//        System.out.println("3");
//        t = (System.currentTimeMillis() - t) / 1000;
//        System.out.println("upload: " + (len / t / 1000000d) + " MB/s");
//        System.out.println("4" );
    }

}
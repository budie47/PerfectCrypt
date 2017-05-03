package database;
import java.sql.Connection;
import java.sql.DriverManager;

public class DbConn {
	String driver;
	String connectionURL;
	String dbName;
	String username;
	String password;


	public DbConn()
	{
		 driver = "com.mysql.jdbc.Driver";
		 connectionURL = "jdbc:mysql://localhost:3306/";
		 dbName = "perfectCrypt";
		 username = "root";
		 password = "";
	}
	 public Connection getConnection() throws Exception 
	 {
		 Class.forName(driver);
		 Connection connection = DriverManager.getConnection(connectionURL+dbName,username,password);
		 return connection;
	 }
	 
	 public static void main(String[] args) 
	 {
		 DbConn db = new DbConn();
			try 
			{
				Connection conn = db.getConnection();
				System.out.println("Database successfully connected!");
				conn.close();
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}

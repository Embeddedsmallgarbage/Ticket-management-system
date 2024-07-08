package ticketmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
	private static Connection conn = null;
	
	private static String Name = "root";
	private static String password = "00000000";
	private static String URL = "jdbc:mysql://127.0.0.1:3306/ticket_management?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
	
	private static String Dname = "com.mysql.jdbc.Driver"; //驱动名
	
	public static Connection getConn() {
		// 加载驱动
		try {
			Class.forName(Dname);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//获取连接
		try {
			conn = DriverManager.getConnection(URL, Name, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void main(String[] args) {
//		System.out.println(getConn());
	}
}

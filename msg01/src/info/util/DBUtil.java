package info.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	public static Connection getConnection(){
		String username = "jhc";
		String password = "jhc123";
		String url = "jdbc:mysql://localhost:3306/msg";
		Connection connection = null;
		try {
			//"jdbc:mysql://localhost:3306/XX","root","XXXX"
			connection = DriverManager.getConnection(url,username,password);
			//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/msg","root","8123456");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void close(Connection connection){
		try {
			if(connection != null) connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement preparedStatement){
		try {
			if(preparedStatement != null) preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet resultSet){
		try {
			if(resultSet != null) resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

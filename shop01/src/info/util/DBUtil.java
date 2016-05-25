package info.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.sun.xml.internal.ws.Closeable;

public class DBUtil {
	public static Connection getConnection(){
		Properties prop = PropertiesUtil.getJdbcProp();
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String url = prop.getProperty("url");
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url,username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
		
		/*
		Connection connection = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context)initCtx.lookup("java:comp/env");
			DataSource dSource = (DataSource)envCtx.lookup("jdbc/msg");
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
		*/
	}
	
	public static void close(Connection connection){
		try {
			if(connection != null)
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(java.sql.PreparedStatement pStatement){
		try {
			if(pStatement != null)
				pStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet resultSet){
		try {
			if(resultSet != null)
				resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	private static final String URL = "jdbc:mysql://localhost:3306/bulletin_board_system";
	private static final String USER = "root";
	private static final String PASSWORD = "!02Sora09!";
	
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection connection = null;
	
	public static Connection getConnection(){
		
		
		try{
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			connection.setAutoCommit(false);
			return connection;
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
		
	}
	
	public static void commit(Connection connection){
		try{
			connection.commit();
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}

	
	public static void rollback(Connection connection){
		if(connection != null){
			try{
				connection.rollback();
			}catch(SQLException e){
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void close(Connection connection){
		if(connection != null){
			try{
				connection.close();
			}catch(SQLException e){
				throw new RuntimeException(e);
			}
		}
	}


	public static void close(PreparedStatement ps){
		if(ps != null){
			try {
				ps.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void close(ResultSet rs){
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
}
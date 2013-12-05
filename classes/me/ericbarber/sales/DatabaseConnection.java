package me.ericbarber.sales;
/**
 * DatabaseConnection is the class used to define how to connect to a database, in this instance, MySQL.
 * 
 * @author Eric Barber
 * @version 0.1
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
 	  private static final String DRIVER = "com.mysql.jdbc.Driver";
 	  private static final String URL = "jdbc:mysql://";
 	  private static final String USERNAME = "";
 	  private static final String PASSWORD = "";
 	  public Connection connect = null;
	
 	  /**
 	   * This method will return a database connection.
 	   * 
 	   * @return Connection
 	   * @throws ClassNotFoundException 
 	   * @throws SQLException
 	   */
	  public Connection getConnection() throws SQLException, ClassNotFoundException{
		  try{
		  	Class.forName(DRIVER);
		  	connect = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		  }catch(SQLException e){
			  e.printStackTrace();
			  //Log
		  }catch(ClassNotFoundException e){
			  e.printStackTrace();
			  //Log
		  }
		  return connect;
	  }

	  /**
	   * This method closes the connection to the database.
	   */
		   public void close() throws SQLException {
		      try {
		        if (connect != null) {
		          connect.close();
		        }
		      } catch (SQLException e) {
		    	  e.printStackTrace();
		    	  //Log
		      }
		    }
}
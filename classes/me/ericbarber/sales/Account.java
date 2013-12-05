package me.ericbarber.sales;
/**
 * Account is a class simply used to allow users to login through a JSP page. The only public available method is login(username,password)
 * 
 * @author Eric Barber
 * @version 0.2
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Account {
	private DatabaseConnection DBConnection;
	private Connection connect;
	private Statement statement = null;
  	private ResultSet resultSet = null;
	private boolean status;
	
	/**
	 * This should be in DatabaseConnections in version v0.2. In short, connects to the database.
	 * 
	 * @throws SQLException
	 * @since 0.2
	 * @deprecated
	 */
	private void connectToDb() throws SQLException{
		try{
		DBConnection = new DatabaseConnection();
		connect = DBConnection.getConnection();
		}catch (Exception e){
		}
	}
	
	/**
	 * This should be in DatabaseConnections in version v0.2. In short, closes connection to the database.
	 * 
	 * @throws SQLException
	 * @since 0.2
	 * @deprecated
	 */
	private void closeConnections(DatabaseConnection DB, Statement s,ResultSet rs,Connection c) throws SQLException{
  		try{
  			if (rs != null){
  	  				rs.close();
  	  		}
  	  		if(c != null){
  	  				c.close();
  	  		}
  	  		if(DB != null){
  	  				DB.close();
  	  		}
  		}catch(SQLException e){

  		}
  	}
	
	/**
	 * This method is used for logging into the admin panel to approve items and view all items currently approved, etc.
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 * @since 0.2
	 */
	public boolean login(String username, String password) throws SQLException  {
	    status = false;
	    try {
			 connectToDb();
			 statement = connect.createStatement();
			 resultSet = statement.executeQuery("select email,password from randomsalesadmin.user");
			   while (resultSet.next ())
			   {
				   if(username.equals(resultSet.getString("email")) && password.equals(resultSet.getString("password"))){
					status = true;
					 break;
				   }else{
					status = false;
				}
			   }
	    }
	catch(SQLException e){
		return false;
	}finally{
			closeConnections(DBConnection,statement,resultSet,connect);
	}
	return status;
	}
	
}

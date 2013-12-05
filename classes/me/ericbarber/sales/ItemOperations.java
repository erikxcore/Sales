package me.ericbarber.sales;
/**
 * Item Operations is a class that may be used in connection with DBConn, Item, Items, and the test interface SQLtoXMLTest.
 * This class has been designed to seperate the model of item object creation and direct access to the database.
 * Item Operations is built for use with a web service for web driven applications with a MySQL back-end. In terms of implementing
 * this class please read the documentation on how each method operates. In conjunction with Java Server Pages you will need
 * knowledge of the item class to work with the database entry ID #s and parsing certain information about a specific item; the
 * Items Web Service will return pertinent information via XML.
 * 
 * @author Eric Barber
 * @version 0.1
 */

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

import me.ericbarber.logger.MyLogger;

public class ItemOperations {
	private DatabaseConnection DBConnection;
	private Connection connect;
	private Statement statement = null;
  	private ResultSet resultSet = null;
	private PreparedStatement preparedStatement = null;
  	private ArrayList<Item> items;
    private MyLogger logger = new MyLogger();
  	
	/**
  	 * Closes any open connections to the database for methods that were using prepared statements. This method
  	 * is overloaded to allow either a prepared statement or a regular statement. Should be moved into DatabaseConnection.
  	 * 
  	 * @param DB The database connection object bases on the DatabaseConnection class - simply put, the connection to the MySQL database.
  	 * @param ps The sent in Prepared Statement; if using a Statement see other overloaded method.
  	 * @param c  The literal connection from DatabaseConnection.
  	 * @throws SQLException If there is an error in the method call a stack trace will be printed; only
  	 * 						expected error would be if the objects have already been closed or if the
  	 * 						connection itself failed.
  	 * @see DatabaseConnection
  	 * @since 0.1
  	 * @deprecated
	 */
	private void closeConnections(DatabaseConnection DB, PreparedStatement ps,Connection c) throws SQLException{
  		try{
  			if (ps != null){
  	  				ps.close();
  	  		}
  	  		if(c != null){
  	  				c.close();
  	  		}
  	  		if(DB != null){
  	  				DB.close();
  	  		}
  		}catch(SQLException e){
  			logger.setupLogger("Problem closing database connection.");
  		}
  	}
	
	/**
  	 * Closes any open connections to the database for methods that were using statements. This method
  	 * is overloaded to allow either a prepared statement or a regular statement. Should be moved into DatabaseConnection.
  	 * 
  	 * @param DB The database connection object bases on the DatabaseConnection class - simply put, the connection to the MySQL database.
  	 * @param rs The sent in statement; if using a Prepared Statement see other overloaded method.
  	 * @param c  The literal connection from DatabaseConnection.
  	 * @throws SQLException If there is an error in the method call a stack trace will be printed; only
  	 * 						expected error would be if the objects have already been closed or if the
  	 * 						connection itself failed.
  	 * @see DatabaseConnection
  	 * @since 0.1
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
  			logger.setupLogger("Problem closing database connection.");
  		}
  	}
	
	/**
	 * This method attempts to create the connection to the database in conjunction with the DatabaseConnection class.
	 * Should be moved into DatabaseConnection.
	 * 
	 * @throws SQLException
	 * @see DatabaseConnection
	 * @since 0.1
	 * @deprecated
	 */
	private void connectToDb() throws SQLException{
		try{
		DBConnection = new DatabaseConnection();
		connect = DBConnection.getConnection();
		}catch (Exception e){
			logger.setupLogger("Problem connecting with database connection.");
		}
	}
	
	/**
	 * This method, which is used in multiple other methods, will generate an item object based on a Result Set
	 * and requested ID.
	 * 
	 * @param rs The result set from a SQL statement.
	 * @param id The requested ID (either a randomly generated # or a specific ID)
	 * @return item object which should not be null unless there is an exception or the requested ID does not exist.
	 * @throws SQLException, SalesExceptions
	 * @see Item
	 * @since 0.1
	 */
	private Item generateItem(ResultSet rs, int id) throws SQLException,SalesExceptions,NullPointerException{
		Item returnItem = new Item();
		try{
			while(rs.next()){
				if(rs.getInt("ID") == id){
				String title = rs.getString("title");
				String url = rs.getString("url");
				String thumburl = rs.getString("thumburl");
				String description = rs.getString("description");
				int upvote = rs.getInt("upvote");
				int downvote = rs.getInt("downvote");
				boolean status = rs.getBoolean("approved");
				returnItem.setTitle(title);
				returnItem.setUrl(url);
				returnItem.setThumb(thumburl);
				returnItem.setDescription(description);
				returnItem.setUpVote(upvote);
				returnItem.setDownVote(downvote);
				returnItem.setdbID(id);
				returnItem.setApproved(status);
				if(returnItem.getTitle() == null){
					throw new SalesExceptions("Your sent in ID did not return a valid item.");
				}
			}
		}
		}catch (SQLException e){
			logger.setupLogger("Problem with the database parsing in generateItem.");
		}catch (NullPointerException e){
  			logger.setupLogger("Problem connecting with database connection in generateItem.");
  		}catch(SalesExceptions e){
			logger.setupLogger("Your sent in ID did not return a valid item.");
		}
		return returnItem;
	}
	
	/**
	 * Generate Array accepts an ArrayList<Item> and will return a full array list dependent on the sent in
	 * result set. The ArrayList returned will have fully workable Item objects within.
	 * 
	 * @param rs The result set of an already committed SQL statement.
	 * @return Populated ArrayList<Item>
	 * @throws SQLException, SalesExceptions
	 * @see Item
	 * @see Items
	 * @since 0.1
	 */
	private ArrayList<Item> generateArray(ResultSet rs) throws SQLException, SalesExceptions,NullPointerException{
		ArrayList<Item> items = new ArrayList<Item>();
		try{
		while (rs.next()) {
			Item newItem = new Item();
			String title = rs.getString("title");
			String url = rs.getString("url");
			String thumburl = rs.getString("thumburl");
			String description = rs.getString("description");
			int upvote = rs.getInt("upvote");
			int downvote = rs.getInt("downvote");
			int id = rs.getInt("ID");
			boolean status = rs.getBoolean("approved");
			newItem.setTitle(title);
			newItem.setUrl(url);
			newItem.setThumb(thumburl);
			newItem.setDescription(description);
			newItem.setUpVote(upvote);
			newItem.setDownVote(downvote);
			newItem.setdbID(id);
			newItem.setApproved(status);
			items.add(newItem);
		}
		if(items.isEmpty()){
			throw new SalesExceptions("Your SQL statement did not generate any valid items to return.");
		}
		}catch(SQLException e){
			logger.setupLogger("Problem connecting with database connection in generateArray.");
		}catch (NullPointerException e){
  			logger.setupLogger("Problem connecting with database connection in generateArray");
  		}catch(SalesExceptions e){
			logger.setupLogger("Your SQL statement did not generate any valid items to return.");
		}
		return items;
	}
  	
	/**
	 * This method accepts a specific ID and then a type of voting procedure for an Item. If the type is not
	 * sent in correctly then this method will do nothing as the boolean typeCorrect will check if the type
	 * is indeed correct.
	 * 
	 * @param id The specific database ID for an Item entry.
	 * @param type Should either be 'Up' or 'Down' depending on how the user wants to vote for an item.
	 * @throws SQLException
	 */
  	public void voteItem(int id, String type) throws SQLException,SalesExceptions,NullPointerException{
  		try{
  			boolean typeCorrect = false;
			connectToDb();
			if(type.toUpperCase().equals("UP")){
			typeCorrect = true;
  			preparedStatement = connect.prepareStatement("update sale set upvote = upvote + 1 where id = ?");
			}else if(type.toUpperCase().equals("DOWN")){
			typeCorrect = true;
			preparedStatement = connect.prepareStatement("update sale set downvote = downvote + 1 where id = ?");	
			}else{
			typeCorrect = false;
			throw new SalesExceptions("You did not send in a valid type for voteItem. The string must be either 'Up' or 'Down' (Case insensitive)."); 
			}
			if(typeCorrect == true){
  			preparedStatement.setInt(1, id);
  			int success = preparedStatement.executeUpdate();
  			if(success == 0){
  			throw new SalesExceptions("Could not vote for item #" + id +" ID not found!");
  			}
			}
  		}catch(SQLException e){
  			logger.setupLogger("Problem connecting with database connection in voteItem.");
  		}catch (NullPointerException e){
  			logger.setupLogger("Problem connecting with database connection in voteItem.");
  		}catch(SalesExceptions e){
  			logger.setupLogger("Could not vote for item #" + id +" ID not found!");
  		}finally{
  			closeConnections(DBConnection,preparedStatement,connect);
  		}
  	}
  	
  	/**
  	 * This method allows you to attempt to delete an item in the database.
  	 * 
  	 * @param id - ID of a specific item in the database.
  	 * @throws SQLException
  	 * @see Item
  	 * @since 0.1
  	 */
  	public void deleteSpecificItem(int id) throws SQLException,SalesExceptions,NullPointerException{
  		try{
  			connectToDb();
  			preparedStatement = connect.prepareStatement("DELETE from sale where id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
  			int success = preparedStatement.executeUpdate();
  			if(success == 0){
  			throw new SalesExceptions("Could not delete for item #" + id +" ID not found!");
  			}else{
  			// /*Log*/
  			}
  		}catch (SQLException e){
  			logger.setupLogger("Problem connecting with database connection in deleteSpecificItem.");
  		}catch (NullPointerException e){
  			logger.setupLogger("Problem connecting with database connection in deleteSpecificItem.");
  		}catch(SalesExceptions e){
  			logger.setupLogger("Could not delete for item #" + id +" ID not found!");
		}finally{
  			closeConnections(DBConnection,preparedStatement,connect);	
  		}
  	}
  	
  	/**
  	 * This method allows a user to either approve or disapprove an Item.
  	 * 
  	 * @param status True if the item is approved, false if item is un-approved or newly added.
  	 * @param id The ID of a specific Item in the database.
  	 * @throws SQLException
  	 * @see Item
  	 * @since 0.1
  	 */
  	public void changeItemStatus(boolean status,int id) throws SQLException,NullPointerException,SalesExceptions{
  		try{
  			connectToDb();
  			preparedStatement = connect.prepareStatement("UPDATE sale set approved = ? where id = ?");
			preparedStatement.setBoolean(1, status);
			preparedStatement.setInt(2, id);
  			int success = preparedStatement.executeUpdate();
  			if(success == 0){
  			throw new SalesExceptions("Could not approve/deny for item #" + id +"ID not found!");
  			}
  		}catch(SQLException e){
  			logger.setupLogger("Problem connecting with database connection in changeItemStatus.");
  		}catch (NullPointerException e){
  			logger.setupLogger("Problem connecting with database connection in changeItemStatus.");
  		}catch(SalesExceptions e){
  			logger.setupLogger("Could not approve/deny for item #" + id +"ID not found!");
  		}
  		finally{
  			closeConnections(DBConnection,preparedStatement,connect);	
  		}
  	}
  	
  	/**
  	 * This method allows a user to insert a new record into the database through this class rather than an interface or directly
  	 * through the SQL database.
  	 * 
  	 * @param newTitle - Title of Item
  	 * @param newUrl - Direct URL to Item
  	 * @param thumbUrl - Direct Image URL to Item
  	 * @param description - Simple description of Item
  	 * @throws SQLException
  	 * @see Item
  	 * @since 0.1
  	 */
  	public void insertNewItem(String newTitle, String newUrl, String thumbUrl, String description) throws SQLException,SalesExceptions,NullPointerException{
  		try{
  			connectToDb();
  			preparedStatement = connect.prepareStatement("insert into sale values (default, ?, ?, ?, ?, 0, 0, ?)");
			preparedStatement.setString(1, newTitle);
			preparedStatement.setString(2, newUrl);
			preparedStatement.setString(3, thumbUrl);
			preparedStatement.setString(4, description);
			preparedStatement.setBoolean(5, false);
  			int success = preparedStatement.executeUpdate();
  			if(success == 0){
  			throw new SalesExceptions("Could not insert a new item.");
  			}
  		}catch (SQLException e){
  			logger.setupLogger("Problem connecting with database connection in insertNewItem.");
  		}catch (NullPointerException e){
  			logger.setupLogger("Problem connecting with database connection in insertNewItem.");
  		}catch(SalesExceptions e){
  			logger.setupLogger("Could not insert a new item.");
  		}
  		finally{
  			closeConnections(DBConnection,preparedStatement,connect);
  		}	
  	}

  	/**
  	 * This method will return a specific Item from the database as an Item object.
  	 * 
  	 * @param id - The specific ID of an Item in the database.
  	 * @return Item
  	 * @throws SQLException
  	 * @see Item
  	 * @since 0.1
  	 */
	public Item getSpecificItem(int id) throws SQLException,SalesExceptions,NullPointerException{
		Item specificItem = new Item();
		try{
			connectToDb();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from sale");
			specificItem = generateItem(resultSet, id);
		}catch (SQLException e){
			logger.setupLogger("Problem connecting with database connection in getSpecificItem.");
		}catch (NullPointerException e){
  			logger.setupLogger("Problem connecting with database connection in getSpecificItem.");
  		}catch (SalesExceptions e){
			//Logged in generateItem
		}finally{
			closeConnections(DBConnection,statement,resultSet,connect);
		}
		return specificItem;
	}
	
	/**
	 * This method will return a random Item from the database as an Item object.
	 * 
	 * @return Item
	 * @throws SQLException
	 * @see Item
	 * @since 0.1
	 */
	public Item getRandomItem() throws SQLException,SalesExceptions,NullPointerException{
		Item randomItem = new Item();
		int totalSales = 0;
		try{
			connectToDb();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT COUNT(ID) as count from sale");
			while(resultSet.next()){
				totalSales = resultSet.getInt("count");
			}
			Random rand = new Random();
			int randomSale = rand.nextInt((totalSales - 1)+1)+1;	
			statement.close();
			statement = connect.createStatement();
			while(randomItem.getTitle() == null || randomItem.getApproved() == false){
				randomSale = rand.nextInt((totalSales - 1)+1)+1;
				resultSet = statement.executeQuery("select * from sale where ID =  " + randomSale);
				randomItem = generateItem(resultSet,randomSale);
				if(randomItem.getTitle() != null && randomItem.getApproved() == true){
					break;
				}
			}
		}catch (SQLException e){
			logger.setupLogger("Problem connecting with database connection in getRandomItem.");
		}catch (NullPointerException e){
  			logger.setupLogger("Problem connecting with database connection in getRandomItem.");
  		}catch (SalesExceptions e){
			//Logged in generateItem.
		}finally{
			closeConnections(DBConnection,statement,resultSet,connect);
		}
		return randomItem;
	}
	
	/**
	 * This method returns a populated ArrayList of Items which contains many to one Item objects. The ArrayList will only
	 * contain Items which return true on Item.getApproved().
	 * 
	 * @return ArrayList<Item>
	 * @throws SQLException
	 * @see Items
	 * @see Item
	 * @since 0.1
	 */
  	public ArrayList<Item> generateApprovedItems() throws SQLException,SalesExceptions,NullPointerException{
		ArrayList<Item> approvedItems = new ArrayList<Item>();
  		try{
  			connectToDb();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from sale");
			items = new ArrayList<Item>();
			items = generateArray(resultSet);
			for(Item i : items){
				if(i.getApproved() == true){
					approvedItems.add(i);
				}
			}
  		}catch (SQLException e){
  			logger.setupLogger("Problem connecting with database connection in generateApprovedItems.");
  		}catch (NullPointerException e){
  			logger.setupLogger("Problem connecting with database connection in generateApprovedItems.");
  		}catch (SalesExceptions e){
  			//Logged in generateArray.
  		}finally{
			closeConnections(DBConnection,statement,resultSet,connect);	
  		}
  		return approvedItems;
  	}
  	
	/**
	 * This method returns a populated ArrayList of Items which contains many to one Item objects. This ArrayList will contain
	 * all items in the database. Should only be used for administration purposes such as selecting items to approve/disapprove.
	 * 
	 * @return ArrayList<Item>
	 * @throws SQLException
	 * @see Items
	 * @see Item
	 * @since 0.1
	 */
  	public ArrayList<Item> generateAllItems() throws SQLException, SalesExceptions, NullPointerException{
  		try{
  			connectToDb();
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from sale");
			items = new ArrayList<Item>();
  			items = generateArray(resultSet);
  		}catch (SQLException e){
  			logger.setupLogger("Problem connecting with database connection in generateAllItems.");
  		}catch (NullPointerException e){
  			logger.setupLogger("Problem connecting with database connection in generateAllItems.");
  		}catch (SalesExceptions e){
  			//Logged in generateArray.
  		}finally{
			closeConnections(DBConnection,statement,resultSet,connect);
  		}
  		return items;
  	}
}

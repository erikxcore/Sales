package me.ericbarber.logger;
/**
 * MyLogger is a class that simply creates files of any exceptions that may be logged rather than system stack traces. This
 * class uses a custom LogFormatter class that generates a visibly pleasing HTML document, as well. If using this logger in
 * an external class or package you must first import this package and then create a local variable; e.g. private MyLogger log
 * = new MyLogger();. To log any sort of exceptions or messages you must catch an exception and then log.setupLogger(e) - in this
 * example e would be catch(Exception e). This logger will then output any caught exception to a .txt or .html file in the main
 * directory of the project. Will append all errors to Logging.txt or Logging.html.
 * 
 * @author Eric Barber
 * @version 0.1
 */
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;
	
	static private FileHandler fileHTML;
	static private Formatter formatterHTML;
	
  	static private Logger logger = Logger.getLogger(MyLogger.class.getName()); 
  	
  	/**
  	 * This method sets up the file creation of the logger and adds the formatting to the text and HTML
  	 * versions of the log.
  	 * 
  	 * @throws IOException
  	 */
	static private void setup() throws IOException{	  		
		logger.setLevel(Level.INFO);
		fileTxt = new FileHandler("Log.txt", true);
		fileHTML = new FileHandler("Log.htm", true);
		
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);
		
		formatterHTML = new LogFormatter();
		fileHTML.setFormatter(formatterHTML);
		logger.addHandler(fileHTML);
	}

	/**
	 * This method calls the initial setup above however it is intended for public use in outside classes.
	 * 
	 * @param message - could be anything ranging from an exception to a regular String that must be logged
	 * For example, database operations.
	 */
  	public void setupLogger(String message){
  		try{
  		setup();
  		}catch(IOException e){
  			throw new RuntimeException("Problems with creating log file.");
  		}
  		logger.log(Level.SEVERE, message);
  	}
  
}

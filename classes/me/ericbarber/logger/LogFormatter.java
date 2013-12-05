package me.ericbarber.logger;
/**
 * LogFormatter is a class that generates an HTML document for easily readability of logs. Currently each caught exception
 * that is logged will generate a new table - which requires a rewrite - however, if the Log file does not exist in conjunction
 * with MyLogger all exceptions will be logged to one file.
 * 
 * @author Eric Barber
 * @version 0.1
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {

	/**
	 * This method takes a string exception record and generates a table row for more pleasing output.
	 * 
	 * @param record - the incoming information to log.
	 */
	@Override
	public String format(LogRecord record) {
		StringBuffer buf = new StringBuffer(1000);
		buf.append("<tr>");
		buf.append("<td>");
		
		if(record.getLevel().intValue() >= Level.WARNING.intValue()){
			buf.append("<b>");
			buf.append(record.getLevel());
			buf.append("</b>");
		}else{
			buf.append(record.getLevel());
		}
		
		buf.append("</td>");
		buf.append("<td>");
		buf.append(calcDate(record.getMillis()));
		buf.append(' ');
		buf.append("</td>");
		buf.append("<td>");
		buf.append(formatMessage(record));
		buf.append("</td>");
		buf.append("</tr>\n");
		
		return buf.toString();
	}

	/**
	 * This method calculates the date.
	 * 
	 * @param millisecs
	 * @return - the date in String format.
	 */
	private String calcDate(long millisecs){
		SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}
	
	/**
	 * This method is the header of the HTML document, extended from Formatter but not overloaded.
	 * 
	 * @param h - required by getHead, unused in return statement.
	 */
	public String getHead(Handler h){
		return "<HTML>\n<HEAD>\n" + (new Date()) + "\n</HEAD>\n<BODY>\n<PRE>\n" + "<table width=\"100%\" border>\n" + "<tr><th>Level</th>" + "<th>Time</th>" + "<th>Log Message</th>" + "</tr>\n";
	}
	
	/**
	 * This method is the footer of the HTML document, extended from Formatter but not overloaded.
	 * 
	 * @param h - required by getFoot, unused in return statement.
	 */
	public String getTail(Handler h){
		return "</table>\n </PRE></BODY>\n</HTML>\n";
	}

}

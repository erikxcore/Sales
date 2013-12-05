package me.ericbarber.sales;
/**
 * This custom exception is used if a user does not use the voteItem method correctly.
 * 
 * @author Eric Barber
 * @version 0.1
 */

public class SalesExceptions extends Exception {
	private static final long serialVersionUID = 5484591281196473900L;

	public SalesExceptions(String message){
		super(message);
	}

}

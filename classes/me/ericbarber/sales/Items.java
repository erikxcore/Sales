package me.ericbarber.sales;
/**
 * Items is the model for any Array List of Item that is pulled from the Database.
 * 
 * @author Eric Barber
 * @version 0.1
 */

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.*;

@XmlRootElement(namespace = "me.ericbarber.sales")
public class Items {

	private ArrayList<Item> saleItems;
	
	/**
	 * This method sets an ArrayList<Items> to a either predefined ArrayList or a new Array List.
	 * 
	 * @param items
	 * @see ItemOperations
	 * @since 0.1
	 */
	public void setItems(ArrayList<Item> items) {
		this.saleItems = items;
	}
	
	/**
	 * This method returns an ArrayList<Items>.
	 * 
	 * @return
	 * @see ItemOperations
	 * @since 0.1
	 */
	@XmlElement(name = "item")
	public ArrayList<Item> getItems(){
		return saleItems;
	}
	
}

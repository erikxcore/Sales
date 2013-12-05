package me.ericbarber.jaxb.test;

import java.util.ArrayList;

//import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "me.ericbarber.jaxb.test")
public class items {

	//@XmlElementWrapper(name = "items")
	//@XmlElement(name = "item")
	private ArrayList<item> saleItems;

	public void setItems(ArrayList<item> items) {
		this.saleItems = items;
	}
	
	public ArrayList<item> getItems(){
		return saleItems;
	}
	
}

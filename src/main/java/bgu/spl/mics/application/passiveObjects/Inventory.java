package bgu.spl.mics.application.passiveObjects;

import java.util.*;
import java.io.*;


/**
 * Passive data-object representing the store inventory.
 * It holds a collection of {@link BookInventoryInfo} for all the
 * books in the store.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Inventory {

	private BookInventoryInfo [] inventoryInfo;
	private static Inventory instance = null;

	private Inventory(){
		this.inventoryInfo=null;
	}

	/**
     * Retrieves the single instance of this class.
     */
	public static Inventory getInstance() {
		if(instance == null) {
			instance = new Inventory();
		}
		return instance;
	}
	
	/**
     * Initializes the store inventory. This method adds all the items given to the store
     * inventory.
     * <p>
     * @param inventory 	Data structure containing all data necessary for initialization
     * 						of the inventory.
     */
	public void load (BookInventoryInfo[ ] inventory ) {
		this.inventoryInfo=inventory;
	}
	/**
     * Attempts to take one book from the store.
     * <p>
     * @param book 		Name of the book to take from the store
     * @return 	an {@link Enum} with options NOT_IN_STOCK and SUCCESSFULLY_TAKEN.
     * 			The first should not change the state of the inventory while the 
     * 			second should reduce by one the number of books of the desired type.
     */
	public OrderResult take (String book) {
		for (BookInventoryInfo b:inventoryInfo) {
			if(b.getBookTitle().equals(book)){
				if (b.getAmountInInventory()>0){
					b.reduceAmount();
					return OrderResult.SUCCESSFULLY_TAKEN;
				}
			}
		}
		return OrderResult.NOT_IN_STOCK;
	}
	
	
	
	/**
     * Checks if a certain book is available in the inventory.
     * <p>
     * @param book 		Name of the book.
     * @return the price of the book if it is available, -1 otherwise.
     */
	public int checkAvailabiltyAndGetPrice(String book) {
		for (BookInventoryInfo b : inventoryInfo) {
			if (b.getBookTitle().equals(book)) {
				if (b.getAmountInInventory() > 0) {
					return b.getPrice();
				}
			}
		}
		return  -1;
	}
	
	/**
     * 
     * <p>
     * Prints to a file name @filename a serialized object HashMap<String,Integer> which is a Map of all the books in the inventory. The keys of the Map (type {@link String})
     * should be the titles of the books while the values (type {@link Integer}) should be
     * their respective available amount in the inventory. 
     * This method is called by the main method in order to generate the output.
     */
	public void printInventoryToFile(String filename){
		//check if the functions works
		HashMap<String,Integer> tofile = new HashMap<>();
		for(int i =0;i<inventoryInfo.length;i++)
			tofile.put(inventoryInfo[i].getBookTitle(),inventoryInfo[i].getAmountInInventory());
		try{
			File file=new File(filename);
			FileOutputStream fos=new FileOutputStream(file);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(tofile);
			oos.flush();
			oos.close();
			fos.close();
		}catch(Exception e){}
	}

}

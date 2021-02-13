import java.util.ArrayList;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

/** ItemGenerator Class - representation of an item being created  */
public class ItemGenerator {
  /** The different number of items getting stored into an Arraylist */
  private ArrayList<Item> itemList;

  /** Checks if an instance of ItemGenerator was created */
  private static ItemGenerator igInstance = null;
  
  /**
   * Default Constructor - creates an item from getting the item from a txt file
   * and then adding it to the itemList which is an ArrayList
   * The constructor is private now because it is a singleton
   * Also checks if the file is found or not
   * @throws FileNotFoundException when the text file is not found
   */
  private ItemGenerator() {
    itemList = new ArrayList<Item>();
    try {
      Scanner read = new Scanner(new File("ItemList.txt"));
      while( read.hasNext() ) {
        String line = read.nextLine();
        String[] tokens = line.split(",");

        Item artifact = new Item(tokens[0], Integer.parseInt(tokens[1]), tokens[2].charAt(0));
        itemList.add(artifact);
      }
      read.close();

    }
    catch( FileNotFoundException fnf ) {
      System.out.println("File was not found");
    }
  }

  /**
   * Creates new instance of ItemGenerator if it doesn't already exist 
   * @return the instance of ItemGenerator that was created
   */
  public static ItemGenerator getInstance() {
    if( igInstance == null ) {
      igInstance = new ItemGenerator();
    }
    return igInstance;
  }

  /**
   * Generate a random item from the ArrayList of itemList
   * and calls the item clone() function to clone that item
   * @return the random cloned item from the itemList
   */
  public Item generateItem() {
    Random rand = new Random();
    int randNum = rand.nextInt(itemList.size());
    Item randItem = itemList.get(randNum).clone();
    return randItem;
  }

  /**
   * Retrieves a Health Potion from the itemList
   * by looping through the itemList to find that item
   * @return the potion that was found in the itemList
   */
  public Item getPotion() {
    Item potion = itemList.get(0);
    for( int i = 0; i < itemList.size(); i++ ) {
      if( itemList.get(i).getType() == 'p' ) {
        potion = itemList.get(i);
      }
    }
    return potion;
  }

  /**
   * Retrieves a Key from the itemList
   * by looping through the itemList to find that item
   * @return the key that was found in the itemList
   */
  public Item getKey() {
    Item key = itemList.get(0);
    for( int i = 0; i < itemList.size(); i++ ) {
      if( itemList.get(i).getType() == 'k' ) {
        key = itemList.get(i);
      }
    }
    return key;
  }
}

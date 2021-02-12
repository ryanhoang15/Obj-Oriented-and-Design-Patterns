/** Item Class - representation of a single item */
public class Item {
  /** The name of the item */
  private String name;

  /** The amount of gold the item is worth at the store */
  private int value;

  /** The type of item */
  private char type;

  /**
   * The constructor that creates the item
   * @param itemName is the name of the item
   * @param itemPrice is the price of the item
   * @param itemType is the type of item
   */
  public Item( String itemName, int itemPrice, char itemType ) {
    name = itemName;
    value = itemPrice;
    type = itemType;
  }

  /**
   * Retrieve the name of the item
   * @return the item's name
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the value of the item
   * @return how much the item is worth
   */
  public int getValue() {
    return value;
  }
  
  /**
   * Retrieves the type of item it is
   * @return a char representing the type of item
   */
  public char getType() {
    return type;
  }

  /**
   * Clones the item that is generate in the
   * itemGenerator class
   * @return the new Item that was cloned
   */
  public Item clone() {
    return new Item(this.getName(), this.getValue(), this.getType());
  }

}

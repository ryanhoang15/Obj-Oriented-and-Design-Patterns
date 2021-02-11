import java.util.ArrayList;
import java.util.Random;

/** 
 *  Hero Class - representation of a single hero, which
 *  extends from Entity and implements Magical
 */
public class Hero extends Entity implements Magical {
  /** The numbers of items the hero has */
  private ArrayList<Item> items;

  /** The map the hero is on */
  private Map map;

  /** The location of where the hero is on the map */
  private Point location;

  /** The amount of gold the hero has */
  private int heroGold;

  /**
   * This constructor creates a hero
   * @param heroName is the name of the hero
   * @param heroMap is the map the hero is on
   */
  public Hero( String heroName, Map heroMap ) {
    super( heroName, 50 );
    map = heroMap;
    location = map.findStart();
    items = new ArrayList<Item>();
    heroGold = 100;
  }

  /**
   * Retrieves the amount of gold the hero has
   * @return the amount of gold 
   */
  public int getGold() {
    return heroGold;
  }
  
  /**
   * Collects gold found and add it to the hero's gold amount
   * @param the gold to be collected
   */
  public void collectGold( int gold ) {
    heroGold = heroGold + gold;
  }

  /**
   * Chekcs if the hero can spend the gold
   * @param the goldAmount to be spent
   */
  public void spendGold( int goldAmount ) {
    if ( heroGold >= goldAmount ) {
      heroGold = heroGold - goldAmount;
    }
    else {
      System.out.println("Not enough gold to buy");
    }
  }

  /**
   * Checks if hero has an armor item and gets its index
   * @return index of where the armor is located, else returns -1
   */
  public int hasArmorItem() {
    for( int i = 0; i < items.size(); i++ ) {
      if( items.get(i).getType() == 'a' ) {
        return i;
      }
    }
    return -1; 
  }

  /**
   * Checks if hero has a key or not
   * @return true if hero has a key, otherwise false
   */
  public boolean hasKey() {
    for( int i = 0; i < items.size(); i++ ) {
      if( items.get(i).getType() == 'k' ) {
        return true;
      }
    }
    return false; 
  }

  /**
   * Finds the key in the hero's inventory to drop after
   * the heros used it
   */
  public void useKey() {
    for( int i = 0; i < items.size(); i++ ) {
      if( items.get(i).getType() == 'k' ) {
        dropItem(i);
        break;
      }
    }
  }

  /**
   * String representation of the Hero object
   * @return string representation of this Hero
   */
  @Override
  public String toString() {
    return super.toString() + "\nGold: " + getGold() + "\n" + itemsToString();
  }

  /**
   * Gives the Item's name that the Hero has
   * @return the name of the item
   */
  public String itemsToString() {
    String itemNames = "Inventory: ";
    for( int i = 0; i < items.size(); i++ ) {
      itemNames += "\n" + (i+1) + ". " + items.get(i).getName();
    }
    return itemNames;
  }

  /**
   * Retrieves the numbers of items the Hero has in his possession
   * @return how many items there are in the ArrayList of items
   */
  public int getNumItems() {
    return items.size();
  }

  /**
   * Determines if the Hero would like to pick the item up or not
   * @param item is the item the Hero is choosing to pick up or not
   * @return true if the Hero picks it up
   */
  public boolean pickUpItem( Item item ) {
    if( getNumItems() < 5 ){
      items.add(item);
      return true;
    }
    return false;
  }

  /**
   * Allows the Hero to drink the potion to heal himself
   * and then discard it by removing it from the ArrayList of items
   */ 
  public void drinkPotion() {
    int potionHealth = getMaxHp();
    heal(potionHealth);
    for( int i = 0; i < items.size(); i++ ) {
      if( items.get(i).getName().equals("Health Potion") ) {
        dropItem(i);
      }
    }
  }

  /**
   * Get rid of the specific item the Hero has in his inventory
   * @param index is the index spot of what item to get rid of
   * @return item being removed
   */
  public Item dropItem( int index ) {
    Item item = items.get(index);
    items.remove(index);
    return item;
  }

  /**
   * Determines if the Hero has a potion in his possession
   * @return true if the Hero has a potion, false otherwise
   */
  public boolean hasPotion() {
    for( int i = 0; i < items.size(); i++ ) {
      if( items.get(i).getName().equals("Health Potion") ) {
        return true;
      }
    }
    return false;
  }

  /**
   * Retrieve the location of the Hero
   * @return the exact location
   */
  public Point getLocation() {
    return location;
  }
  
  /**
   * Telling the Hero to go North which is up and checks if it is out of bounds
   * if it is out of bound the hero goes back to the same spot
   * @return the char of the new spot or "X" if it is out of bounds
   */
  public char goNorth() {
    location.move(-1,0);
    char north = map.getCharAtLoc(location);
    if( north == 'X' ) {
      goSouth();
    }
    return north;  
  }

  /**
   * Telling the Hero to go South which is down and checks if it is out of bounds
   * if it is out of bound the hero goes back to the same spot
   * @return the char of the new spot or "X" if it is out of bounds
   */
  public char goSouth() {
    location.move(1,0);
    char south = map.getCharAtLoc(location);
    if( south == 'X' ) {
      goNorth();
    }
    return south;
  }

  /**
   * Telling the Hero to go East which is right and checks if it is out of bounds
   * if it is out of bound the hero goes back to the same spot
   * @return the char of the new spot or "X" if it is out of bounds
   */
  public char goEast() {
    location.move(0,1);
    char east = map.getCharAtLoc(location);
    if( east == 'X' ) {
      goWest();
    }
    return east;
  }

  /**
   * Telling the Hero to go West which is left checks if it is out of bounds
   * if it is out of bound the hero goes back to the same spot
   * @return the new spot when the Hero moves to the left or "X" if it is out of bounds
   */
  public char goWest() {
    location.move(0,-1);
    char west = map.getCharAtLoc(location);
    if( west == 'X' ) {
      goEast();
    }
    return west;
  }

  /**
   * Attack the entity with a random physical damage
   * @param entity is the entity getting passed in to take the damage
   * @return the physical attack info that the entity took
   */
  @Override
  public String attack( Entity entity) {
    Random rand = new Random();
    int randDamage = rand.nextInt(8) + 2;
    
    String attack = super.getName() + " attacks " + entity.getName() + " for " 
                    + randDamage + " damage.";
                      
    entity.takeDamage(randDamage);
    return attack;
  }

  /**
   * Hero choose a Magical attack magic missile that deals
   * a random amount of damage
   * @param entity is the entity getting passed in to take the damage
   * @return the magic missile attack info that the entity took
   */
  @Override
  public String magicMissile( Entity entity ) {
    Random rand = new Random();
    int randDamage = rand.nextInt(8) + 2;
    
    String mMissile = super.getName() + " hits " + entity.getName() 
                      + " with Magic Missile for " + randDamage + " damage.";
    
    entity.takeDamage(randDamage);
    return mMissile;
  }

  /**
   * Hero choose a Magical attack fireball that deals
   * a random amount of damage
   * @param entity is the entity getting passed in to take the damage
   * @return the magical fireball attack info that the entity took
   */
  @Override
  public String fireball( Entity entity ) {
    Random rand = new Random();
    int randDamage = rand.nextInt(8) + 2;
    
    String fireball = super.getName() + " hits " + entity.getName() 
                      + " with Fireball for " + randDamage +  " damage.";
  
    entity.takeDamage(randDamage);
    return fireball;
  }

  /**
   * Hero choose a Magical attack thunderclap that deals
   * a random amount of damage
   * @param entity is the entity getting passed in to take the damage
   * @return the magic thunderclap attack info that the entity took
   */
  @Override
  public String thunderclap( Entity entity ) {
    Random rand = new Random();
    int randDamage = rand.nextInt(8) + 2;

    String thunderclap = super.getName() + " zaps " + entity.getName() 
                         + " with Thunderclap for " + randDamage + " damage.";

    entity.takeDamage(randDamage);
    return thunderclap;
  }
}
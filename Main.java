import java.util.Random;
/**
 * @author Ryan Hoang
 * @Class - CECS 277
 * @Due: 12/10/2020
 */

public class Main {
  /**
   * This is when the hero enters a store
   * The hero has the choice to buy an item, sell items, or exit
   * @param hero is the hero object getting passed in
   */
  public static void store( Hero hero ) {
    System.out.println("Welcome to the store");

    // This is to determine if the hero is in the store or not
    boolean isInStore = false;

    while( !isInStore ) {
      int heroMoney = hero.getGold();
      System.out.println("You have " + heroMoney + " gold.");
      System.out.println("1. Buy Health Potion for 25 Gold");
      System.out.println("2. Buy Key for 50 Gold");
      System.out.println("3. Sell item");
      System.out.println("4. Exit");
      int number = CheckInput.getIntRange(1,4);
      boolean hasSpace;

      /*
      Check first if the user has enough money and then checks
      if the hero has space in his inventory to buy the potion
      */
      if( number == 1 ) {
        Item potion = ItemGenerator.getInstance().getPotion();
        if(heroMoney >= 25) {
          hasSpace = hero.pickUpItem(potion);
          if(hasSpace) {
            hero.spendGold(25);
          }
          else{
            System.out.println("No space left in inventory!!!");
          }
        }
        else{
          System.out.println("Not enough money!!!");
        }
      }

      /*
      Check first if the user has enough money and then checks
      if the hero has space in his inventory to buy a key
      */
      else if( number == 2 ) {
        Item key = ItemGenerator.getInstance().getKey();
        if( heroMoney >= 50 ) {
          hasSpace = hero.pickUpItem(key);
          if( hasSpace ) {
            hero.spendGold(50);
          }
          else{
            System.out.println("No space left in inventory!!!");
          }
        }
        else{
          System.out.println("Not enough money!!!");
        }
      }

      /*
      This checks if the hero has any inventory to sell
      */
      else if( number == 3 ) {
        if( hero.getNumItems() > 0 ) {
          System.out.println(hero.itemsToString());
          System.out.println("Pick the item number to sell: ");
          int itemDrop = CheckInput.getIntRange(1, hero.getNumItems());
          Item sellItem = hero.dropItem(itemDrop - 1);
          hero.collectGold(sellItem.getValue());
        }
        else {
          System.out.println("No items in your inventory to sell!!!");
        }
      }
      else {
        isInStore = true;
      }
    }
  }

  /**
   * This is when the hero enters a monster room and a random enemy is generated
   * The hero fights the enemy until one of them does not have any health left
   * or if the hero decide to run away
   * @param hero is the hero object getting passed in
   * @param map is the map the hero is on
   * @param eg is the enemy generator that is going to generate the enemy
   * @param level is the level the hero is on 
   * @return true if the hero won, false otherwise
   */
  public static boolean monsterRoom( Hero hero, int level ) {
    // This is to determine if the hero beat the monster or not
    boolean isAlive = false;
    Enemy monster = EnemyGenerator.getInstance().generateEnemy(level);
    String monsterName = monster.getName();
    System.out.println("You've encountered a " + monsterName);
    
    /* 
    This while loop is looping until the hero or monster does not have any health left
    In this loop a menu will be display with fight, run, or use health potion if the hero
    has it or fight and run away will display if the hero doesn't have it
    The user pick their choice, if it is 1 than the hero and monster fight until one dies
    or if the user pick 2 than the hero runs away in a vaild direction, the user
    also has a option to drink a health potion if the hero has that item
    */
    while( hero.getHp() > 0 && monster.getHp() > 0 ) {
      System.out.println(monster);
      
      int pick;
      
      if( hero.hasPotion() ) {
        System.out.println("1. Fight\n2. Run Away\n3. Drink Health Potion");
        pick = CheckInput.getIntRange(1, 3); 
      }
      else {
        System.out.println("1. Fight\n2. Run Away");
        pick = CheckInput.getIntRange(1, 2);
      }
      
      if( pick == 1 ) {
        isAlive = fight(hero, monster);
      }
      else if( pick == 2 ) {
        Map.getInstance().reveal(hero.getLocation());
        Random rand = new Random();
        char randSpot = 'X';

        while( randSpot == 'X' ) {
          int randDirection = rand.nextInt(4);
          if( randDirection == 0 ) {
            randSpot = hero.goNorth();
          }
          else if( randDirection == 1 ) {
            randSpot = hero.goSouth();
          } 
          else if( randDirection == 2 ) {
            randSpot = hero.goEast();
          }
          else {
            randSpot = hero.goWest();
          }
        }
        return isAlive;
      }
      else{
        hero.drinkPotion();
      }
    }
    
    /*
    Once the result from the battle is over the flag variable will be assign true or false
    If it is true than the hero won and gets the monster's item but if the hero has 5 items 
    already than the user can drop an existing item for the new one or drop the new one.
    Then the monster spot on the map will get removed and an 'n' will be place there to represent
    nothing is there.
    If it is false than the hero lost and the monster won.
    */
    if( isAlive ) {
      System.out.println("You defeated the " + monsterName + "!");
      boolean isPickedUp = hero.pickUpItem(monster.getItem());
      if( isPickedUp ) {
        System.out.println("You received a " + monster.getItem().getName() + " from its corpse.");
      }
      else {
        System.out.println(monsterName + " dropped " + monster.getItem().getName() + " from its corpse.");
        System.out.println("You have reached the max capacity in your inventory");
        
        System.out.println("1. Select an item in your inventory to replace.");
        System.out.println("2. Drop the new item found.");
        int itemPick = CheckInput.getIntRange(1, 2);

        if( itemPick == 1 ) {
          System.out.println(hero.itemsToString());
          System.out.println("Pick the item number to drop: ");
          int itemDrop = CheckInput.getIntRange(1, 5);
          hero.dropItem(itemDrop - 1);
          hero.pickUpItem(monster.getItem()); 
        }
      }
      Map.getInstance().removeCharAtLoc(hero.getLocation());
    }
    else {
      System.out.println(monsterName + " defeated you.");
    }
    return isAlive;
  }

  /**
   * This is where the hero and enemy fight until one of them 
   * does not have any health left. The hero can fight a magical 
   * or physical enemy using magical attacks or his sword
   * @param hero is the hero getting passed in to fight
   * @param enemy is the enemy the hero has to fight
   * @return true if the hero won, otherwise false if the hero lost
   */
  public static boolean fight( Hero hero, Enemy enemy ) {
    // This is to determine if the hero won or not
    boolean hasWon = false;

    // Display the option of what type of attack to use
    System.out.println("1. Physical Attack\n2. Magical Attack");
    int attackType = CheckInput.getIntRange(1, 2);

    /*
    If the user pick 1 than the hero uses a physical attack and
    If the user pick 2 than a magic attack menu is display and the 
    user pick which magical attack the hero does.
    There is a loop to determine how many times the enemy can attack
    depending on the level and in the loop the monster's health is checked
    to see if the monster has any health left to attack back. Then it is checking
    if the hero has any armor to block the enemy's attack.
    It continues until one of them doesn't having any health left
    */
    if( attackType == 1 ) {
      System.out.println(hero.attack(enemy));
    }
    else {
      System.out.println(Magical.MAGIC_MENU);
      int magicPick = CheckInput.getIntRange(1, 3);

      if(magicPick == 1) {
        System.out.println(hero.magicMissile(enemy));
      } 
      else if(magicPick == 2) {
        System.out.println(hero.fireball(enemy));
      }
      else {
        System.out.println(hero.thunderclap(enemy));
      }
    }

    int armor = hero.hasArmorItem();
    if( enemy.getHp() > 0 ) {
      if( armor == -1 ) {
        System.out.println(enemy.getName() + enemy.attack(hero));
      }
      else {
        System.out.println("Enemy's Attack was blocked by Hero's Armor");
        hero.dropItem(armor);
      }
    }
    
    // Win gets assign true if the enemy has no health left and the hero still has health
    if( enemy.getHp() <= 0 && hero.getHp() > 0 ) {
      hasWon = true;
    }
    return hasWon;
  }

  /**
   * This is where the hero goes to finds an item
   * @param hero is the hero getting passed in to retrieve the item found
   * @param map is the current map the hero is on
   * @param ig is the item generator that is going to generate a random item
   */
  public static void itemRoom( Hero hero ){
    Item itemFound = ItemGenerator.getInstance().generateItem();
    int itemPick;
    int itemDrop;
    boolean isItemFound = hero.pickUpItem(itemFound);
    System.out.println("You found an item: " + itemFound.getName());

    /*
    This check if the hero's items in his inventory reached a max
    capacity of 5 and if it does than the user can drop an existing item 
    for the new one or drop the new one. Else the hero just pick up the item.
    The char spot the hero is standing on will be removed and replace with 'n' 
    if the hero choose to pick the item up or not.
    */
    if( !isItemFound ) {
      System.out.println("You have reached the max capacity in your inventory");

      System.out.println("1. Select an item in your inventory to replace.");
      System.out.println("2. Drop the new item found.");
      itemPick = CheckInput.getIntRange(1, 2);
      
      if( itemPick == 1 ) {
        System.out.println(hero.itemsToString());
        System.out.println("Pick the item number to drop: ");
        itemDrop = CheckInput.getIntRange(1, 5);
        hero.dropItem(itemDrop - 1);
        hero.pickUpItem(itemFound); 
      } 
    }

    Map.getInstance().removeCharAtLoc(hero.getLocation()); 
  }
  
  /**
   * Displaying the menu to the user can pick what direction to go
   * @return what direction the user wants the hero to move
   */
  public static int menu() {
    System.out.println("1. Go North");
    System.out.println("2. Go South");
    System.out.println("3. Go East");
    System.out.println("4. Go West");
    System.out.println("5. Quit");
    return CheckInput.getIntRange(1, 5);
  }

  public static void main(String[] args) {
    System.out.print("What is your name, traveler? ");
    String traveler = CheckInput.getString();

    // Keeps track of what map hero is on
    int mapCount = 1;

    Map map = Map.getInstance();
    map.loadMap(mapCount);

    Hero hero = new Hero(traveler, map);
    Point heroLocation = hero.getLocation();

    boolean isValid = false;

    int startLocation = 0;

    int gameLevel = 1;

    /*
    This while loop is used to play the game continously
    by asking the user what direction the user wants the hero to 
    go, or the user can quit the game by pressing 5 in the menu option.
    The game can end if the hero dies
    */
    while(!isValid){
      System.out.println(hero);

      map.reveal(heroLocation);
      
      map.displayMap(heroLocation);

      char start = map.getCharAtLoc(heroLocation);
      if( start == 's' && startLocation == 0) {
        startLocation++;
        System.out.println("Do you want to visit the store (y/n)");
        boolean enterStore = CheckInput.getYesNo();
        if( enterStore ) {
          store(hero);
        }
        map.displayMap(heroLocation);
      }
  
      int option = menu();
      char heroSpot = ' ';
      
      if( option == 1 ) {
        heroSpot = hero.goNorth();
      }
      else if( option == 2 ) {
        heroSpot = hero.goSouth();
      } 
      else if( option == 3 ) {
        heroSpot = hero.goEast();
      }
      else if( option == 4 ) {
        heroSpot = hero.goWest();
      }
      else {
        System.out.println("Game Over");
        isValid = true;
      }
      
      /*
      Depending of where the hero is on the map a char is going to
      be returned from that spot. The hero can either encounter a 
      monster and fight it or run away, find an item in an item room,
      end up back at the starting location, end up going out of bounds, or 
      entering an empty room. If the hero doesn't have any health left than the game is over.
      When the character is 'f' than the hero moves on to a new map and the hero's health gets replenished. 
      The map gets reset to the first map after every third level
      */
      if( heroSpot == 'm' ) {
        monsterRoom(hero, gameLevel);
        if( hero.getHp() <= 0 ) {
          System.out.println("Hero died");
          System.out.println("You lost");
          System.out.println("Game Over!!!");
          isValid = true;
        }
      }
      else if( heroSpot == 'i' ) {
        itemRoom(hero);
      }
      else if( heroSpot == 'n' ) {
        System.out.println("There was nothing here.");
      }
      else if( heroSpot == 's' ) {
        System.out.println("You're back at the start.");
        System.out.println("Do you want to visit the store (y/n)");
        boolean enterStore = CheckInput.getYesNo();
        if( enterStore ) {
          store(hero);
        }
      }
      else if( heroSpot == 'X' ) {
        System.out.println("You hit a wall, try again.");
      }
      else if( heroSpot == 'f'){
        boolean hasKey = hero.hasKey();
        if(hasKey){
          hero.useKey();
          mapCount++;
          gameLevel++;
          map.loadMap(mapCount);
          if( hero.getHp() < hero.getMaxHp() ) {
            hero.heal(hero.getMaxHp());
          }
          if( mapCount == 3 ) {
            mapCount = 0;
          }
          if( startLocation == 1 ) {
            startLocation = 0;
          }
        }
        else{
          System.out.println("Need a key to move on");
        }
      } // end if else statements
    } // end while loop
  } // end main 
} 

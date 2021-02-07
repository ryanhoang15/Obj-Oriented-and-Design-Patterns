import java.util.Random;

/** EnemyGenerator class - representation of an enemy being created */
public class EnemyGenerator {

  /** It's an instance of EnemyGenerator to deterine if it was created or not */
  private static EnemyGenerator egInstance = null;

  /**
   * This Constructor is for the EnemyGenerator where it is 
   * instantiated and is a singleton, the EnemyGenerator Constructor is called
   * in the getInstance() function
   */
  private EnemyGenerator() {
    
  }

  /**
   * Creates new instance of EnemyGenerator if it doesn't already exist 
   * @return the instance of EnemyGenerator that was created
   */
  public static EnemyGenerator getInstance() {
    if( egInstance == null ) {
      egInstance = new EnemyGenerator();
    }
    return egInstance;
  }

  /**
   * Generate a random enemy from one of the 4 base classes of Troll, Froglok, 
   * Orc,and Goblin. 
   * Then checks the level to decorate the enemy to be warrior or warlock. 
   * @param level is use to determine the level the player is on how many times to decorate the enemy
   * @return the random enemy that was created 
   */
  public Enemy generateEnemy( int level ) {
    Random rand = new Random();

    Enemy opponent;

    int randEnemy = rand.nextInt(4) + 1;
    if( randEnemy == 1 ) {
      opponent = new Troll();
    }
    else if(randEnemy == 2) {
      opponent = new Froglok();
    } 
    else if(randEnemy == 3) {
      opponent = new Orc();
    } 
    else {
      opponent = new Goblin();
    }

    int randDecorator = rand.nextInt(2) + 1;
    for( int i = level; i > 1; i-- ) {
      if(randDecorator == 1) {
        opponent = new Warrior(opponent);
      }
      else {
        opponent = new Warlock(opponent);
      }
    }
    return opponent;
  }
}

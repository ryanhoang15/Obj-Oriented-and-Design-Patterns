import java.util.Random;
/** 
 *  Warlock Class - representation of a warlock, which 
 *  extends from EnemyDecorator and implements Magical
 */
public class Warlock extends EnemyDecorator implements Magical {
  /**
   * Warlock constructor create the warlock enemy
   * @param opponent is enemy object being passed in to be decorated
   */
  public Warlock( Enemy opponent ) {
    super(opponent, opponent.getName().contains("Warlock") ? opponent.getName() : opponent.getName() + " Warlock", opponent.getMaxHp() + 1);
  }

  /**
   * Determine the random magical attack the Warlock Enemy uses
   * @param entity is the Entity itself getting passed in to be attacked
   * @return the name of the magical attack info
   */
  @Override
  public String attack( Entity entity ) {
    Random rand = new Random();
    int randNum = rand.nextInt(3);
    String attack = super.attack(entity) + "\n";
    
    if( randNum == 0 ) {
      attack = attack + magicMissile(entity);
    }

    else if( randNum == 1 ) {
      attack = attack + fireball(entity);
    }

    else {
      attack = attack + thunderclap(entity);
    }
    return attack;
  }

  
  /**
   * Warlock Enemy choose a Magical attack magic missile that deals
   * a random amount of damage.
   * @param entity is the entity getting passed in to take the damage
   * @return the magic missile attack info that the entity took
   */
  @Override
  public String magicMissile( Entity entity ) {
    Random rand = new Random();
    int randDamage = rand.nextInt(5) + 1; 
    
    String magicMissile = super.getName() + " hits " + entity.getName() 
                        + " with Magic Missile for " + randDamage +  " damage.";

    entity.takeDamage(randDamage);
    return magicMissile;
  }

  /**
   * Warlock Enemy choose a Magical attack fireball that deals
   * a random amount of damage.
   * @param entity is the entity getting passed in to take the damage
   * @return the fireball attack info that the entity took
   */
  @Override
  public String fireball( Entity entity ) {
    Random rand = new Random();
    int randDamage = rand.nextInt(5) + 1;
    
    String fireball = super.getName() + " hits " + entity.getName() 
                    + " with Fireball for " + randDamage +  " damage.";
    
    entity.takeDamage(randDamage);
    return fireball;
  }

  /**
   * Warlock Enemy choose a Magical attack thunderclap that deals
   * a random amount of damage.
   * @param entity is the entity getting passed in to take the damage
   * @return the thunderclap attack info that the entity took
   */
  @Override
  public String thunderclap( Entity entity ) {
    Random rand = new Random();
    int randDamage = rand.nextInt(5) + 1;
    
    String thunderclap = super.getName() + " zaps " + entity.getName() 
                       + " with Thunderclap for " + randDamage +  " damage.";
    
    entity.takeDamage(randDamage);
    return thunderclap;
  }
}

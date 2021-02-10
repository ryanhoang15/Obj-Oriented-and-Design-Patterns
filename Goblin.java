import java.util.Random;
/** 
 *  Goblin Class - representation of a single Goblin, which 
 *  extends from Enemy
 */
public class Goblin extends Enemy {
  /**
   * Goblin constructor create the goblin enemy
   */
  public Goblin() {
    super("Goblin", 8, ItemGenerator.getInstance().generateItem());
  }

  /**
   * Attack the entity with a random physical damage
   * @param entity is the entity getting passed int
   * @return the goblin attack info that the entity took
   */
  @Override
  public String attack( Entity entity ) {
    Random rand = new Random();

    int physicalDamage = rand.nextInt(6) + 1;

    String goblinAttack = " attacks " + entity.getName() 
                          + " for " + physicalDamage +  " damage.";

    entity.takeDamage(physicalDamage);
    return goblinAttack;
  }
}
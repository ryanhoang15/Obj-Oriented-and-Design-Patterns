import java.util.Random;
/** 
 *  Froglok Class - representation of a single Froglok, which 
 *  extends from Enemy
 */
public class Froglok extends Enemy {
  /**
   * Froglok constructor create the froglok enemy
   */
  public Froglok() {
    super("Froglok", 8, ItemGenerator.getInstance().generateItem());
  }

  /**
   * Attack the entity with a random physical damage
   * @param entity is the entity getting passed int
   * @return the froglok attack info that the entity took
   */
  @Override
  public String attack( Entity entity ) {
    Random rand = new Random();

    int physicalDamage = rand.nextInt(7) + 1;

    String froglokAttack = " attacks " + entity.getName() 
                           + " for " + physicalDamage +  " damage.";

    entity.takeDamage(physicalDamage);
    return froglokAttack;
  }
}
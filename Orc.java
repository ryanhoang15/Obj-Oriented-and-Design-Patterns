import java.util.Random;
/** 
 *  Orc Class - representation of a single orc, which 
 *  extends from Enemy
 */
public class Orc extends Enemy {
  /**
   * Orc constructor create the orc enemy
   */
  public Orc() { 
    super("Orc", 8, ItemGenerator.getInstance().generateItem());
  }

  /**
   * Attack the entity with a random physical damage
   * @param entity is the entity getting passed int
   * @return the orc attack info that the entity took
   */
  @Override
  public String attack( Entity entity ) {
    Random rand = new Random();

    int physicalDamage = rand.nextInt(3) + 1;

    String orcAttack = " attacks " + entity.getName() 
                       + " for " + physicalDamage +  " damage.";

    entity.takeDamage(physicalDamage);
    return orcAttack;
  }
}
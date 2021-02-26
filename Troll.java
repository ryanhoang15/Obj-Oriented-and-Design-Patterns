import java.util.Random;
/** 
 * Troll Class - representation of a single Troll, which 
 * extends from Enemy
 */
public class Troll extends Enemy {
  /**
   * Troll constructor create the troll enemy
   */
  public Troll() {
    super("Troll", 8, ItemGenerator.getInstance().generateItem()); 
  }

  /**
   * Attack the entity with a random physical damage
   * @param entity is the entity getting passed int
   * @return the troll attack info that the entity took
   */
  @Override
  public String attack( Entity entity ) {
    Random rand = new Random();

    int physicalDamage = rand.nextInt(5) + 1;

    String trollAttack = " attacks " + entity.getName() 
                         + " for " + physicalDamage +  " damage.";

    entity.takeDamage(physicalDamage);
    return trollAttack;
  }
}
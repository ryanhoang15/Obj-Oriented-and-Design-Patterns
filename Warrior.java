import java.util.Random;
/** 
 *  Warrior Class - representation of a warrior, which 
 *  extends from EnemyDecorator
 */
public class Warrior extends EnemyDecorator {
  /**
   * Warrior constructor create the warrior enemy
   * @param opponent is the enemy object that is passed in to be decorated
   */
  public Warrior( Enemy opponent ) {
    super(opponent, opponent.getName().contains("Warrior") ? opponent.getName() : opponent.getName() + " Warrior", opponent.getMaxHp() + 2);
  }

   public String attack( Entity entity ) {
    Random rand = new Random();

    int additionalDamage = rand.nextInt(5) + 1;
    entity.takeDamage(additionalDamage);  
    
    String warriorAttack = super.attack(entity) + "\n" + super.getName() + " attacks " 
                          + entity.getName() + " for " + additionalDamage +  " damage.";

    return warriorAttack;
  }
}
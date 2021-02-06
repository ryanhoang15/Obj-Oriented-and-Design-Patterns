import java.util.Random;
/** EnemyDecorator is an Abstract Representation of an EnemyDecorator which extends Entity */
public abstract class EnemyDecorator extends Enemy {
  /** The enemy to be decorated */
  private Enemy enemy;

  /**
   * EnemyDecorator constructor create the decorated enemy
   * @param enemy is the enemy object that is passed in
   * @param enemyDecoratedName is the name of the decorated enemy
   * @param enemyDecoratedHealth is how much health the decorated enemy has
   */
  public EnemyDecorator( Enemy enemy, String enemyDecoratedName, int enemyDecoratedHealth ){
    super(enemyDecoratedName, enemyDecoratedHealth, enemy.getItem());
    this.enemy = enemy;
  }

  /**
   * Attack the entity with a random physical damage
   * @param entity is the entity getting passed int
   * @return the physical attack info that the entity took
   */
  @Override
  public String attack( Entity entity ) {
    return this.enemy.attack(entity);
  }
}
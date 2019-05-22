package Entity;

import Audio.AudioPlayer;
import BlockMap.Map;
import java.io.PrintStream;
import java.util.HashMap;

public class Enemy
  extends MapObject
{
  protected HashMap<String, AudioPlayer> sfx;
  protected int mobID;
  protected int damageToFlinch;
  protected long flinchTimer;
  protected int timeToFlinch;
  protected int exp;
  protected int damage;
  protected long health;
  protected long maxHealth;
  protected int pushedDistance;
  protected int pushedSpeed;
  protected int currentAction;
  protected int random;
  protected int pushedDistanceSoFar;
  protected int xChaseDistance;
  protected int yChaseDistance;
  protected boolean chasing;
  protected boolean dead;
  protected boolean flinching;
  protected boolean dying;
  protected boolean beingPushed;
  protected Player player;
  
  public Enemy(Map bm, Player player)
  {
    super(bm);
    this.player = player;
    sfx = new HashMap<>();
  }
  
  public boolean isDead()
  {
    return dead;
  }
  
  public int getDamage()
  {
    return damage;
  }
  
  public int getExp()
  {
    return exp;
  }
  
  public void push()
  {
    if (player.getx() > x)
    {
      dx = (-pushedSpeed);
      pushedDistanceSoFar = ((int)(pushedDistanceSoFar + dx));
      if (pushedDistanceSoFar < -pushedDistance)
      {
        beingPushed = false;
        pushedDistanceSoFar = 0;
      }
    }
    if (player.getx() < x)
    {
      dx = pushedSpeed;
      pushedDistanceSoFar = ((int)(pushedDistanceSoFar + dx));
      if (pushedDistanceSoFar > pushedDistance)
      {
        beingPushed = false;
        pushedDistanceSoFar = 0;
      }
    }
  }
  
  public void hit(int damage)
  {
    if (dying) {
      return;
    }
    ((AudioPlayer)sfx.get("damage")).play();
    beingPushed = true;
    if (damage >= damageToFlinch)
    {
      flinching = true;
      flinchTimer = System.nanoTime();
    }
    System.out.println("damage = " + damage);
    health -= damage;
    if (health < 0) {
      health = 0;
    }
    if (health == 0)
    {
      dying = true;
      ((AudioPlayer)sfx.get("die")).play();
    }
  }
  
  public void update() {}
}

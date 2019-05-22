package Entity.Mob;

import Audio.AudioPlayer;
import Entity.Animation;
import Entity.Enemy;
import Entity.Player;
import BlockMap.Map;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

public class GreenSnail extends Enemy {
  private final int IDLE = 0;
  private final int WALKING = 1;
  private final int FALLING = 2;
  private final int FLINCHING = 3;
  private final int DYING = 4;
  private final int[] numOfFrames = { 1, 5, 1, 1, 7 };
  
  public GreenSnail(Map tm, Player player)
  {
    super(tm, player);
    mobID = 1;
    width = 44;
    height = 33;
    cwidth = 30;
    cheight = 20;
    moveSpeed = 0.05D;
    maxSpeed = 0.5D;
    stopSpeed = 0.05D;
    fallSpeed = 0.25000201D;
    maxFallSpeed = 30.0D;
    exp = 5;
    damage = 5;
    health = (maxHealth = 12);
    pushedDistance = 20;
    pushedSpeed = 2;
    xChaseDistance = 50;
    yChaseDistance = 50;
    damageToFlinch = 0;
    timeToFlinch = 1000;
    
    facingRight = true;
    right = true;
    flinching = false;
    chasing = false;
    
    currentAction = 1;
    
    animation = new Animation();
    animation.setFrames(Helpers.Content.mobMove[1], numOfFrames[1]);
    animation.setDelay(100);
    
    sfx.put("damage", new AudioPlayer("/SoundAffects/" + mobID + "Damage.mp3"));
    sfx.put("die", new AudioPlayer("/SoundAffects/" + mobID + "Die.mp3"));
  }
  
  public void checkIfChasing()
  {
    if ((player.getx() - x <= xChaseDistance) && (player.getx() - x >= -xChaseDistance) && 
      (player.gety() - y <= yChaseDistance) && (player.gety() - y >= -yChaseDistance)) {
      chasing = true;
    }
  }
  
  public void getNextPosition()
  {
    if (beingPushed)
    {
      push();
    }
    else if (right)
    {
      dx += moveSpeed;
      if (dx > maxSpeed) {
        dx = maxSpeed;
      }
    }
    else if (left)
    {
      dx -= moveSpeed;
      if (dx < -maxSpeed) {
        dx = (-maxSpeed);
      }
    }
    if (falling) {
      dy += fallSpeed;
    }
    if ((flinching) && (!beingPushed) && (dying)) {
      dx = 0.0D;
    }
    if (dying)
    {
      dx = 0.0D;dy = 0.0D;
    }
  }
  
  public void update()
  {
    getNextPosition();
    checkBlockMapCollision();
    setPosition(xtemp, ytemp);
    if ((dying) && (currentAction == 4))
    {
      if (animation.hasPlayedOnce()) {
        dead = true;
      }
      animation.update();
      return;
    }
    if ((flinching) && 
      ((System.nanoTime() - flinchTimer) / 1000000L >= 400L))
    {
      chasing = true;
      flinching = false;
    }
    if (chasing) {
      if (player.getx() - x > 0.0D)
      {
        right = true;
        left = false;
        facingRight = true;
      }
      else
      {
        right = false;
        left = true;
        facingRight = false;
      }
    }
    if (!chasing) {
      if ((right) && (dx == 0.0D))
      {
        right = false;
        left = true;
        facingRight = false;
      }
      else if ((left) && (dx == 0.0D))
      {
        left = false;
        right = true;
        facingRight = true;
      }
    }
    if (dying)
    {
      if (currentAction != 4)
      {
        currentAction = 4;
        animation.setFrames(Helpers.Content.mobDie[1], 7);
        animation.setDelay(200);
      }
    }
    else if (flinching)
    {
      if (currentAction != 3)
      {
        currentAction = 3;
        animation.setFrames(Helpers.Content.mobHit[1], 1);
        animation.setDelay(-1);
      }
    }
    else if (dy > 0.0D)
    {
      if (currentAction != 2)
      {
        currentAction = 2;
        animation.setFrames(Helpers.Content.mobStand[1], 1);
        animation.setDelay(1000);
      }
    }
    else if ((left) || (right))
    {
      if (currentAction != 1)
      {
        currentAction = 1;
        animation.setFrames(Helpers.Content.mobMove[1], 5);
        animation.setDelay(100);
      }
    }
    else if (currentAction != 0)
    {
      currentAction = 0;
      animation.setFrames(Helpers.Content.mobStand[1], 1);
      animation.setDelay(1000);
    }
    animation.update();
  }
  
  public void draw(Graphics2D g)
  {
    setMapPosition();
    super.draw(g);
    g.setColor(Color.RED);
    g.drawString("[" + health + "/" + maxHealth + "]", (int)x - (int)xmap - 30, (int)y - (int)ymap - height);
  }
}

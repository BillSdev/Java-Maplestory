package Entity.Mob;

import Audio.AudioPlayer;
import Entity.Animation;
import Entity.BlueSnailMissile;
import Entity.Enemy;
import Entity.Player;
import Entity.Projectile;
import Entity.RedSnailBullet;
import BlockMap.Map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

public class BlueSnail  extends Enemy {
  private final int IDLE = 0;
  private final int WALKING = 1;
  private final int FALLING = 2;
  private final int FLINCHING = 3;
  private final int DYING = 4;
  private final int[] numOfFrames = { 1, 4, 1, 1, 3 };
  
  private int shotDelay = 5000;
  private long lastTime;
  
  private ArrayList<Projectile> BList;
  
  public BlueSnail(Map tm, Player player, ArrayList<Projectile> bulletlist)
  {
    super(tm, player);
    mobID = 0;
    width = 56;
    height = 41;
    cwidth = 32;
    cheight = 34;
    moveSpeed = 0.05D;
    maxSpeed = 0.5D;
    stopSpeed = 0.05D;
    fallSpeed = 0.25000201D;
    maxFallSpeed = 30.0D;
    exp = 500;
    damage = 13;
    health = (maxHealth = 20);
    pushedDistance = 6;
    pushedSpeed = 1;
    xChaseDistance = 150;
    yChaseDistance = 50;
    damageToFlinch = 5;
    timeToFlinch = 1000;
    
    facingRight = true;
    right = true;
    flinching = false;
    chasing = false;
    
    BList = bulletlist;
    
    currentAction = 1;
    
    animation = new Animation();
    animation.setFrames(Helpers.Content.mobMove[0], numOfFrames[1]);
    animation.setDelay(100);
    
    lastTime = System.nanoTime();
    
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
    checkIfChasing();
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
        if((System.nanoTime() - lastTime)/1000000 > shotDelay) {
			BList.add(new BlueSnailMissile(map, this.x, this.y, 40, 20, -50, -50, 40, this.facingRight, player));
			lastTime = System.nanoTime();
		}
        
      }
      else
      {
        right = false;
        left = true;
        facingRight = false;
        if((System.nanoTime() - lastTime)/1000000 > shotDelay) {
			BList.add(new BlueSnailMissile(map, this.x, this.y, 40, 20, -50, -50, 40, this.facingRight, player));
			lastTime = System.nanoTime();
		}
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
        animation.setFrames(Helpers.Content.mobDie[0], 3);
        animation.setDelay(200);
      }
    }
    else if (flinching)
    {
      if (currentAction != 3)
      {
        currentAction = 3;
        animation.setFrames(Helpers.Content.mobHit[0], 1);
        animation.setDelay(-1);
      }
    }
    else if (dy > 0.0D)
    {
      if (currentAction != 2)
      {
        currentAction = 2;
        animation.setFrames(Helpers.Content.mobStand[0], 1);
        animation.setDelay(1000);
      }
    }
    else if ((left) || (right))
    {
      if (currentAction != 1)
      {
        currentAction = 1;
        animation.setFrames(Helpers.Content.mobMove[0], 4);
        animation.setDelay(100);
      }
    }
    else if (currentAction != 0)
    {
      currentAction = 0;
      animation.setFrames(Helpers.Content.mobStand[0], 1);
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

package Entity;

import Audio.AudioPlayer;
import Equipments.Equipment;
import Helpers.Content;
import BlockMap.Map;

import java.awt.Graphics2D;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Player
  extends MapObject
{
  private boolean attackedMonster;
  private int exp;
  private int maxExp;
  private int level;
  private int health;
  private int maxHealth;
  private int mana;
  private int maxMana;
  private int originalDamage;
  private int damage;
  private boolean dead;
  private boolean flinching;           //short invincibility  after getting hit.
  private long flinchTimer;
  private boolean makingFace;
  private int faceTimer;
  private long currentFaceTime;
  private int meleeAttackDistance;
  private boolean meleeAttacking;
  private double originalMaxSpeed;
  private double originalJumpStart;
  private Equipment currentTop;
  private Equipment currentBottom;
  private int currentBody;
  private int currentFace;
  private int currentSmile;
  private final int[] numFrame = { 3, 4, 1, 1, 2 };
  Animation[] animationList = new Animation[7];
  private final int BODY = 0;
  private final int ARM = 1;
  private final int TOP_ARM = 2;
  private final int TOP_BODY = 3;
  private final int BOTTOM = 4;
  private final int FACE = 5;
  private final int HEAD = 6;
  private final int IDLE = 0;
  private final int WALKING = 1;
  private final int JUMPING = 2;
  private final int FALLING = 3;
  private final int MELEEATTACKING = 4;
  private HashMap<String, AudioPlayer> sfx;
  private int xface;
  private int yface;
  
  private long lastManaRegenTime;
  
  private int cannonAngle;
  private int cannonDamage;
  private int cannonVector;
  private double cannonGravity;
  
  public Player(Map bm)
  {
    super(bm);
    
    this.xface = 0;
    this.yface = 0;
    
    this.width = 46;
    this.height = 72;
    this.cheight = 60;
    this.cwidth = 30;
    this.moveSpeed = 0.5D;
    this.maxSpeed = (this.originalMaxSpeed = 2.4D);
    this.stopSpeed = 0.3D;
    this.fallSpeed = 0.5000201D;
    this.maxFallSpeed = 30.0D;
    this.jumpStart = (this.originalJumpStart = -9.0D);
    this.stopJumpSpeed = 0.50002D;
    this.faceTimer = 4000;
    this.level = 1;
    this.exp = 0;
    this.health = 50;
    this.maxHealth = 50;
    this.mana = 100;
    this.maxMana = 100;
    this.maxExp = 200;
    this.meleeAttackDistance = 150;
    this.damage = (this.originalDamage = 10);
    
    this.cannonAngle = 0;
    this.cannonDamage = 10;
    this.cannonVector = 20;
    this.cannonGravity = 0.5;
    
    this.facingRight = true;
    this.makingFace = false;
    this.meleeAttacking = false;
    this.attackedMonster = false;
    

    this.currentAction = 0;
    


    this.currentBottom = Helpers.Content.equipArray[0];
    this.currentTop = Helpers.Content.equipArray[0];
    this.currentBody = 0;
    this.currentFace = 0;
    this.currentSmile = 0;
    for (int i = 0; i < this.animationList.length; i++) {
      this.animationList[i] = new Animation();
    }
    this.animationList[0].setFrames(Helpers.Content.bodyStand[this.currentBody], this.numFrame[0]);
    this.animationList[3].setFrames(this.currentTop.getBodyStand(), this.numFrame[0]);
    this.animationList[4].setFrames(this.currentBottom.getStand(), this.numFrame[0]);
    this.animationList[1].setFrames(Helpers.Content.armStand[this.currentBody], this.numFrame[0]);
    this.animationList[2].setFrames(this.currentTop.getArmStand(), this.numFrame[0]);
    this.animationList[5].setFace(Helpers.Content.Faces[this.currentFace], this.currentSmile);
    this.animationList[6].setFrames(Helpers.Content.headStand[this.currentBody], this.numFrame[0]);
    
    this.animationList[2].setDelay(400);
    this.animationList[3].setDelay(400);
    this.animationList[1].setDelay(400);
    this.animationList[0].setDelay(400);
    this.animationList[4].setDelay(400);
    this.animationList[5].setDelay(-1);
    this.animationList[6].setDelay(400);
    
    lastManaRegenTime = System.nanoTime();
    
    this.sfx = new HashMap();
    this.sfx.put("jump", new AudioPlayer("/SoundAffects/Jump.mp3"));
    this.sfx.put("attack", new AudioPlayer("/SoundAffects/attack.mp3"));
    this.sfx.put("levelUP", new AudioPlayer("/SoundAffects/levelUp.mp3"));
  }
  
  public double getHealth() {  return this.health;  }
  
  public double getMaxHealth() {  return this.maxHealth;  }
  
  public double getMana() {  return this.mana;  }
  
  public double getMaxMana() {  return this.maxMana;  }
  
  public boolean getMakingFace() {  return this.makingFace;  }
  
  public Equipment getCurrentTop() {  return this.currentTop;  }
  
  public boolean isFacingRight() {  return facingRight;  }
  
  public Equipment getCurrentBottom() {  return this.currentBottom;  }
  
  public int getCurrentBody() {  return this.currentBody;  }
  
  public int getCurrentFace() {  return this.currentFace;  }
  
  public int getLevel() {  return this.level;  }
  
  public double getEXP() {  return this.exp;  }
  
  public int getCannonAngle() {  return this.cannonAngle;  }
  
  public int getCannonVector() {  return this.cannonVector;  }
  
  public int getCannonDamage() {  return this.cannonDamage;  }
  
  public double getCannonGravity() {  return this.cannonGravity; }
  
  public double getMaxExp() {  return this.maxExp;  }
  
  public void addExp(int exp) {  this.exp += exp;  }
  
  public void setMeleeAttacking()
  {
    if (this.currentAction != 4) {
      this.meleeAttacking = true;
    }
  }
  
  public void setHealth(int h) {  this.health = h;  }
  
  public void setMana(int m) {  this.mana = m;  }
  
  public void setMaxHealth(int mh) {  this.maxHealth = mh;  }
  
  public void setMaxMana(int mm) {  this.maxMana = mm;  }
  
  public void setCurrentSmile(int face) {  this.currentSmile = face;  }
  
  public void setCurrentFaceTime(long time) {  this.currentFaceTime = time;  }
  
  public void setMakingFace(boolean b) {  this.makingFace = b; }
  
  public void setCurrentTop(Equipment e) {  this.currentTop = e;  }
  
  public void setCurrentFace(int i) {  this.currentFace = i;  }
  
  public void setCurrentBottom(Equipment e) {  this.currentBottom = e;  }
  
  public void setCurrentBody(int i) {  this.currentBody = i;  }
  
  public void setCurrentAction(int i) {  this.currentAction = i;  }
  
  public void setCannonAngle(int a) {  this.cannonAngle = a;  }
  
  public void setCannonVector(int v) {  this.cannonVector = v;  }
  
  public void setCannonDamage(int d) {  this.cannonDamage = d;  }
  
  public void setCannonGravity(double g) {  this.cannonGravity = g;  }
  
  public void hit(int damage)
  {
    System.out.println("Damage taken = " + damage);
    if (this.flinching) {
      return;
    }
    this.health -= damage;
    if (this.health < 0) {
      this.health = 0;
    }
    if (this.health == 0) {
      this.dead = true;
    }
    this.flinching = true;
    this.flinchTimer = System.nanoTime();
    if (this.facingRight)
    {
      this.dy = -3.0D;
      this.dx = -5.0D;
    }
    else
    {
      this.dy = -3.0D;
      this.dx = 5.0D;
    }
  }
  
  public void levelUp()
  {
    ((AudioPlayer)this.sfx.get("levelUP")).play();
    this.maxHealth = ((int)(this.maxHealth * 1.2D));
    this.maxMana = ((int)(this.maxMana * 1.05D));
    this.exp = 0;
    this.level += 1;
    this.maxExp = Helpers.Content.expTable[(this.level + 1)];
    this.health = this.maxHealth;
    this.mana = this.maxMana;
    System.out.println("Level Up!!!");
  }
  
  public void checkAttack(ArrayList<Enemy> enemies)
  {
    for (int i = 0; i < enemies.size(); i++)
    {
      Enemy e = (Enemy)enemies.get(i);
      if ((this.meleeAttacking) && (!this.attackedMonster) && (this.animationList[6].getImage().equals(Helpers.Content.headStabO2[0][1]))) {
        if (this.facingRight)
        {
          if ((e.getx() > this.x) && 
            (this.x + this.meleeAttackDistance > e.getx()) && 
            (e.gety() > this.y - this.height / 2) && 
            (e.gety() < this.y + this.height / 2))
          {
            e.hit((int)(0.7D * this.damage + (int)(Math.random() * (0.6D * this.damage))));
            this.attackedMonster = true;
          }
        }
        else if ((e.getx() < this.x) && 
          (e.getx() > this.x - this.meleeAttackDistance) && 
          (e.gety() > this.y - this.height / 2) && 
          (e.gety() < this.y + this.height / 2))
        {
          e.hit((int)(0.7D * this.damage + (int)(Math.random() * (0.6D * this.damage))));
          this.attackedMonster = true;
        }
      }
      if (intersects(e))
      {
        hit((int)(0.7D * e.getDamage() + (int)(Math.random() * (0.6D * e.getDamage()))) - (this.currentTop.getDefenseIncrease() + this.currentBottom.getDefenseIncrease()));
        //System.out.println("@@@");
      }
    }
  }
  
  
  public void getNextPosition()
  {
    if (this.left)
    {
      this.dx -= this.moveSpeed;
      if (this.dx < -this.maxSpeed) {
        this.dx = (-this.maxSpeed);
      }
    }
    else if (this.right)
    {
      this.dx += this.moveSpeed;
      if (this.dx > this.maxSpeed) {
        this.dx = this.maxSpeed;
      }
    }
    else
    {
      if (this.dx > 0.0D)
      {
        this.dx -= this.stopSpeed;
        if (this.dx < 0.0D) {
          this.dx = 0.0D;
        }
      }
      if (this.dx < 0.0D)
      {
        this.dx += this.stopSpeed;
        if (this.dx > 0.0D) {
          this.dx = 0.0D;
        }
      }
    }
    if ((this.jumping) && (!this.falling))
    {
      ((AudioPlayer)this.sfx.get("jump")).play();
      this.dy = this.jumpStart;
      this.falling = true;
    }
    if ((this.currentAction == 4) && (!this.falling) && (!this.jumping)) {
      this.dx = 0.0D;
    }
    if (this.falling)
    {
      this.dy += this.fallSpeed;
      if (this.dy > 0.0D) {
        this.jumping = false;
      }
      if ((this.dy < 0.0D) && (this.jumping))
      {
        this.dy -= this.fallSpeed;this.dy += this.stopJumpSpeed;
      }
      if (this.dy > this.maxFallSpeed) {
        this.dy = this.maxFallSpeed;
      }
    }
  }
  
  public void update()
  {
    this.damage = (this.currentTop.getDamageIncrease() + this.currentBottom.getDamageIncrease() + this.originalDamage);
    this.jumpStart = (this.currentTop.getJumpIncrease() + this.currentBottom.getJumpIncrease() + this.originalJumpStart);
    this.maxSpeed = (this.currentTop.getSpeedIncrease() + this.currentBottom.getSpeedIncrease() + this.originalMaxSpeed);
    
    getNextPosition();
    checkBlockMapCollision();
    setPosition(this.xtemp, this.ytemp);
    if ((this.currentAction == 4) && 
      (this.animationList[0].hasPlayedOnce()))
    {
      this.meleeAttacking = false;this.attackedMonster = false;
    }
    if (this.meleeAttacking)
    {
      if (this.currentAction != 4)
      {
        ((AudioPlayer)this.sfx.get("attack")).play();
        this.currentAction = 4;
        
        this.animationList[3].setFrames(this.currentTop.getBodyStabO2(), this.numFrame[4]);
        this.animationList[2].setFrames(this.currentTop.getArmStabO2(), this.numFrame[4]);
        this.animationList[0].setFrames(Helpers.Content.bodyStabO2[this.currentBody], this.numFrame[4]);
        this.animationList[1].setFrames(Helpers.Content.armStabO2[this.currentBody], this.numFrame[4]);
        this.animationList[4].setFrames(this.currentBottom.getStabO2(), this.numFrame[4]);
        this.animationList[6].setFrames(Helpers.Content.headStabO2[this.currentBody], this.numFrame[4]);
        
        this.animationList[2].setDelay(300);
        this.animationList[3].setDelay(300);
        this.animationList[1].setDelay(300);
        this.animationList[0].setDelay(300);
        this.animationList[4].setDelay(300);
        this.animationList[6].setDelay(300);
      }
    }
    else if (this.dy > 0.0D)
    {
      if (this.currentAction != 3)
      {
        this.currentAction = 3;
        

        this.animationList[2].setFrames(this.currentTop.getArmJumpFall(), this.numFrame[2]);
        this.animationList[3].setFrames(this.currentTop.getBodyJumpFall(), this.numFrame[2]);
        this.animationList[1].setFrames(Helpers.Content.armJumpFall[this.currentBody], this.numFrame[2]);
        this.animationList[0].setFrames(Helpers.Content.bodyJumpFall[this.currentBody], this.numFrame[2]);
        this.animationList[4].setFrames(this.currentBottom.getJumpFall(), this.numFrame[2]);
        this.animationList[6].setFrames(Helpers.Content.headJumpFall[this.currentBody], this.numFrame[2]);
        
        this.animationList[2].setDelay(-1);
        this.animationList[3].setDelay(-1);
        this.animationList[1].setDelay(-1);
        this.animationList[0].setDelay(-1);
        this.animationList[4].setDelay(-1);
        this.animationList[6].setDelay(-1);
        
        this.width = 47;
      }
    }
    else if (this.dy < 0.0D)
    {
      if (this.currentAction != 2)
      {
        this.currentAction = 2;
        

        this.animationList[2].setFrames(this.currentTop.getArmJumpFall(), this.numFrame[2]);
        this.animationList[3].setFrames(this.currentTop.getBodyJumpFall(), this.numFrame[2]);
        this.animationList[1].setFrames(Helpers.Content.armJumpFall[this.currentBody], this.numFrame[2]);
        this.animationList[0].setFrames(Helpers.Content.bodyJumpFall[this.currentBody], this.numFrame[2]);
        this.animationList[4].setFrames(this.currentBottom.getJumpFall(), this.numFrame[2]);
        this.animationList[6].setFrames(Helpers.Content.headJumpFall[this.currentBody], this.numFrame[2]);
        
        this.animationList[2].setDelay(-1);
        this.animationList[3].setDelay(-1);
        this.animationList[1].setDelay(-1);
        this.animationList[0].setDelay(-1);
        this.animationList[4].setDelay(-1);
        this.animationList[6].setDelay(-1);
        this.width = 47;
      }
    }
    else if ((this.left) || (this.right))
    {
      if (this.currentAction != 1)
      {
        this.currentAction = 1;
        

        this.animationList[2].setFrames(this.currentTop.getArmMove(), this.numFrame[1]);
        this.animationList[3].setFrames(this.currentTop.getBodyMove(), this.numFrame[1]);
        this.animationList[1].setFrames(Helpers.Content.armWalk[this.currentBody], this.numFrame[1]);
        this.animationList[0].setFrames(Helpers.Content.bodyWalk[this.currentBody], this.numFrame[1]);
        this.animationList[4].setFrames(this.currentBottom.getMove(), this.numFrame[1]);
        this.animationList[6].setFrames(Helpers.Content.headWalk[this.currentBody], this.numFrame[1]);
        
        this.animationList[2].setDelay(150);
        this.animationList[3].setDelay(150);
        this.animationList[1].setDelay(150);
        this.animationList[0].setDelay(150);
        this.animationList[4].setDelay(150);
        this.animationList[6].setDelay(150);
        this.width = 47;
      }
    }
    else if (this.currentAction != 0)
    {
      this.currentAction = 0;
      

      this.animationList[0].setFrames(Helpers.Content.bodyStand[this.currentBody], this.numFrame[0]);
      this.animationList[3].setFrames(this.currentTop.getBodyStand(), this.numFrame[0]);
      this.animationList[4].setFrames(this.currentBottom.getStand(), this.numFrame[0]);
      this.animationList[1].setFrames(Helpers.Content.armStand[this.currentBody], this.numFrame[0]);
      this.animationList[2].setFrames(this.currentTop.getArmStand(), this.numFrame[0]);
      this.animationList[5].setFace(Helpers.Content.Faces[this.currentFace], this.currentSmile);
      this.animationList[6].setFrames(Helpers.Content.headStand[this.currentBody], this.numFrame[0]);
      
      this.animationList[2].setDelay(400);
      this.animationList[3].setDelay(400);
      this.animationList[1].setDelay(400);
      this.animationList[0].setDelay(400);
      this.animationList[4].setDelay(400);
      this.animationList[5].setDelay(-1);
      this.animationList[6].setDelay(400);
      
      this.width = 47;
    }
    if (this.flinching)
    {
      long elapsed = (System.nanoTime() - this.flinchTimer) / 1000000L;
      if (elapsed > 1000L) {
        this.flinching = false;
      }
    }
    this.animationList[5].setFace(Helpers.Content.Faces[this.currentFace], this.currentSmile);
    
    this.animationList[2].update();
    this.animationList[3].update();
    this.animationList[1].update();
    this.animationList[0].update();
    this.animationList[4].update();
    this.animationList[5].update();
    this.animationList[6].update();
    if ((System.nanoTime() - this.currentFaceTime) / 1000000L > this.faceTimer)
    {
      this.currentSmile = 0;
      this.makingFace = false;
    }
    if (this.dead) {
      if(level > 1) {
    	  level--;
      }
      health = maxHealth;
      dead = false;
    }
    if (this.health > this.maxHealth) {
      this.health = this.maxHealth;
    }
    if (this.mana > this.maxMana) {
      this.mana = this.maxMana;
    }
    if (this.currentAction != 4)
    {
      if (this.right) {
        this.facingRight = true;
      }
      if (this.left) {
        this.facingRight = false;
      }
    }
    if (this.exp >= this.maxExp) {
      levelUp();
    }
    if (this.animationList[6].getImage().equals(Helpers.Content.headStabO2[0][1]))
    {
      this.xface = -6;this.yface = 6;
    }
    else if (this.animationList[6].getImage().equals(Helpers.Content.headStabO2[0][0]))
    {
      this.xface = 1;this.yface = 1;
    }
    else
    {
      this.xface = 0;this.yface = 0;
    }
    
    if((System.nanoTime() - lastManaRegenTime)/100 >= 100000000) {
    	//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    	this.setMana(maxMana);
    	lastManaRegenTime = System.nanoTime();
    }
  }
  
  public void draw(Graphics2D g)
  {
	  
    setMapPosition();
    if (!this.facingRight)
    {
      g.drawImage(this.animationList[0].getImage(), (int)(this.x - this.xmap - this.width / 2), (int)(this.y - this.ymap - this.height / 2), null);
      g.drawImage(this.animationList[4].getImage(), (int)(this.x - this.xmap - this.width / 2), (int)(this.y - this.ymap - this.height / 2), null);
      g.drawImage(this.animationList[3].getImage(), (int)(this.x - this.xmap - this.width / 2), (int)(this.y - this.ymap - this.height / 2), null);
      g.drawImage(this.animationList[6].getImage(), (int)(this.x - this.xmap - this.width / 2), (int)(this.y - this.ymap - this.height / 2), null);
      g.drawImage(this.animationList[1].getImage(), (int)(this.x - this.xmap - this.width / 2), (int)(this.y - this.ymap - this.height / 2), null);
      g.drawImage(this.animationList[2].getImage(), (int)(this.x - this.xmap - this.width / 2), (int)(this.y - this.ymap - this.height / 2), null);
      g.drawImage(this.animationList[5].getImage(), (int)(this.x - this.xmap - this.width / 2 - 2.0D + this.xface), (int)(this.y - this.ymap - this.height / 2 + this.yface), null);
     
    }
    else
    {
      g.drawImage(this.animationList[0].getImage(), (int)(this.x - this.xmap - this.width / 2 + this.width), (int)(this.y - this.ymap - this.height / 2), -this.width, this.height, null);
      g.drawImage(this.animationList[4].getImage(), (int)(this.x - this.xmap - this.width / 2 + this.width), (int)(this.y - this.ymap - this.height / 2), -this.width, this.height, null);
      g.drawImage(this.animationList[3].getImage(), (int)(this.x - this.xmap - this.width / 2 + this.width), (int)(this.y - this.ymap - this.height / 2), -this.width, this.height, null);
      g.drawImage(this.animationList[6].getImage(), (int)(this.x - this.xmap - this.width / 2 + this.width), (int)(this.y - this.ymap - this.height / 2), -this.width, this.height, null);
      g.drawImage(this.animationList[1].getImage(), (int)(this.x - this.xmap - this.width / 2 + this.width), (int)(this.y - this.ymap - this.height / 2), -this.width, this.height, null);
      g.drawImage(this.animationList[2].getImage(), (int)(this.x - this.xmap - this.width / 2 + this.width), (int)(this.y - this.ymap - this.height / 2), -this.width, this.height, null);
      g.drawImage(this.animationList[5].getImage(), (int)(this.x - this.xmap - this.width / 2 + this.width + 2.0D - this.xface), (int)(this.y - this.ymap - this.height / 2 + this.yface), -this.width, this.height, null);
      
      
    }
  }
}

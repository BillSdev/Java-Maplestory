package Equipments;

import java.awt.image.BufferedImage;

public class Equipment
{
  protected BufferedImage[] armStand;
  protected BufferedImage[] armMove;
  protected BufferedImage[] armJumpFall;
  protected BufferedImage[] armStabO2;
  protected BufferedImage[] bodyStand;
  protected BufferedImage[] bodyMove;
  protected BufferedImage[] bodyJumpFall;
  protected BufferedImage[] bodyStabO2;
  protected BufferedImage[] stand;
  protected BufferedImage[] move;
  protected BufferedImage[] jumpFall;
  protected BufferedImage[] stabO2;
  protected int equipID;
  protected int damageIncrease;
  protected int defenseIncrease;
  protected double jumpIncrease;
  protected double speedIncrease;
  
  public int getEquipID()
  {
    return equipID;
  }
  
  public int getDamageIncrease()
  {
    return damageIncrease;
  }
  
  public int getDefenseIncrease()
  {
    return defenseIncrease;
  }
  
  public double getJumpIncrease()
  {
    return jumpIncrease;
  }
  
  public double getSpeedIncrease()
  {
    return speedIncrease;
  }
  
  public BufferedImage[] getStand()
  {
    return stand;
  }
  
  public BufferedImage[] getMove()
  {
    return move;
  }
  
  public BufferedImage[] getJumpFall()
  {
    return jumpFall;
  }
  
  public BufferedImage[] getStabO2()
  {
    return stabO2;
  }
  
  public BufferedImage[] getArmStand()
  {
    return armStand;
  }
  
  public BufferedImage[] getArmMove()
  {
    return armMove;
  }
  
  public BufferedImage[] getArmJumpFall()
  {
    return armJumpFall;
  }
  
  public BufferedImage[] getArmStabO2()
  {
    return armStabO2;
  }
  
  public BufferedImage[] getBodyStand()
  {
    return bodyStand;
  }
  
  public BufferedImage[] getBodyMove()
  {
    return bodyMove;
  }
  
  public BufferedImage[] getBodyJumpFall()
  {
    return bodyJumpFall;
  }
  
  public BufferedImage[] getBodyStabO2()
  {
    return bodyStabO2;
  }
}

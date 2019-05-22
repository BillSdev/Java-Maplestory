package Equipments;

import Helpers.Content;

public class E101
  extends Equipment
{
  public E101()
  {
    equipID = 101;
    
    stand = Content.loadImageArray("/Character/Equipments/" + equipID + "/stand/", 3);
    move = Content.loadImageArray("/Character/Equipments/" + equipID + "/walk/", 4);
    jumpFall = Content.loadImageArray("/Character/Equipments/" + equipID + "/jump/", 1);
    stabO2 = Content.loadImageArray("/Character/Equipments/" + equipID + "/stabO2/", 2);
    
    damageIncrease = 10;
    defenseIncrease = 5;
    jumpIncrease = 0.0;
    speedIncrease = 0.0;
  }
}

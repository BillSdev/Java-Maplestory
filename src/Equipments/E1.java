package Equipments;

import Helpers.Content;

public class E1
  extends Equipment
{
  public E1()
  {
    equipID = 1;
    
    armStand = Content.loadImageArray("/Character/Equipments/" + equipID + "/stand/arm", 3);
    armMove = Content.loadImageArray("/Character/Equipments/" + equipID + "/walk/arm", 4);
    armJumpFall = Content.loadImageArray("/Character/Equipments/" + equipID + "/jump/arm", 1);
    armStabO2 = Content.loadImageArray("/Character/Equipments/" + equipID + "/stabO2/arm", 2);
    
    bodyStand = Content.loadImageArray("/Character/Equipments/" + equipID + "/stand/body", 3);
    bodyMove = Content.loadImageArray("/Character/Equipments/" + equipID + "/walk/body", 4);
    bodyJumpFall = Content.loadImageArray("/Character/Equipments/" + equipID + "/jump/body", 1);
    bodyStabO2 = Content.loadImageArray("/Character/Equipments/" + equipID + "/stabO2/body", 2);
    
    damageIncrease = 0;
    defenseIncrease = 0;
    jumpIncrease = -7.0;
    speedIncrease = 2.0;
  }
}

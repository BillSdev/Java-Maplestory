package Entity;

import Helpers.Content;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class HUD
{
  private String expString;
  private Player player;
  private Font levelFont;
  private Font gaugeFont;
  private double widthRectHp;
  private double widthRectMp;
  private double widthRectExp;
  private int maxExpRectWidth;
  private int maxMpRectWidth;
  private int maxHpRectWidth;
  private double tween;
  private Font creditFont;
  
  public HUD(Player player)
  {
    this.player = player;
    levelFont = new Font("Arial", 1, 20);
    gaugeFont = new Font("Arial", 1, 10);
    creditFont = new Font("Arial", 1, 50);
    maxExpRectWidth = 309;
    maxHpRectWidth = (maxMpRectWidth = 139);
    tween = 0.1;
  }
  
  public void update()
  {
    widthRectExp += (maxExpRectWidth * (player.getEXP() / player.getMaxExp()) - widthRectExp) * tween;
    widthRectHp += (maxHpRectWidth * (player.getHealth() / player.getMaxHealth()) - widthRectHp) * tween;
    widthRectMp += (maxMpRectWidth * (player.getMana() / player.getMaxMana()) - widthRectMp) * tween;
    expString = String.format(player.getEXP() + "[%.2f", player.getEXP() / player.getMaxExp() * 100 );
  }
  
  public void draw(Graphics2D g)
  {
    g.drawImage(Content.backGround, 0, 684, null);
    g.drawImage(Content.levelBar, 0, 728, null);
    g.drawImage(Content.chat, 0, 709, null);
    g.setColor(Color.yellow);
    g.drawString(""+player.getLevel(), 40, 758);
    g.drawImage(Content.gaugeBackGround, 215, 735, null);
    g.fillRect(244, 754, (int)widthRectExp, 11);
    g.setColor(Color.RED);
    g.fillRect(244, 739, (int)widthRectHp, 11);
    g.setColor(Color.blue);
    g.fillRect(415, 739, (int)widthRectMp, 11);
    g.drawImage(Content.gaugeTop, 215, 735, null);
    
    
    g.setFont(gaugeFont);
    g.setColor(Color.WHITE);
    g.drawString("[" + player.getHealth() + "/" + player.getMaxHealth() + "]", 322, 747);
    g.drawString("[" + player.getMana() + "/" + player.getMaxMana() + "]", 480, 747);
    g.drawString(expString + "%]", 470, 763);
    g.setColor(Color.RED);
    g.setFont(creditFont);
    g.setFont(levelFont);
  }
}

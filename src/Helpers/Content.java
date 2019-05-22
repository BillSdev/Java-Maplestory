package Helpers;

import Equipments.E0;
import Equipments.E1;
import Equipments.E101;
import Equipments.Equipment;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import javax.imageio.ImageIO;

public class Content
{
  public static Equipment[] equipArray = { new E0(), new E1(), new E101() };
  public static BufferedImage cursor = loadSinglePicture("/HUD/Cursor.png");
  public static BufferedImage cursorPressed = loadSinglePicture("/HUD/CursorPressed.png");
 // public static BufferedImage[][] Player = load1("/Sprites/Player/ChacterSprites.gif", 47, 72);
  public static BufferedImage head0 = loadSinglePicture("/Character/Body/0/head.png");
  public static BufferedImage levelBar = loadSinglePicture("/HUD/levelBar.png");
  public static BufferedImage gaugeBackGround = loadSinglePicture("/HUD/gaugeBG.png");
  public static BufferedImage gaugeTop = loadSinglePicture("/HUD/gaugeTop.png");
  public static BufferedImage hpGauge = loadSinglePicture("/HUD/hpGauge.png");
  public static BufferedImage backGround = loadSinglePicture("/HUD/backGround2.png");
  public static BufferedImage chat = loadSinglePicture("/HUD/uselessChat.png");
  public static BufferedImage[][] bodyWalk = load("/Character/Body/", "/walk/walkbody", 1, 4);
  public static BufferedImage[][] armWalk = load("/Character/Body/", "/walk/walkarm", 1, 4);
  public static BufferedImage[][] headWalk = load("/Character/Body/", "/walk/head", 1, 4);
  public static BufferedImage[][] bodyStand = load("/Character/Body/", "/stand/standbody", 1, 3);
  public static BufferedImage[][] armStand = load("/Character/Body/", "/stand/standarm", 1, 3);
  public static BufferedImage[][] headStand = load("/Character/Body/", "/stand/head", 1, 3);
  public static BufferedImage[][] bodyJumpFall = load("/Character/Body/", "/jump/jumpbody", 1, 1);
  public static BufferedImage[][] armJumpFall = load("/Character/Body/", "/jump/jumparm", 1, 1);
  public static BufferedImage[][] headJumpFall = load("/Character/Body/", "/jump/head", 1, 1);
  public static BufferedImage[][] Faces = load("/Character/Face/", "/F", 2, 8);
  public static BufferedImage[][] bodyStabO2 = load("/Character/Body/", "/stabO2/body", 1, 2);
  public static BufferedImage[][] armStabO2 = load("/Character/Body/", "/stabO2/arm", 1, 2);
  public static BufferedImage[][] headStabO2 = load("/Character/Body/", "/stabO2/head", 1, 2);
  public static BufferedImage[][] mobMove = load("/Mobs/", "/move/", 3, 5);
  public static BufferedImage[][] mobDie = load("/Mobs/", "/die/", 3, 7);
  public static BufferedImage[][] mobStand = load("/Mobs/", "/stand/", 3, 1);
  public static BufferedImage[][] mobHit = load("/Mobs/", "/hit/", 3, 1);
  
  public static BufferedImage bg = loadSinglePicture("/Backgrounds/MenuOriginal.png");
  public static BufferedImage bg2 = loadSinglePicture("/Backgrounds/Menu1.png");
  public static BufferedImage gamebg = loadSinglePicture("/Backgrounds/background1.gif");
  
  //Components  
  public static BufferedImage playButton = loadSinglePicture("/Components/PlayButton.png");
  public static BufferedImage playButtonOn = loadSinglePicture("/Components/PlayButtonOn.png");
  public static BufferedImage helpButton = loadSinglePicture("/Components/HelpButton.png");
  public static BufferedImage helpButtonOn = loadSinglePicture("/Components/HelpButtonOn.png");
  public static BufferedImage exitButton = loadSinglePicture("/Components/ExitButton.png");
  public static BufferedImage exitButtonOn = loadSinglePicture("/Components/ExitButtonOn.png");
  
  public static BufferedImage playerBullet = loadSinglePicture("/Projectiles/Bullet.png");
  public static BufferedImage[] redSnailProjectiles = loadImageArray("/Projectiles/RedSnailBullet", 4);
  public static BufferedImage blueSnailMissile = loadSinglePicture("/Projectiles/missile.png");
  public static BufferedImage cannonBall = loadSinglePicture("/Projectiles/Cannonball.png");
  
  
  public static BufferedImage alphabet = loadSinglePicture("/Characters/alphabet.png");
  public static BufferedImage startType = loadSinglePicture("/Characters/startType.png");
  public static BufferedImage numbers = loadSinglePicture("/Characters/numbers.png");
  
  public static int[] expTable = { 15, 34, 57, 92, 135, 372, 560, 840, 1242, 1242, 1242, 1242, 1242, 1242, 1490, 1788, 2146, 2575, 3090, 3708, 
    4450, 5340, 6408, 7690, 9228, 11074, 13289, 15947, 19136, 19136 };
  
  public static BufferedImage[][] load(String s1, String s2, int itemNum, int actionNum)
  {
    BufferedImage[][] ret = new BufferedImage[itemNum][actionNum];
    for (int i = 0; i < ret.length; i++) {
      for (int j = 0; j < ret[0].length; j++) {
        try
        {
          System.out.print(s1 + i + s2 + j + ".png ");
          ret[i][j] = ImageIO.read(Content.class.getResourceAsStream(s1 + i + s2 + j + ".png"));
          System.out.println("- Loaded Successfully...1");
        }
        catch (Exception e)
        {
          System.out.println("- Problem loading image...1");
        }
      }
    }
    return ret;
  }
  
  public static BufferedImage[][] load1(String s, int w, int h)
  {
    try
    {
      BufferedImage spritesheet = ImageIO.read(Content.class.getResourceAsStream(s));
      int width = spritesheet.getWidth() / w;
      int height = spritesheet.getHeight() / h;
      BufferedImage[][] ret = new BufferedImage[height][width];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
        }
      }
      return ret;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      System.out.println("Error loading graphics.");
      System.exit(0);
    }
    return null;
  }
  
  public static BufferedImage[] loadImageArray(String s, int l)
  {
    BufferedImage[] ret = new BufferedImage[l];
    try
    {
      for (int i = 0; i < l; i++)
      {
        System.out.print(s + i + ".png ");
        ret[i] = ImageIO.read(Content.class.getResourceAsStream(s + i + ".png "));
        System.out.println("- Loaded Successfully...2");
      }
    }
    catch (Exception e)
    {
      System.out.println("- Problem loading image");e.printStackTrace();
    }
    return ret;
  }
  
  public static BufferedImage loadSinglePicture(String s)
  {
    try
    {
      return ImageIO.read(Content.class.getResourceAsStream(s));
    }
    catch (Exception e)
    {
      System.out.println("Problem loading singleImage");e.printStackTrace();
    }
    return null;
  }
}

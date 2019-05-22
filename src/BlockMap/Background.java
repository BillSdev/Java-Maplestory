package BlockMap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

//a simple object that draws the picture of the map.
//moveScale is used to move the background only a fraction of how much the map moved to give an effect of being far away.

public class Background
{
  private BufferedImage image;
  private double x;
  private double y;
  private double dx;
  private double dy;
  private double moveScale;
  
  public Background(String s, double ms) {
    try  {
    	System.out.println("S1.5");
    	image = ImageIO.read(
        getClass().getResourceAsStream(s));
      
      moveScale = ms;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public Background(double ms) {
	  moveScale = ms;
  }
  
  
  public void setPosition(double x, double y) {
    x = (x * moveScale);
    y = (y * moveScale);
  }
  
  public void setVector(double dx, double dy) {
    this.dx = dx;
    this.dy = dy;
  }
  
  public void update() {
    x += dx;
    y += dy;
  }
  
  public void setImage(BufferedImage image) { this.image = image; }
  
  public void draw(Graphics2D g)
  {
    g.drawImage(image, (int)x, (int)y, null);
    if (x < 0) 
      g.drawImage( image, (int)x + GamePanel.WIDTH, (int)y,  null);
    if (x > 0) 
	 g.drawImage( image,  (int)x - GamePanel.WIDTH,  (int)y,   null);
    
  }
}

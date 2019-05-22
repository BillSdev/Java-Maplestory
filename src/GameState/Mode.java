package GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class Mode
{
  protected ModeChanger mc;
  
  public abstract void init();
  
  public abstract void update();
  
  public abstract void draw(Graphics2D paramGraphics2D);
  
  public abstract void keyPressed(int paramInt);
  
  public abstract void keyReleased(int paramInt);
  
  public abstract void mousePressed(MouseEvent paramMouseEvent);
  
  public abstract void mouseReleased(MouseEvent paramMouseEvent);
}

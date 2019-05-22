package GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class ModeChanger
{
   Mode[] modes;
   int currentMode;
  public static final int NUMOFGAMEMODES = 4;
  public static final int MENUMODE = 0;
  public static final int PLAYMODE = 1;
  public static final int DEADMODE = 2;
  public static final int PAUSEMODE = 3;
  
  public ModeChanger()
  {
    modes = new Mode[4];
    currentMode = 0;
    loadMode(currentMode);
  }
  
  private void loadMode(int mode)
  {
    if (mode == 0) {        
    	modes[mode] = new MenuMode(this);
    }
    if (mode == 1) {   	
    	modes[mode] = new PlayMode(this);
    }
//    if (state == 2) {                                                       implement later
//    	modes[state] = new DeadMode(this);
//    }
//    if (state == 3) {
//    	modes[state] = new PauseMode(this);
//    }
  }
  
  public void setMode(int mode)
  {
    //unloadMode();
	  modes[currentMode] = null;
	  currentMode = mode;
    loadMode(currentMode);
  }
  
//  private void unloadMode()
//  {
//    modes[currentMode] = null;
//  }
  
  public void update() {
	  if(modes[currentMode] != null)
	  modes[currentMode].update();
  }
  
  public void draw(Graphics2D g)
  {
	  if(modes[currentMode] != null)
	  modes[currentMode].draw(g);
  }
  
  public void keyPressed(int k)
  {
    modes[currentMode].keyPressed(k);
  }
  
  public void keyReleased(int k)
  {
    modes[currentMode].keyReleased(k);
  }
  
  public void mousePressed(MouseEvent e)
  {
    modes[currentMode].mousePressed(e);
  }
  
  public void mouseReleased(MouseEvent e)
  {
    modes[currentMode].mouseReleased(e);
  }
}

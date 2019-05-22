package Main;

import GameState.ModeChanger;
import Helpers.Content;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {

  public static final int WIDTH = 1366;
  public static final int HEIGHT = 768;
	
  private Thread thread;
  private boolean running;
  public static int fps = 0;
  private int count = 0;
  private long startTime;
  private final int FPS = 200;
  private Cursor c;
  private PointerInfo pinfo;
  private BufferedImage cursor;
  private BufferedImage image;
  private Graphics2D g;
  private ModeChanger mc;
  private long startTime1;
  
  public GamePanel()
  {
    super();
	setPreferredSize(new Dimension((int)(WIDTH*1), (int)(HEIGHT*1)));
    setFocusable(true);
    requestFocus();
    
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    
    Image image2 = toolkit.getImage("pencil.gif");
    this.c = toolkit.createCustomCursor(image2, new Point(getX(), getY()), "png");
    setCursor(this.c);
    this.cursor = Content.cursor;

    if (this.thread == null)
    {
      this.thread = new Thread(this);
    }
    addKeyListener(this);
    addMouseListener(this);
    this.thread.start();
    
  }

  private void init()
  {
    image = new BufferedImage(WIDTH, HEIGHT, 1);
    g = ((Graphics2D)this.image.getGraphics());
    this.running = true;
    this.mc = new ModeChanger();
  }
  
  public void run()
  {
    init();
    this.startTime = System.nanoTime();
    this.startTime1 = System.nanoTime();
    while (this.running) {
      if (System.nanoTime() - this.startTime >= 16666666)
    	{
        this.startTime = System.nanoTime();
        update();
        draw();
        repaint();
        
        this.count += 1;
        if (System.nanoTime() - this.startTime1 >= 1000000000)
        {
          fps = this.count;
          System.out.println("FPS = " + fps);
          this.count = 0;
          this.startTime1 = System.nanoTime();
        }
      }
    }
  }
  
  private void update()
  {
	requestFocus();     //For whatever reason, the focus is constantly getting taken away, causing the keyboard input to not register.
	 this.mc.update();
    this.pinfo = MouseInfo.getPointerInfo();
  }
  
  private void draw()
  {
    this.mc.draw(this.g);
    this.g.drawImage(this.cursor, this.pinfo.getLocation().x - getLocationOnScreen().x, this.pinfo.getLocation().y - getLocationOnScreen().y, null);  
    //note to self : getLocationOnScreen throws exception if the component is not visible.   -> .setVisible(true) on frame before initializing the panel.
  }
  
  @Override
  public void paintComponent(Graphics g) {
	  g.drawImage(this.image, 0, 0, (int)(WIDTH*1), (int)(HEIGHT*1), null);
  }
  
  public void keyTyped(KeyEvent key) {
	  System.out.println("KeyTyped");
  }
  
  public void keyPressed(KeyEvent key)
  {
	 System.out.println("KeyPressed");
    this.mc.keyPressed(key.getKeyCode());
  }
  
  public void keyReleased(KeyEvent key)
  {
	  System.out.println("Key released");
	  this.mc.keyReleased(key.getKeyCode());
  }
  
  public void mouseClicked(MouseEvent arg0) {}
  
  public void mouseEntered(MouseEvent arg0) {}
  
  public void mouseExited(MouseEvent arg0) {}
  
  public void mousePressed(MouseEvent arg0)
  {
    this.cursor = Content.cursorPressed;
    this.mc.mousePressed(arg0);
  }
  
  public void mouseReleased(MouseEvent arg0)
  {
    this.cursor = Content.cursor;
    this.mc.mouseReleased(arg0);
  }
}

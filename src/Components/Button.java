package Components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import GameState.Mode;


public class Button {

	
	protected Mode actionListener;
	protected BufferedImage imageToDraw;
	protected BufferedImage image;
	protected BufferedImage imageOn;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	//boolean isExist;

	
	public void action() {	}
	
	public void update() {  }
	
	public void draw(Graphics2D g) {
		g.drawImage(imageToDraw, x, y, width, height, null);
	}
	
	public void setCord(int x, int y) { this.x = x ; this.y = y; }
	public void setImage(BufferedImage image) { imageToDraw = image; }
	//public void setExist(boolean b) { isExist = b; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public BufferedImage getImage() { return image; }
	public BufferedImage getImageOn() { return imageOn; }
	
}

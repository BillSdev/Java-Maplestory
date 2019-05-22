package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Helpers.Content;
import BlockMap.Map;

public class Projectile extends MapObject {

    protected int Damage;
    protected boolean Destroyed;
	protected BufferedImage image;
	
	public Projectile(Map tilemap, double x, double y, int width, int height, double vectorX, double vectorY,int Damage ,boolean facingRight) {
    	
		super(tilemap);
		this.cwidth = this.width = width;
    	this.cheight = this.height = height;
    	dy = vectorY;
    	dx = vectorX;
    	if(facingRight == false)
    		dx = -vectorX;
    	setVector(dx, dy);
    	this.x = x;
    	this.y = y;
    	Destroyed = false;
    	this.facingRight = facingRight;
    	this.Damage = Damage;
    	
    	
    	
    }
	
	public boolean isDestroyed() { return Destroyed; }
	
	public void checkHit(ArrayList<Enemy> enemies, Player player) { }
	
	public void update() { 
		if((x + dx < map.getWidth() && x + dx > 0)
				&& (y + dy > 0 && y + dy < map.getHeight())){
		checkCornersForColl(x + dx , y + dy);}
		setMapPosition();
		x += dx;
		y += dy;
		if(bottomLeft || bottomRight || topLeft || topRight)
			Destroyed = true;
		
	}
	
	
	 public void draw(Graphics2D g)
	  {
		 
		 
		 if (facingRight) {
	      g.drawImage(
	        image,
	        (int)(this.x - xmap), 
	        (int)(this.y - ymap), 
	        null);
			 
	    } else {
	    	g.drawImage(
	        image, 
	        (int)(this.x - xmap), 
	        (int)(this.y - ymap), 
	        -width, height, 
	        null);
	    }
		
		  
	  }
	
	
}

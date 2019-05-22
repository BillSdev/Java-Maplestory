package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Helpers.Content;
import BlockMap.Map;

public class RedSnailBullet extends Projectile {

	private BufferedImage[] bulletImageArray;
	
	public RedSnailBullet(Map tilemap, double x, double y, int width, int height, double vectorX, double vectorY, int Damage, boolean facingRight) {
		super(tilemap, x, y, width, height, vectorX, vectorY, Damage, facingRight);
		bulletImageArray = Content.redSnailProjectiles;
		animation = new Animation();
		animation.setFrames(bulletImageArray, 4);
		animation.setDelay(100);
	}
	
	public void checkHit(ArrayList<Enemy> enemies, Player player) {
		if(intersects(player)) {
			player.hit((int)(0.7 * Damage + (int)(Math.random() * (0.6 * Damage))));
			this.Destroyed = true;
		}
	}
	
	public void update() {
		super.update();
		animation.update();
	}
	
	public void draw(Graphics2D g) {
		if (facingRight) {
		      g.drawImage(
		        animation.getImage(),
		        (int)(this.x - xmap), 
		        (int)(this.y - ymap), 
		        null);
				 
		    } else {
		    	g.drawImage(
		        animation.getImage(), 
		        (int)(this.x - xmap), 
		        (int)(this.y - ymap), 
		        -20,20, 
		        null);
		    }
	}

}

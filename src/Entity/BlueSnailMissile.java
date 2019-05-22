package Entity;

import java.util.ArrayList;

import Helpers.Content;
import BlockMap.Map;

public class BlueSnailMissile extends Projectile {

	private Player player;
	private double distancePerTick;
	private double bulletDX;
	private double bulletDY;
	private double slope;

	public BlueSnailMissile(Map tilemap, double x, double y, int width ,int height, double vectorX, double vectorY, int Damage, boolean facingRight, Player player) {
		super(tilemap, x, y, width, height, vectorX, vectorY, Damage, facingRight);
		this.player = player;
		image = Content.blueSnailMissile;
		distancePerTick = 2.5;
	}

	public void checkHit(ArrayList<Enemy> enemies, Player player) {
		if(intersects(player)) {
			player.hit((int)(0.7 * Damage + (int)(Math.random() * (0.6 * Damage))));
			this.Destroyed = true;
		}
	}

	public void update() {
		super.update();
		slope = (player.gety() - this.y)/(player.getx() - this.getx());
		if(player.getx() - this.x > 0){
			facingRight = true;
			bulletDX = distancePerTick/Math.sqrt(1+(slope*slope));
			bulletDY = bulletDX*slope;
			dy = bulletDY;
			dx = bulletDX;
			setVector(dx, dy);}
		else{
			facingRight = false;
			bulletDX = distancePerTick/Math.sqrt(1+(slope*slope));
			bulletDY = bulletDX*slope;
			dy = -bulletDY;
			dx = -bulletDX;
			setVector(dx, dy);
		}
	}


}

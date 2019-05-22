package Entity;

import java.util.ArrayList;

import Helpers.Content;
import BlockMap.Map;

public class Cannonball extends Projectile{

	private double gravity;
	private double vectorDegree;
	private double vectorSize;
	
	//Maximum height the cannonball will reach is y0 + (dy0 + gravity)/(dy0/2*gravity)
	
	public Cannonball(Map blockmap, double x, double y, int width, int height, double vectorX, double vectorY, double vectorSize, double vectorDegree, int Damage, double gravity, boolean facingRight) {
		super(blockmap, x, y, width, height, vectorX, vectorY, Damage, facingRight);
		this.vectorDegree = vectorDegree;
		this.gravity = gravity;
		this.vectorSize = vectorSize;
		dy = Math.sin(Math.toRadians(vectorDegree))*vectorSize + gravity;
		dx = Math.cos(Math.toRadians(vectorDegree))*vectorSize;
		if(this.facingRight == false) {
			dx = -Math.cos(Math.toRadians(vectorDegree))*vectorSize;
			}
		image = Content.cannonBall;
		
	}
	
	public void checkHit(ArrayList<Enemy> enemies, Player player) {
		
		for(int i = 0 ; i < enemies.size() ; i++) {
			if(intersects(enemies.get(i))) {
				enemies.get(i).hit((int)(0.7 * Damage + (int)(Math.random() * (0.6 * Damage))));
				this.Destroyed = true;
			}
			
		}
		
	}
	
	public void calculateVector () {
		dy = dy + gravity;
		//vectorSize = Math.sqrt((dy*dy)+(dx*dx));    //just so that i will have the value 
		//vectorDegree = Math.asin(Math.toRadians(dy/vectorSize));   //just so that i will have the value 
	}
	
	public void update() {
		calculateVector();
		super.update();
		//System.out.println("vectorSize = " + vectorSize + " vectorDegree = " + vectorDegree);
		
	}

}

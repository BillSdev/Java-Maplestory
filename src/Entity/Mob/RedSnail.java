package Entity.Mob;

import Audio.AudioPlayer;
import Entity.Animation;
import Entity.Enemy;
import Entity.Player;
import Entity.Projectile;
import Entity.RedSnailBullet;
import GameState.PlayMode;
import BlockMap.Map;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

public class RedSnail extends Enemy {
	private final int IDLE = 0;
	private final int WALKING = 1;
	private final int FALLING = 2;
	private final int FLINCHING = 3;
	private final int DYING = 4;
	private final int[] numOfFrames = { 1, 4, 1, 1, 3 };

	
	
	//Projectile
	private long lastTime;
	private ArrayList<Projectile> BList;
    private int shotDelay = 500;
    private double distancePerTick;
    private double bulletDX;
    private double bulletDY;
    private double slope;
	
	public RedSnail(Map tm, Player player, ArrayList<Projectile> bulletlist)
	{
		super(tm, player);
		mobID = 2;
		width = 56;
		height = 41;
		cwidth = 32;
		cheight = 34;
		moveSpeed = 0.05;
		maxSpeed = 0.5;
		stopSpeed = 0.05;
		fallSpeed = 0.25000201;
		maxFallSpeed = 30;
		exp = 500;
		damage = 20;
		pushedDistance = 100;
		pushedSpeed = 5;
		xChaseDistance = 150;
		yChaseDistance = 50;
		damageToFlinch = 10;
		timeToFlinch = 1000;
		health = maxHealth = 35 + PlayMode.countRespawn*PlayMode.countRespawn;

		BList = bulletlist;

		facingRight = true;
		right = true;
		flinching = false;
		chasing = false;

		distancePerTick = 3;
		
		currentAction = 1;

		animation = new Animation();
		animation.setFrames(Helpers.Content.mobMove[2], numOfFrames[1]);
		animation.setDelay(100);

		sfx.put("damage", new AudioPlayer("/SoundAffects/" + mobID + "Damage.mp3"));

		sfx.put("die", new AudioPlayer("/SoundAffects/2Die.mp3"));
	}

	public void checkIfChasing()
	{
		if ((player.getx() - x <= xChaseDistance) && (player.getx() - x >= -xChaseDistance) && 
				(player.gety() - y <= yChaseDistance) && (player.gety() - y >= -yChaseDistance)) {
			chasing = true;
		}
	}

	public void getNextPosition()
	{
		if (beingPushed)
			push();
		else if (right) {
			dx += moveSpeed;
			if (dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else if (left) {
			dx -= moveSpeed;
			if (dx < -maxSpeed) 
				dx = (-maxSpeed);	
		}
		if (falling) 
			dy += fallSpeed;
		if ((flinching) && (!beingPushed) && (dying)) 
			dx = 0;
		
		if (dying)	
			dx = 0;dy = 0;
	}

	public void update()
	{
		checkIfChasing();
		getNextPosition();
		checkBlockMapCollision();
		setPosition(xtemp, ytemp);
		if ((dying) && (currentAction == 4))
		{
			if (animation.hasPlayedOnce()) 
				dead = true;
			animation.update();
			return;
		}
		if ((flinching) && ((System.nanoTime() - flinchTimer) / 1000000L >= 400L)) {
			chasing = true;
			flinching = false;
		}
		if (chasing) {
			if (player.getx() - x > 0) {
				right = true;
				left = false;
				facingRight = true;
				if((System.nanoTime() - lastTime)/1000000 > shotDelay) {
					slope = (player.gety() - this.y)/(player.getx() - (this.getx() + 30));
					bulletDX =  distancePerTick/Math.sqrt(1+(slope*slope));
					bulletDY = bulletDX*slope;
					BList.add(new RedSnailBullet(map, this.x + 30, this.y, 20, 8, bulletDX, bulletDY, 20, this.facingRight));
					lastTime = System.nanoTime();
				}
			}
			else
			{
				right = false;
				left = true;
				facingRight = false;
				if((System.nanoTime() - lastTime)/1000000 > shotDelay) {
					double slope = (player.gety() - this.y)/(player.getx() - (this.getx() - 30));
					System.out.println("Slope = " + slope);
					BList.add(new RedSnailBullet(map, this.x - 30, this.y, 20, 8, 3, 3*-slope, 20, this.facingRight));
					lastTime = System.nanoTime();
				}
			}

		}
		if (!chasing) {
			if ((right) && (dx == 0)) {
				right = false;
				left = true;
				facingRight = false;
			}
			else if ((left) && (dx == 0)) {
				left = false;
				right = true;
				facingRight = true;
			}
		}
		if (dying) {
			if (currentAction != 4) {
				currentAction = 4;
				animation.setFrames(Helpers.Content.mobDie[2], 3);
				animation.setDelay(200);
			}
		}
		else if (flinching) {
			if (currentAction != 3) {
				currentAction = 3;
				animation.setFrames(Helpers.Content.mobHit[2], 1);
				animation.setDelay(-1);
			}
		}
		else if (dy > 0) {
			if (currentAction != 2) {
				currentAction = 2;
				animation.setFrames(Helpers.Content.mobStand[2], 1);
				animation.setDelay(1000);
			}
		}
		else if ((left) || (right)) {
			if (currentAction != 1) {
				currentAction = 1;
				animation.setFrames(Helpers.Content.mobMove[2], 4);
				animation.setDelay(100);
			}
		}
		else if (currentAction != 0) {
			currentAction = 0;
			animation.setFrames(Helpers.Content.mobStand[2], 1);
			animation.setDelay(1000);
		}
		animation.update();
 
	}


	public void draw(Graphics2D g)
	{
		setMapPosition();
		super.draw(g);
		g.setColor(Color.RED);
		g.drawString("[" + health + "/" + maxHealth + "]", (int)x - (int)xmap - 30, (int)y - (int)ymap - height);
	}
}

package Entity;

import java.util.ArrayList;

import Helpers.Content;
import BlockMap.Map;

public class PlayerBullet extends Projectile {

	public PlayerBullet(Map tilemap, double x, double y, int width, int height, double vectorX, double vectorY, int Damage, boolean facingRight) {
		super(tilemap, x, y, width, height, vectorX, vectorY, Damage, facingRight);
		image = Content.playerBullet;
	}

	public void checkHit(ArrayList<Enemy> enemies, Player player) {
		for(int i = 0 ; i < enemies.size() ; i++) {	

			if(intersects(enemies.get(i))) {
				enemies.get(i).hit((int)(0.7 * Damage + (int)(Math.random() * (0.6 * Damage))));
				this.Destroyed = true;
			}
			
		}

	}
}

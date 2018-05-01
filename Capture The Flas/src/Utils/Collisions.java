package Utils;

import Entities.Projectiles.Projectile;
import Player.Player;

public class Collisions {
	
	public static boolean PlayerProjectileCollision(Player player, Projectile projectile) {
		float x1 = player.getHero().getX();
		float y1 = player.getHero().getY();
		int r1 = player.getHero().getRadius();
		
		float x2 = projectile.getX();
		float y2 = projectile.getY();
		int r2 = projectile.getRadius();
		
		if(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) < r1 + r2) {
			return true;
		}
		return false;
	}

}

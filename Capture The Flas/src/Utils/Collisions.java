package Utils;

import Entities.Hero;
import Entities.Projectiles.Projectile;

public class Collisions {
	
	public static boolean HeroProjectileCollision(Hero hero, Projectile projectile) {
		float x1 = hero.getX();
		float y1 = hero.getY();
		int r1 = hero.getRadius();
		
		float x2 = projectile.getX();
		float y2 = projectile.getY();
		int r2 = projectile.getRadius();
		
		return circleCollision(x1, y1, r1, x2, y2, r2);
	}
	
	public static boolean circleCollision(float x1, float y1, int r1, float x2, float y2, int r2) {
		if(Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2)) < r1 + r2) {
			return true;
		}
		return false;
	}

}

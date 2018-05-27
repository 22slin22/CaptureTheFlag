package Entities.Weapons;

import Entities.Heros.Hero;
import Entities.Projectiles.StandardProjectile;

public class Laser extends Weapon{
	
	private static final int WEAPON_WIDTH = 22;
	private static final int WEAPON_LENGTH = 25;
	
	public static final int DAMAGE = 5;
	private static final float COOLDOWN = 0.05f;
	
	private static final float PROJECTILE_SPEED = 1f;
	
	
	public Laser(Hero hero) {
		super(hero, WEAPON_WIDTH, WEAPON_LENGTH, DAMAGE, COOLDOWN);
	}
	
	
	@Override
	public void shoot() {
		float spawnX = x + (float)(Math.cos(hero.getGunAngle()) * hero.getRadius());
		float spawnY = y + (float)(-Math.sin(hero.getGunAngle()) * hero.getRadius());
		
		synchronized (hero.getProjectiles()) {
			hero.getProjectiles().add(new StandardProjectile(spawnX, spawnY, hero.getGunAngle(), PROJECTILE_SPEED, hero.getMap()));
		}
		lastShot = System.currentTimeMillis();
	}
}
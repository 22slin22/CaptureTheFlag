package Entities.Weapons;

import Entities.EntityManager;
import Entities.Hero;
import Entities.Projectiles.StandardProjectile;

public class Laser extends Weapon{
	
	private static final int WEAPON_WIDTH = 14;
	private static final int WEAPON_LENGTH = 32;
	
	public static final int DAMAGE = 5;
	private static final float COOLDOWN = 0.05f;
	
	private static final float PROJECTILE_SPEED = 1.3f;
	
	
	public Laser(Hero hero, EntityManager entityManager) {
		super(hero, entityManager, WEAPON_WIDTH, WEAPON_LENGTH, DAMAGE, COOLDOWN);
	}
	
	@Override
	public void shoot() {
		float spawnX = x + (float)(Math.cos(hero.getGunAngle()) * hero.getRadius());
		float spawnY = y + (float)(-Math.sin(hero.getGunAngle()) * hero.getRadius());
		
		synchronized (entityManager.getProjectiles()) {
			entityManager.getProjectiles().add(new StandardProjectile(spawnX, spawnY, hero.getGunAngle(), PROJECTILE_SPEED, hero.getMap(), hero));
		}
		lastShot = System.currentTimeMillis();
	}
	
}
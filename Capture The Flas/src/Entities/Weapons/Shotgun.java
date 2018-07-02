package Entities.Weapons;

import Entities.EntityManager;
import Entities.Hero;
import Entities.Projectiles.StandardProjectile;

public class Shotgun extends Weapon{
	
	private static final int WEAPON_WIDTH = 28;
	private static final int WEAPON_LENGTH = 22;
	
	public static final int DAMAGE = 30;
	private static final float COOLDOWN = 0.5f;
	
	private static final float PROJECTILE_SPEED = 1f;
	private static final int LIFE_TIME = 300;		// in milliseconds
	private static final double DELTA_ANGLE = 0.13;		// = 4 degree
	
	
	public Shotgun(Hero hero, EntityManager entityManager) {
		super(hero, entityManager, WEAPON_WIDTH, WEAPON_LENGTH, DAMAGE, COOLDOWN);
	}
	
	
	@Override
	public void shoot() {
		double angle = hero.getGunAngle() - DELTA_ANGLE;
		for(double i = 0; i < 3; i++) {
			float spawnX = x + (float)(Math.cos(angle) * hero.getRadius());
			float spawnY = y + (float)(-Math.sin(angle) * hero.getRadius());
			
			synchronized (entityManager.getProjectiles()) {
				entityManager.getProjectiles().add(new StandardProjectile(spawnX, spawnY, angle, PROJECTILE_SPEED, hero.getMap(), LIFE_TIME, hero));
			}
			angle += DELTA_ANGLE;
		}
		lastShot = System.currentTimeMillis();
	}
}
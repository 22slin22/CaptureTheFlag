package Entities.Weapons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Entities.Entity;
import Entities.EntityManager;
import Entities.Hero;
import Entities.Tanks.Medium;
import Utils.Teams;

public abstract class Weapon extends Entity{
	
	protected Hero hero;
	protected EntityManager entityManager;
	
	protected int weaponWidth;		// Width of the Barrel
	protected int weaponLength;		// Determines how much the barrel sticks out
	protected float scalar;			// weaponWidth and weaponHeight are for medium tank
	protected int damage;
	
	protected float cooldown;
	protected double lastShot;
	
	
	public static final int GUN = 0;
	public static final int SHOTGUN = 1;
	public static final int LASER = 2;
	
	
	public Weapon(Hero hero, EntityManager entityManager, int weaponWidth, int weaponLength, int damage, float cooldown) {
		super(hero.getX(), hero.getY());
		this.hero = hero;
		this.entityManager = entityManager;
		this.weaponWidth = weaponWidth;
		this.weaponLength = weaponLength;
		this.damage = damage;
		this.cooldown = cooldown;
		
		setScalar(hero.getRadius());
	}
	
	
	public void update() {
		x = hero.getX();
		y = hero.getY();
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		//System.out.println("rendering weapon");
		g.setColor(Teams.getColor(hero.getTeam()));
		Graphics2D g2d = (Graphics2D) g.create();
		
		Rectangle rect = new Rectangle((int)x - cameraX, (int)(y - cameraY - (weaponWidth*scalar)/2), (int)(weaponLength*scalar), (int)(weaponWidth*scalar));		// Weapon sticking out to the right at the start
		g2d.rotate(-hero.getGunAngle(), x-cameraX, y-cameraY);																// then rotation it
		g2d.fill(rect);
		
		g2d.setColor(new Color(50, 50, 50));
		g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
		
		g2d.dispose();
		g2d = (Graphics2D) g.create();
	}
	
	
	public abstract void shoot();

	
	
	public double getLastShot() {
		return lastShot;
	}
	
	public void setLastShot(double lastShot) {
		this.lastShot = lastShot;
	}
	
	public float getCooldown() {
		return cooldown;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public void setScalar(int radius) {
		scalar = (float)radius / (float)Medium.RADIUS;
	}
}

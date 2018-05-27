package Entities.Weapons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Entities.Entity;
import Entities.Heros.Hero;
import Utils.Teams;

public abstract class Weapon extends Entity{
	
	protected Hero hero;
	
	protected int weaponWidth;		// Width of the Barrel
	protected int weaponLength;		// Determines how much the barrel sticks out
	protected int damage;
	
	protected float cooldown;
	protected double lastShot;
	
	
	public static final int GUN = 0;
	public static final int LASER = 1;
	public static final int SHOTGUN = 2;
	
	
	public Weapon(Hero hero, int weaponWidth, int weaponLength, int damage, float cooldown) {
		super(hero.getX(), hero.getY());
		this.hero = hero;
		this.weaponWidth = weaponWidth;
		this.weaponLength = weaponLength;
		this.damage = damage;
		this.cooldown = cooldown;
	}
	
	
	public void tick() {
		x = hero.getX();
		y = hero.getY();
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		//System.out.println("rendering weapon");
		g.setColor(Teams.getColor(hero.getTeam()));
		Graphics2D g2d = (Graphics2D) g.create();
		
		Rectangle rect = new Rectangle((int)x - cameraX, (int)y - cameraY - weaponWidth/2, weaponLength, weaponWidth);		// Weapon sticking out to the right at the start
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
}

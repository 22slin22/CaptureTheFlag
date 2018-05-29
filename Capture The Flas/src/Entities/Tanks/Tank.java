package Entities.Tanks;

import java.awt.Graphics;

import Entities.Entity;
import Entities.Hero;

public abstract class Tank extends Entity{
	
	protected Hero hero;
	
	protected float speed;
	protected int radius;
	protected int defaultHealth;
	
	public Tank(Hero hero, float speed, int radius, int defaultHealth) {
		super(hero.getX(), hero.getY());
		this.hero = hero;
		this.speed = speed;
		this.radius = radius;
		this.defaultHealth = defaultHealth;
	}
	
	public void tick() {
		x = hero.getX();
		y = hero.getY();
	}

	public abstract void render(Graphics g, int cameraX, int cameraY);
	
	
	public float getSpeed() {
		return speed;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public int getDefaultHealth() {
		return defaultHealth;
	}
}

package Entities.Heros;

import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Entity;
import Entities.EntityManager;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Map;
import Map.Obstacle;

public abstract class Hero extends Entity{
	
	protected int max_x, max_y;
	protected int radius;
	
	protected float speed;
	
	protected double gunAngle;
	
	protected float cooldown;
	private double lastShot;
	
	protected EntityManager entityManager;

	
	public Hero(float x, float y, int radius, float speed, Map map, float cooldown, EntityManager entityManager) {
		super(x, y, map.getObstacles());
		this.max_x = map.getWidth();
		this.max_y = map.getHeight();
		this.radius = radius;
		this.speed = speed;
		this.cooldown = cooldown;
		this.entityManager = entityManager;
	}
	
	
	public void tick(float elapsedTime) {
		super.tick(elapsedTime);
	}
	
	public abstract void render(Graphics g, int cameraX, int cameraY);
	
	@Override
	public void move(float x, float y) {
		this.x += x;
		this.y += y;
		
		if(this.x < 0 + radius) {
			this.x = radius;
		}
		else if(this.x > max_x - radius) {
			this.x = max_x - radius;
		}
		if(this.y < 0 + radius) {
			this.y = radius;
		}
		else if(this.y > max_y - radius) {
			this.y = max_y - radius;
		}
		
		for(Obstacle obs : obstacles) {
			if(obs.touches(this)) {
				this.x -= x;
				this.y -= y;
			}
		}
	}
	
	public abstract void shoot();
	
	
	public int getRadius() {
		return radius;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public double getLastShot() {
		return lastShot;
	}
	
	public void setLastShot(double lastShot) {
		this.lastShot = lastShot;
	}
	
	public float getCooldown() {
		return cooldown;
	}
	
	public void setGunAngle(double d) {
		this.gunAngle = d;
	}

}

package Entities.Projectiles;

import java.awt.Graphics;

import Entities.Hero;
import Map.Map;
import Map.Obstacle;
import Utils.Teams;

public class StandardProjectile extends Projectile{
	
	private int lifeTime = -1;
	private long spawnTime;
	
	public final static int RADIUS = 5;

	
	public StandardProjectile(float x, float y, float vx, float vy, Map map) {
		super(x, y, map);
		
		setVx(vx);
		setVy(vy);
		
		spawnTime = System.currentTimeMillis();
	}

	public StandardProjectile(float x, float y, double angle, float speed, Map map, Hero owner) {
		super(x, y, map);
		this.owner = owner;
		
		setVx(angleToSpeedX(angle, speed));
		setVy(angleToSpeedY(angle, speed));
		
		spawnTime = System.currentTimeMillis();
	}
	
	public StandardProjectile(float x, float y, double angle, float speed, Map map, int lifeTime, Hero owner) {
		super(x, y, map);
		this.lifeTime = lifeTime;
		this.owner = owner;
		
		setVx(angleToSpeedX(angle, speed));
		setVy(angleToSpeedY(angle, speed));
		
		spawnTime = System.currentTimeMillis();
	}

	public boolean checkRemove() {
		if (x < RADIUS
				|| x > map.getWidth() - RADIUS
				|| y < RADIUS
				|| y > map.getHeight() - RADIUS) {
			return true;
		}
		for (Obstacle obstacle : obstacles) {
			if (obstacle.touches(this)) {
				return true;
			}
		}
		
		if(lifeTime != -1) {
			if(System.currentTimeMillis() - spawnTime >= lifeTime) {
				return true;
			}
		}
		return false;
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		g.setColor(Teams.getColor(owner.getTeam()));
		g.drawOval((int)x - cameraX - RADIUS, (int)y - cameraY - RADIUS, RADIUS*2, RADIUS*2);
	}
	
	private float angleToSpeedY(double angle, float speed) {
		return (float) (-Math.sin(angle) * speed);
	}
	
	public float angleToSpeedX(double angle, float speed) {
		return (float) (Math.cos(angle) * speed);
	}
	
	public int getRadius() {
		return RADIUS;
	}
	
}

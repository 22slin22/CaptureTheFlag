package Entities.Projectiles;

import java.awt.Color;
import java.awt.Graphics;

import Map.Map;
import Map.Obstacle;

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

	public StandardProjectile(float x, float y, double angle, float speed, Map map) {
		super(x, y, map);
		
		setVx(angleToSpeedX(angle, speed));
		setVy(angleToSpeedY(angle, speed));
		
		spawnTime = System.currentTimeMillis();
	}
	
	public StandardProjectile(float x, float y, double angle, float speed, Map map, int lifeTime) {
		super(x, y, map);
		this.lifeTime = lifeTime;
		
		setVx(angleToSpeedX(angle, speed));
		setVy(angleToSpeedY(angle, speed));
		
		spawnTime = System.currentTimeMillis();
	}

	public void update() {
		if (x < RADIUS
				|| x > map.getWidth() - RADIUS
				|| y < RADIUS
				|| y > map.getHeight() - RADIUS) {
			remove = true;
		}
		for (Obstacle obstacle : obstacles) {
			if (obstacle.touches(this)) {
				remove = true;
				break;
			}
		}
		
		if(lifeTime != -1) {
			if(System.currentTimeMillis() - spawnTime >= lifeTime) {
				remove = true;
			}
		}
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.RED);
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

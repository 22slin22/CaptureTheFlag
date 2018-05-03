package Entities.Projectiles;

import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Entity;
import Map.Map;
import Map.Obstacle;

public abstract class Projectile extends Entity{

	protected int radius;
	protected boolean remove = false;
	
	protected Map map;
	

	public Projectile(float x, float y, float vx, float vy, int radius, Map map) {
		super(x, y, map.getObstacles());
		setVx(vx);
		setVy(vy);
		this.radius = radius;
		this.map = map;
	}
	
	public Projectile(float x, float y, double angle, float speed, int radius, Map map) {
		super(x, y, map.getObstacles());
		setVx(angleToSpeedX(angle, speed));
		setVy(angleToSpeedY(angle, speed));
		
		this.radius = radius;
		this.map = map;
	}

	public void tick() {
		super.tick();
		
		if (x < radius
				|| x > map.getWidth() - radius
				|| y < radius
				|| y > map.getHeight() - radius) {
			remove = true;
		}
		for (Obstacle obstacle : obstacles) {
			if (obstacle.touches(this)) {
				remove = true;
				break;
			}
		}
	}
	
	@Override
	public abstract void render(Graphics g, int cameraX, int cameraY);
	
	private float angleToSpeedY(double angle, float speed) {
		return (float) (-Math.sin(angle) * speed);
	}
	
	public float angleToSpeedX(double angle, float speed) {
		return (float) (Math.cos(angle) * speed);
	}
	
	public int getRadius() {
		return radius;
	}
	
	public boolean isRemove() {
		return remove;
	}
	
	public void setRemove(boolean remove) {
		this.remove = remove;
	}

}

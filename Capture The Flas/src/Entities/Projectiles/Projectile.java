package Entities.Projectiles;

import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Entity;
import Map.Obstacle;

public abstract class Projectile extends Entity{
	
	protected float vx, vy;
	protected int radius;
	

	public Projectile(float x, float y, float vx, float vy, int radius, ArrayList<Obstacle> obstacles) {
		super(x, y, obstacles);
		this.vx = vx;
		this.vy = vy;
		this.radius = radius;
	}
	
	public Projectile(float x, float y, double angle, float speed, int radius, ArrayList<Obstacle> obstacles) {
		super(x, y, obstacles);
		this.vx = angleToSpeedX(angle, speed);
		this.vy = angleToSpeedY(angle, speed);
		this.radius = radius;
	}

	
	@Override
	public void tick() {
		move(vx, vy);
	}

	@Override
	public void render(Graphics g, int cameraX, int cameraY) {
		
	}
	
	private float angleToSpeedY(double angle, float speed) {
		return (float) (-Math.sin(angle) * speed);
	}
	
	public float angleToSpeedX(double angle, float speed) {
		return (float) (Math.cos(angle) * speed);
	}
	
	public int getRadius() {
		return radius;
	}

}

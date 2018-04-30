package Entities.Projectiles;

import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Entity;
import Map.Obstacle;

public abstract class Projectile extends Entity{

	protected int radius;
	

	public Projectile(float x, float y, float vx, float vy, int radius, ArrayList<Obstacle> obstacles) {
		super(x, y, obstacles);
		setVx(vx);
		setVy(vy);
		this.radius = radius;
	}
	
	public Projectile(float x, float y, double angle, float speed, int radius, ArrayList<Obstacle> obstacles) {
		super(x, y, obstacles);
		setVx(angleToSpeedX(angle, speed));
		setVy(angleToSpeedY(angle, speed));
		this.radius = radius;
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

}

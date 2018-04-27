package Entities.Projectiles;

import java.awt.Graphics;

import Entities.Entity;

public abstract class Projectile extends Entity{
	
	protected float vx, vy;
	

	public Projectile(float x, float y, float vx, float vy) {
		super(x, y);
		this.vx = vx;
		this.vy = vy;
	}
	
	public Projectile(float x, float y, double angle, float speed) {
		super(x, y);
		this.vx = angleToSpeedX(angle, speed);
		this.vy = angleToSpeedY(angle, speed);
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

}

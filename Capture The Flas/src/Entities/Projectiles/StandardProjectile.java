package Entities.Projectiles;

import java.awt.Color;
import java.awt.Graphics;

public class StandardProjectile extends Projectile{
	
	public final static int DIAMETER = 10;

	
	public StandardProjectile(float x, float y, float vx, float vy) {
		super(x, y, vx, vy);
	}

	public StandardProjectile(float x, float y, double angle, float speed) {
		super(x, y, angle, speed);
	}
	
	
	public void tick() {
		super.tick();
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.RED);
		g.drawOval((int)x - cameraX, (int)y - cameraY, DIAMETER, DIAMETER);
	}
	
}

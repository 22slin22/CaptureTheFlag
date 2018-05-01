package Entities.Projectiles;

import java.awt.Color;
import java.awt.Graphics;

import Map.Map;

public class StandardProjectile extends Projectile{
	
	public final static int DIAMETER = 10;

	
	public StandardProjectile(float x, float y, float vx, float vy, Map map) {
		super(x, y, vx, vy, DIAMETER/2, map);
	}

	public StandardProjectile(float x, float y, double angle, float speed, Map map) {
		super(x, y, angle, speed, DIAMETER/2, map);
	}

	
	public void render(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.RED);
		g.drawOval((int)x - cameraX - radius, (int)y - cameraY - radius, DIAMETER, DIAMETER);
	}
	
}

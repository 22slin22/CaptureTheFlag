package Entities.Projectiles;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Map.Obstacle;

public class StandardProjectile extends Projectile{
	
	public final static int DIAMETER = 10;

	
	public StandardProjectile(float x, float y, float vx, float vy, ArrayList<Obstacle> obstacles) {
		super(x, y, vx, vy, DIAMETER/2, obstacles);
	}

	public StandardProjectile(float x, float y, double angle, float speed, ArrayList<Obstacle> obstacles) {
		super(x, y, angle, speed, DIAMETER/2, obstacles);
	}
	
	
	public void tick(float elapsedTime) {
		super.tick(elapsedTime);
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.RED);
		g.drawOval((int)x - cameraX - radius, (int)y - cameraY - radius, DIAMETER, DIAMETER);
	}
	
}

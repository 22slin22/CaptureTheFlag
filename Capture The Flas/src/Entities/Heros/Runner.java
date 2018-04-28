package Entities.Heros;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import Entities.EntityManager;
import Entities.Projectiles.StandardProjectile;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Map;

public class Runner extends Hero{
	
	private final static float SPEED = 0.1f;
	private final static int DIAMETER = 40;
	
	private final static float COOLDOWN = 0.1f;
	private final static float PROJECTILE_SPEED = 0.3f;

	public Runner(float x, float y, Map map, EntityManager entityManager) {
		super(x, y, DIAMETER/2, SPEED, map, COOLDOWN, entityManager);
		System.out.println(x);
		System.out.println(y);
	}
	
	
	@Override
	public void tick(float elapsedTime) {
		super.tick(elapsedTime);
	}

	@Override
	public void render(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.BLACK);
		g.fillOval((int)x - cameraX - DIAMETER/2, (int)y - cameraY - DIAMETER/2, DIAMETER, DIAMETER);
	}

	@Override
	public void shoot(double angle) {
		entityManager.addProjectile(new StandardProjectile(x, y, angle, PROJECTILE_SPEED, obstacles));
	}

}

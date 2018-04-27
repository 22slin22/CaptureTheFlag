package Entities.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Entities.EntityManager;
import Entities.Projectiles.StandardProjectile;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Obstacle;

public class Runner extends Player{
	
	private final static float SPEED = 5;
	private final static int DIAMETER = 40;
	
	private final static float COOLDOWN = 0.01f;
	private final static float PROJECTILE_SPEED = 15;

	public Runner(float x, float y, int max_x, int max_y, KeyManager keyManager, MouseManager mouseManager, Camera camera, EntityManager entityManager) {
		super(x, y, DIAMETER/2, max_x, max_y, COOLDOWN, keyManager, mouseManager, camera, entityManager);
		System.out.println(x);
		System.out.println(y);
	}

	
	public void tick(ArrayList<Obstacle> obstacles) {
		if(keyManager.isKeyPressed(KeyEvent.VK_A) || keyManager.isKeyPressed(KeyEvent.VK_LEFT)) {
			move(-SPEED, 0, obstacles);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_W) || keyManager.isKeyPressed(KeyEvent.VK_UP)) {
			move(0, -SPEED, obstacles);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_D) || keyManager.isKeyPressed(KeyEvent.VK_RIGHT)) {	
			move(SPEED, 0, obstacles);
		}
		if(keyManager.isKeyPressed(KeyEvent.VK_S) || keyManager.isKeyPressed(KeyEvent.VK_DOWN)) {
			move(0, SPEED, obstacles);
		}
		
		super.tick();
	}

	@Override
	public void render(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.BLACK);
		g.fillOval((int)x - cameraX - DIAMETER/2, (int)y - cameraY - DIAMETER/2, DIAMETER, DIAMETER);
	}


	@Override
	public void tick() {}

	@Override
	protected void shoot(double angle) {
		entityManager.addProjectile(new StandardProjectile(x, y, angle, PROJECTILE_SPEED));
	}

}

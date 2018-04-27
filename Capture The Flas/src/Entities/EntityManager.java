package Entities;

import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Player.Runner;
import Entities.Projectiles.Projectile;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Map;
import Map.Obstacle;

public class EntityManager {
	
	private Runner runner;
	private Map map;
	
	private ArrayList<Projectile> projectiles;
	
	
	public EntityManager(KeyManager keyManager, MouseManager mouseManager, Map map, Camera camera) {
		runner = new Runner(1000, 1000, map, keyManager, mouseManager, camera, this);
		projectiles = new ArrayList<>();
		
		this.map = map;
	}
	
	
	public void tick() {
		runner.tick();
		
		boolean dead;
		for(int i = projectiles.size()-1; i>=0; i--) {
			dead = false;
			if(projectiles.get(i).getX() < projectiles.get(i).getRadius() || projectiles.get(i).getX() > map.getWidth() - projectiles.get(i).getRadius()
					|| projectiles.get(i).getY() < projectiles.get(i).getRadius() || projectiles.get(i).getY() > map.getHeight() - projectiles.get(i).getRadius()) {
				dead = true;
			}
			for(Obstacle obstacle : map.getObstacles()) {
				if(obstacle.touches(projectiles.get(i))) {
					dead = true;
					break;
				}
			}
			if(dead) {
				projectiles.remove(i);
			}
			else {
				projectiles.get(i).tick();
			}
		}
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		runner.render(g, cameraX, cameraY);
		for(Projectile projectile : projectiles) {
			projectile.render(g, cameraX, cameraY);
		}
	}
	
	
	public Runner getRunner() {
		return runner;
	}
	
	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}

}

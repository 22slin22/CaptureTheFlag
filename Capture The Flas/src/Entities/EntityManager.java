package Entities;

import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Player.Runner;
import Entities.Projectiles.Projectile;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Map;

public class EntityManager {
	
	private Runner runner;
	private Map map;
	
	private ArrayList<Projectile> projectiles;
	
	
	public EntityManager(KeyManager keyManager, MouseManager mouseManager, Map map, Camera camera) {
		runner = new Runner(1000, 1000, map.getWidth(), map.getHeight(), keyManager, mouseManager, camera, this);
		projectiles = new ArrayList<>();
		
		this.map = map;
	}
	
	
	public void tick() {
		runner.tick(map.getObstacles());
		for(Projectile projectile : projectiles) {
			projectile.tick();
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

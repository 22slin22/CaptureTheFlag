package Entities;

import java.awt.Graphics;

import Entities.Player.Runner;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Map;

public class EntityManager {
	
	private Runner runner;
	private Map map;
	
	public EntityManager(KeyManager keyManager, MouseManager mouseManager, Map map, Camera camera) {
		runner = new Runner(1000, 1000, map.getWidth(), map.getHeight(), keyManager, mouseManager, camera);
		this.map = map;
	}
	
	
	public void tick() {
		runner.tick(map.getObstacles());
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		runner.render(g, cameraX, cameraY);
	}
	
	
	public Runner getRunner() {
		return runner;
	}

}

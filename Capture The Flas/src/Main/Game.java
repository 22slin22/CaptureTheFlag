package Main;
import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Input.KeyManager;
import Input.MouseManager;
import Map.Camera;
import Map.Map;

public class Game {
	
	private int cameraX, cameraY;
	
	private Map map;
	private EntityManager entityManager;
	
	private Camera camera;
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	public Game(KeyManager keyManager, MouseManager mouseManager) {
		map = new Map();
		camera = new Camera(map.getWidth(), map.getHeight());
		entityManager = new EntityManager(keyManager, mouseManager, map, camera);
		camera.setPlayer(entityManager.getRunner());
		
		this.keyManager = keyManager;
		this.mouseManager = mouseManager;
	}
	
	public void tick() {
		entityManager.tick();
		camera.tick();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.getWidth(), Main.getHeight());
		
		cameraX = camera.getX();
		cameraY = camera.getY();
		
		map.render(g, cameraX, cameraY);
		entityManager.render(g, cameraX, cameraY);
	}

}

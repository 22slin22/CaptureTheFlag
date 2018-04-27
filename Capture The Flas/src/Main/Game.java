package Main;
import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Input.KeyManager;
import Map.Camera;
import Map.Map;

public class Game {
	
	private int xOffset, yOffset;
	
	private Map map;
	private EntityManager entityManager;
	
	private Camera camera;
	private KeyManager keyManager;
	
	public Game(KeyManager keyManager) {
		entityManager = new EntityManager(keyManager);
		map = new Map();
		camera = new Camera(entityManager.getRunner(), map.getWidth(), map.getHeight());
		
		this.keyManager = keyManager;
	}
	
	public void tick() {
		entityManager.tick();
		camera.tick();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.getWidth(), Main.getHeight());
		
		xOffset = camera.getX();
		yOffset = camera.getY();
		
		map.render(g, xOffset, yOffset);
		entityManager.render(g, xOffset, yOffset);
	}

}

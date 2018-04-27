package Entities;

import java.awt.Graphics;

import Entities.Player.Runner;
import Input.KeyManager;

public class EntityManager {
	
	private Runner runner;
	
	public EntityManager(KeyManager keyManager) {
		runner = new Runner(1000, 1000, keyManager);
	}
	
	
	public void tick() {
		runner.tick();
	}
	
	public void render(Graphics g, int xOffset, int yOffset) {
		runner.render(g, xOffset, yOffset);
	}
	
	
	public Runner getRunner() {
		return runner;
	}

}

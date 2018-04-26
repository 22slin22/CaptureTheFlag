package Main;
import java.awt.Color;
import java.awt.Graphics;

import Entities.Player.Runner;
import Input.KeyManager;
import Map.Map;

public class Game {
	
	private Runner runner;
	private Map map;
	
	private KeyManager keyManager;
	
	public Game(KeyManager keyManager) {
		runner = new Runner(300, 300, keyManager);
		map = new Map(runner);
		this.keyManager = keyManager;
	}
	
	public void tick() {
		runner.tick();
		map.tick();
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.getWidth(), Main.getHeight());
		
		map.render(g);
		runner.render(g);
	}

}

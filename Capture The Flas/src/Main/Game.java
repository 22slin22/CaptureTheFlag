package Main;
import java.awt.Color;
import java.awt.Graphics;

import Entities.Player.Runner;
import Input.KeyManager;
import Map.Map;

public class Game {
	
	private int xOffset, yOffset;
	
	private Runner runner;
	private Map map;
	
	private KeyManager keyManager;
	
	public Game(KeyManager keyManager) {
		runner = new Runner(Main.getWidth()/2, Main.getHeight()/2, keyManager);
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
		
		xOffset = map.getXOffset();
		yOffset = map.getYOffset();
		
		map.render(g);
		runner.render(g, xOffset, yOffset);
	}

}

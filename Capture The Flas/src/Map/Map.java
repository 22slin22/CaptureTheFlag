package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Player.Player;
import Main.Main;

public class Map {
	
	private final static int WIDTH = 3000;
	private final static int HEIGHT = 2000;
	
	private final static ArrayList<Obstacle> obstacles = new ArrayList<>();
	
	
	public Map() {
		
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int xOffset, int yOffset) {
		g.setColor(Color.WHITE);
		g.fillRect(-xOffset, -yOffset, WIDTH, HEIGHT);
		
		for(Obstacle obs : obstacles) {
			obs.render(g);
		}
	}
	
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}

}

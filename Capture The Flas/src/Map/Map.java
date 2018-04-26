package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Player.Player;
import Main.Main;

public class Map {
	
	private final static int WIDTH = 3000;
	private final static int HEIGHT = 2000;
	
	private int xOffset, yOffset;
	
	private Player player;
	
	private final static ArrayList<Obstacle> obstacles = new ArrayList<>();
	
	
	public Map(Player player) {
		this.player = player;
	}
	
	public void tick() {
		xOffset = (int)(-player.getX() - Main.getWidth()/2);
		yOffset = (int)(-player.getY() - Main.getHeight()/2);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(xOffset, yOffset, WIDTH, HEIGHT);
		
		for(Obstacle obs : obstacles) {
			obs.render(g);
		}
	}

}

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
		
		//System.out.println(xOffset);
		
		if(xOffset > 0 + 30) {
			xOffset = 0 + 30;
		}
		else if(xOffset < -WIDTH + Main.getWidth() - 30) {
			xOffset = -WIDTH + Main.getWidth() - 30;
		}
		if(yOffset > 0 + 30) {
			yOffset = 0 + 30;
		}
		else if(yOffset < -HEIGHT + Main.getHeight() - 30) {
			yOffset = -HEIGHT + Main.getHeight() - 30;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(xOffset, yOffset, WIDTH, HEIGHT);
		
		for(Obstacle obs : obstacles) {
			obs.render(g);
		}
	}
	
	public int getXOffset() {
		return xOffset;
	}
	
	public int getYOffset() {
		return yOffset;
	}

}

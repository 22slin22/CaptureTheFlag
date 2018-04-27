package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Player.Player;
import Main.Main;

public class Map {
	
	private final static int WIDTH = 2000;
	private final static int HEIGHT = 1500;
	
	private final static ArrayList<Obstacle> obstacles = new ArrayList<>();
	
	
	public Map() {
		obstacles.add(new Obstacle(300, 100, 200, 100));
		obstacles.add(new Obstacle(100, 300, 100, 200));
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int cameraX, int cameraY) {
		g.setColor(Color.WHITE);
		g.fillRect(-cameraX, -cameraY, WIDTH, HEIGHT);
		
		for(Obstacle obs : obstacles) {
			obs.render(g, cameraX, cameraY);
		}
	}
	
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public ArrayList<Obstacle> getObstacles(){
		return obstacles;
	}

}

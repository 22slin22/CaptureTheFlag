package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Heros.Hero;
import Main.Main;
import Utils.Teams;

public class Map {
	
	private final static int WIDTH = 3000;
	private final static int HEIGHT = 2000;
	
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
		renderBackgroundGrid(g, cameraX, cameraY);
		
		for(Obstacle obs : obstacles) {
			obs.render(g, cameraX, cameraY);
		}
	}
	
	
	private int gridLineDistance = 25;
	private void renderBackgroundGrid(Graphics g, int cameraX, int cameraY) {
		g.setColor(new Color(240, 240, 240));
		for(int i=0; i < WIDTH; i+=gridLineDistance) {
			g.drawLine(i - cameraX, 0- cameraY, i- cameraX, HEIGHT- cameraY);
		}
		for(int i=0; i < HEIGHT; i+=gridLineDistance) {
			g.drawLine(0- cameraX, i- cameraY, WIDTH- cameraX, i- cameraY);
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

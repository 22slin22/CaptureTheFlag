package Map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Entities.Hero;
import Main.Main;
import Utils.Teams;

public class Map {
	
	private final static int WIDTH = 3000;
	private final static int HEIGHT = 2000;
	
	private final static ArrayList<Obstacle> obstacles = new ArrayList<>();
	
	public Map() {
		addObstacles();
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
	
	private void addObstacles() {
		//left side
		obstacles.add(new Obstacle(200, 500, 400, 200));
		obstacles.add(new Obstacle(200, 1300, 400, 200));
		
		obstacles.add(new Obstacle(400, 900, 100, 200));
		
		obstacles.add(new Obstacle(500, 200, 400, 100));
		obstacles.add(new Obstacle(800, 300, 100, 100));
		obstacles.add(new Obstacle(500, 1700, 400, 100));
		obstacles.add(new Obstacle(800, 1600, 100, 100));
		
		obstacles.add(new Obstacle(900, 600, 100, 300));
		obstacles.add(new Obstacle(900, 1100, 100, 300));
		obstacles.add(new Obstacle(1000, 500, 100, 400));
		obstacles.add(new Obstacle(1100, 500, 100, 200));
		obstacles.add(new Obstacle(1000, 1100, 100, 400));
		obstacles.add(new Obstacle(1100, 1300, 100, 200));
		
		obstacles.add(new Obstacle(1100, 300, 100, 100));
		obstacles.add(new Obstacle(1100, 1600, 100, 100));
		
		//right side
		obstacles.add(new Obstacle(2400, 500, 400, 200));
		obstacles.add(new Obstacle(2400, 1300, 400, 200));
		
		obstacles.add(new Obstacle(2500, 900, 100, 200));
		
		obstacles.add(new Obstacle(2100, 200, 400, 100));
		obstacles.add(new Obstacle(2100, 300, 100, 100));
		obstacles.add(new Obstacle(2100, 1700, 400, 100));
		obstacles.add(new Obstacle(2100, 1600, 100, 100));
		
		obstacles.add(new Obstacle(2000, 600, 100, 300));
		obstacles.add(new Obstacle(2000, 1100, 100, 300));
		obstacles.add(new Obstacle(1900, 500, 100, 400));
		obstacles.add(new Obstacle(1800, 500, 100, 200));
		obstacles.add(new Obstacle(1900, 1100, 100, 400));
		obstacles.add(new Obstacle(1800, 1300, 100, 200));
		
		obstacles.add(new Obstacle(1800, 300, 100, 100));
		obstacles.add(new Obstacle(1800, 1600, 100, 100));
		
		//middle
		obstacles.add(new Obstacle(1200, 0, 600, 100));
		obstacles.add(new Obstacle(1200, 100, 200, 100));
		obstacles.add(new Obstacle(1600, 100, 200, 100));
		obstacles.add(new Obstacle(1200, 1900, 600, 100));
		obstacles.add(new Obstacle(1200, 1800, 200, 100));
		obstacles.add(new Obstacle(1600, 1800, 200, 100));
		
		obstacles.add(new Obstacle(1300, 300, 400, 100));
		obstacles.add(new Obstacle(1400, 400, 200, 100));
		obstacles.add(new Obstacle(1300, 1600, 400, 100));
		obstacles.add(new Obstacle(1400, 1500, 200, 100));
		
		obstacles.add(new Obstacle(1400, 900, 200, 200));
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

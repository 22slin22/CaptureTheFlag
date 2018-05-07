package Display.UI;

import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Main.Main;
import Map.Map;
import Map.Obstacle;
import Player.Player;
import Utils.Teams;

public class Minimap {
	
	private Map map;
	private EntityManager entityManager;
	
	private int width = 300;
	private int height;
	
	private int xOffset = 50;
	private int yOffset = 50;
	
	private int xStart;
	private int yStart;
	
	private float minimapScalar;
	
	private int borderThickness = 3;
	
	
	public Minimap() {
		xStart = Main.getWidth() - xOffset - width;
		yStart = yOffset;
	}
	
	
	public void render(Graphics g) {
		drawBorder(g, height);
		drawObstacles(g);
		drawPlayers(g);
	}
	
	private  void drawBorder(Graphics g, int minimapHeight) {
		g.setColor(Color.BLACK);
		g.fillRect(xStart - borderThickness, yStart - borderThickness, width + 2*borderThickness, minimapHeight + 2*borderThickness);
		
		g.setColor(Color.WHITE);
		g.fillRect(xStart, yStart, width, minimapHeight);
	}
	
	private void drawObstacles(Graphics g) {
		g.setColor(Color.BLACK);
		for(Obstacle obs : map.getObstacles()) {
			g.fillRect(xStart + (int)(obs.getX()*minimapScalar), yStart + (int)(obs.getY()*minimapScalar), (int)(obs.getWidth()*minimapScalar), (int)(obs.getHeight()*minimapScalar));
		}
	}
	
	private void drawPlayers(Graphics g) {
		synchronized (entityManager.getPlayers()) {
			for(Player player : entityManager.getPlayers()) {
				g.setColor(Teams.getColor(player.getTeam()));
				g.fillOval(xStart + (int)((player.getHero().getX() - (float)player.getHero().getRadius())*minimapScalar), 
						yStart + (int)((player.getHero().getY() - player.getHero().getRadius())*minimapScalar), 
						(int)(player.getHero().getRadius()*2*minimapScalar), (int)(player.getHero().getRadius()*2*minimapScalar));
			}
		}
	}
	
	public void setMap(Map map) {
		this.map = map;
		height = width * map.getHeight() / map.getWidth();
		minimapScalar = (float)width / (float)map.getWidth();
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}

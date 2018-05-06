package Display.UI;

import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Main.Main;
import Map.Map;
import Map.Obstacle;
import Player.Player;
import Utils.Fonts;
import Utils.Teams;

public class Overlay {
	
	private Map map;
	private EntityManager entityManager;
	
	private int minimapWidth = 300;
	private int minimapHeight;
	
	private int minimapXOffset = 50;
	private int minimapYOffset = 50;
	
	private int minimapXStart;
	private int minimapYStart;
	
	private float minimapScalar;
	
	public Overlay(Map map, EntityManager entityManager) {
		this.map = map;
		this.entityManager = entityManager;
		
		minimapHeight = minimapWidth * map.getHeight() / map.getWidth();
		minimapXStart = Main.getWidth() - minimapXOffset - minimapWidth;
		minimapYStart = minimapYOffset;
		minimapScalar = (float)minimapWidth / (float)map.getWidth();
	}
	
	
	public void render(Graphics g) {
		drawScoreboard(g);
		drawMinimap(g);
	}
	
	private int scoreboardXOffset = 15;
	private int scoreboardYOffset = 45;
	private void drawScoreboard(Graphics g) {
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, ":", Main.getWidth()/2, scoreboardYOffset - 3, Fonts.scoreFont);
		
		g.setColor(Teams.getColor(Teams.BLUE));
		Fonts.drawRightAllinedText(g, "" + Teams.getScore(Teams.BLUE), Main.getWidth()/2 - scoreboardXOffset, scoreboardYOffset, Fonts.scoreFont);
		
		g.setColor(Teams.getColor(Teams.RED));
		Fonts.drawLeftAllinedText(g, "" + Teams.getScore(Teams.RED), Main.getWidth()/2 + scoreboardXOffset, scoreboardYOffset, Fonts.scoreFont);
	}
	
	private void drawMinimap(Graphics g) {
		drawMinimapBorder(g, minimapHeight);
		drawMinimapObstacles(g);
		drawMinimapPlayers(g);
	}
	
	private int borderThickness = 3;
	private  void drawMinimapBorder(Graphics g, int minimapHeight) {
		g.setColor(Color.BLACK);
		g.fillRect(minimapXStart - borderThickness, minimapYStart - borderThickness, minimapWidth + 2*borderThickness, minimapHeight + 2*borderThickness);
		
		g.setColor(Color.WHITE);
		g.fillRect(minimapXStart, minimapYStart, minimapWidth, minimapHeight);
	}
	
	private void drawMinimapObstacles(Graphics g) {
		g.setColor(Color.BLACK);
		for(Obstacle obs : map.getObstacles()) {
			g.fillRect(minimapXStart + (int)(obs.getX()*minimapScalar), minimapYStart + (int)(obs.getY()*minimapScalar), (int)(obs.getWidth()*minimapScalar), (int)(obs.getHeight()*minimapScalar));
		}
	}
	
	private void drawMinimapPlayers(Graphics g) {
		synchronized (entityManager.getPlayers()) {
			for(Player player : entityManager.getPlayers()) {
				g.setColor(Teams.getColor(player.getTeam()));
				g.fillOval(minimapXStart + (int)((player.getHero().getX() - (float)player.getHero().getRadius())*minimapScalar), 
						minimapYStart + (int)((player.getHero().getY() - player.getHero().getRadius())*minimapScalar), 
						(int)(player.getHero().getRadius()*2*minimapScalar), (int)(player.getHero().getRadius()*2*minimapScalar));
			}
		}
	}

}

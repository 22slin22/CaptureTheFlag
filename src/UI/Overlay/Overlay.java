package UI.Overlay;

import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Entities.Hero;
import Input.KeyManager;
import Main.Main;
import Map.Map;
import Utils.Fonts;
import Utils.Teams;

public class Overlay {
	
	private static final int scoreboardXOffset = 15;
	private static final int scoreboardYOffset = 45;
	
	
	private Minimap minimap;
	private Killfeed killfeed;
	private DeathOverlay deathOverlay;
	private NotificationManager notificationManager;
	private Tab tab;
	
	
	public Overlay(Hero player, EntityManager entityManager, KeyManager keyManager) {
		minimap = new Minimap();
		killfeed = new Killfeed();
		deathOverlay = new DeathOverlay(player);
		notificationManager = new NotificationManager(entityManager);
		tab = new Tab(keyManager, player, entityManager);
	}
	
	
	public void tick() {
		killfeed.tick();
		notificationManager.tick();
	}
	
	public void render(Graphics g) {
		drawScoreboard(g);
		minimap.render(g);
		killfeed.render(g);
		deathOverlay.render(g);
		notificationManager.render(g);
		tab.render(g);	
	}
	
	private static final int boxHeight = 80;
	private static final int boxWidth = 130;
	private void drawScoreboard(Graphics g) {
		g.setColor(new Color(0.5f, 0.5f, 0.5f, 0.5f));
		// Points from left to right
		g.fillPolygon(new int[] {Main.getWidth()/2 - boxWidth/2 - boxHeight, Main.getWidth()/2 - boxWidth/2, Main.getWidth()/2 + boxWidth/2, Main.getWidth()/2 + boxWidth/2 + boxHeight},  
				new int[] {0, boxHeight, boxHeight, 0}, 4);
		
		Fonts.drawCenteredText(g, new String[] {""+Teams.getScore(Teams.BLUE), " : ", ""+Teams.getScore(Teams.RED)}, Main.getWidth()/2, scoreboardYOffset, Fonts.scoreFont, 
				new Color[] {Teams.getColor(Teams.BLUE), Color.GRAY, Teams.getColor(Teams.RED)});
	}
	
	public Killfeed getKillfeed() {
		return killfeed;
	}
	
	public void setMap(Map map) {
		minimap.setMap(map);
	}
	
	public void setEntityManager(EntityManager entityManager) {
		minimap.setEntityManager(entityManager);
	}
	
	
	public NotificationManager getNotificationManager() {
		return notificationManager;
	}

}

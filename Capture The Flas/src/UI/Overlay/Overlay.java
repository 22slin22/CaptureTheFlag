package UI.Overlay;

import java.awt.Color;
import java.awt.Graphics;

import Entities.EntityManager;
import Entities.Hero;
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
	
	
	public Overlay(Hero hero) {
		minimap = new Minimap();
		killfeed = new Killfeed();
		deathOverlay = new DeathOverlay(hero);
	}
	
	
	public void tick() {
		killfeed.tick();
	}
	
	public void render(Graphics g) {
		drawScoreboard(g);
		minimap.render(g);
		killfeed.render(g);
		deathOverlay.render(g);
	}
	
	
	private void drawScoreboard(Graphics g) {
		g.setColor(Color.BLACK);
		Fonts.drawCenteredText(g, ":", Main.getWidth()/2, scoreboardYOffset - 3, Fonts.scoreFont);
		
		g.setColor(Teams.getColor(Teams.BLUE));
		Fonts.drawRightAllinedText(g, "" + Teams.getScore(Teams.BLUE), Main.getWidth()/2 - scoreboardXOffset, scoreboardYOffset, Fonts.scoreFont);
		
		g.setColor(Teams.getColor(Teams.RED));
		Fonts.drawLeftAllinedText(g, "" + Teams.getScore(Teams.RED), Main.getWidth()/2 + scoreboardXOffset, scoreboardYOffset, Fonts.scoreFont);
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

}

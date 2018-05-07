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
	
	private Minimap minimap;
	private Killfeed killfeed;
	
	public Overlay() {
		minimap = new Minimap();
		killfeed = new Killfeed();
	}
	
	
	public void tick() {
		killfeed.tick();
	}
	
	public void render(Graphics g) {
		drawScoreboard(g);
		minimap.render(g);
		killfeed.render(g);
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
